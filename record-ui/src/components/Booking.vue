<template>
  <NavBar></NavBar>
  <div class="container-fluid p-0 mx-0 position-relative w-100 d-flex flex-column" id="top">
    <div
      id="sectionheader"
      :style="{ backgroundImage: `url(${currentBackground})` }"
      class="mx-0"
    >
      <div class="main">
        <h1 class="title">{{ title }}</h1>
        <h3 class="titledescription">
          You are entitled to 2 passes a month. {{}}
        </h3>
        <h3 v-if="attractionDetails.length != 0" class="titledescription">
          Each pass entitles you to {{ numOfAccompanyingGuests }} accompanying
          guests to {{ title }}
        </h3>
      </div>

      <div class="bookingdetails">
        <div class="dropdown" id="group-location">
          <select
            v-model="attractionDetails"
            class="form-select shadow"
            @change="
              populateNoOfTickets();
              populateAttractionDetails();
            "
          >
            <option disabled value="">Attractions</option>
            <option
              v-for="(id, location) in locations"
              :key="id"
              :value="{ id }"
            >
              {{ location }}
            </option>
          </select>
        </div>

        <div id="group-calendar">
          <div>
            <div class="position-absolute">
              <button
                id="calendar-details"
                class="form-select shadow"
                @click="showCalendar = !showCalendar"
              >
                {{
                  dateSelected != null
                    ? dateSelected.toDateString()
                    : "No date selected"
                }}
              </button>
              <div v-if="showCalendar">
                <v-date-picker
                  v-model="dateSelected"
                  :attributes="attributes"
                ></v-date-picker>
              </div>
            </div>
          </div>
        </div>

        <div class="dropdown" id="group-date">
          <select v-model="numPassesSelected" class="form-select shadow">
            <option disabled value="">Passes</option>
            <option
              v-for="pass in numberofPasses"
              :key="pass"
              :value="{ pass }"
            >
              {{ pass }}
            </option>
          </select>
        </div>

        <div id="group-submit">
          <button
            type="submit"
            class="btn btn-submit btn-md"
            @click="addLoan()"
          >
            Book Now
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";
import NavBar from "../components/Navbar.vue";
import "v-calendar/dist/style.css";

type ticketInformation = {
  description: string;
  isComplete: boolean;
  dates: Date;
  color: string;
};

// Typings
interface Data {
  locations: object;
  title: string;
  attraction: string;
  attractionDetails: any;
  dateSelected: Date;
  numOfAccompanyingGuests: number;
  numPassesSelected: string;
  numberofPasses: number[];
  type: boolean;
  showCalendar: boolean;
  currentBackground: string;
  loanID: number;
  attractionId: number;
  staffId: number;
  ticketInformation: ticketInformation[];
  errorMsg: string;
}

interface LoginData {
  staffId: number;
  role: string;
}

export default defineComponent({
  data(): Data {
    return {
      type: true,
      attraction: "",
      attractionDetails: "",
      locations: {},
      title: "Book Your Passes Now!",
      dateSelected: new Date(),
      numOfAccompanyingGuests: 0,
      numPassesSelected: "",
      numberofPasses: [],
      showCalendar: false,
      currentBackground:
        "https://www.sportsschool.edu.sg/qql/slot/u262/2021/News%20and%20Publications/News/2021/MAR21/What%20Makes%20Us%20Athlete-Friendly/Athlete-friendly%20environment%20at%20Singapore%20Sports%20School%20helps%20nurture%20champions.jpg",
      loanID: 0,
      attractionId: 0,
      errorMsg: "",
      staffId: 0,
      ticketInformation: [
        {
          description: "2 Passes left",
          isComplete: false,
          dates: new Date(2022, 11, 11), // date should be array of objects where one object is one date, format month year and day into an object
          color: "red",
        },
        {
          description: "1 Pass left",
          isComplete: false,
          dates: new Date(2022, 11, 12), // date should be array of objects where one object is one date
          color: "red",
        },
      ],
    };
  },
  watch: {
    loanID: function (value) {
      // Whenever the prop "name" changes, then we will console log its value.
      console.log(value);
    },
  },

  async created() {
    this.checkLogin();
    try {
      const attractions = await axios.get(
        import.meta.env.VITE_API_URL + "api/attractions"
      );
    }
    catch (err) {
      if (err.response.status == 401) {
        this.$router.push({ name: "Login" });
      }
    }

    for (const att of attractions.data.data) {
      const location = att.name;
      const attractionId = att.attractionId;
      const maxPassesPerLoan = att.maxPassesPerLoan;
      const numOfAccompanyingGuests = att.numAccompanyingGuests;
      const photoURL = att.backgroundImage;
      this.locations[location] = [
        location,
        attractionId,
        maxPassesPerLoan,
        numOfAccompanyingGuests,
        photoURL,
      ];
    }
  },
  computed: {
    attributes() {
      return [
        ...this.ticketInformation.map((ticketInfo) => ({
          dates: ticketInfo.dates,
          dot: {
            color: ticketInfo.color,
            class: ticketInfo.isComplete ? "opacity-75" : "",
          },
          popover: {
            label: ticketInfo.description,
          },
          customData: ticketInfo,
        })),
      ];
    },
  },
  methods: {
    async populateNoOfTickets(): Promise<any> {
      this.numberofPasses = [];
      const maxPasses = this.attractionDetails["id"][2];

      for (let i = 1; i <= maxPasses; i++) {
        this.numberofPasses.push(i);
      }
    },
    async populateAttractionDetails(): Promise<any> {
      this.numOfAccompanyingGuests = this.attractionDetails["id"][3];
      this.attractionId = this.attractionDetails["id"][1];
      this.currentBackground = this.attractionDetails["id"][4];
      this.title = this.attractionDetails["id"][0];
    },
    checkLogin(): LoginData | undefined {
      const staffIdStr = localStorage.getItem("staffId");
      const role = localStorage.getItem("role");

      if (staffIdStr === null || role === null) {
        this.$router.push({ name: "Login" });
      } else {
        this.staffId = parseInt(staffIdStr);

        return {
          staffId: this.staffId,
          role: role,
        };
      }
    },
    async addLoan(): Promise<any> {
      const staffId = this.staffId;
      const attractionId = this.attractionId;
      const numPassesSelected = this.numPassesSelected["pass"];
      const day = this.dateSelected.getDate();
      const month = this.dateSelected.getMonth() + 1;
      const year = this.dateSelected.getFullYear();

      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "api/loan/add",
          {
            staffId: staffId, //retrieve from cookie
            attractionId: attractionId,
            numPasses: parseInt(numPassesSelected),
            yyyy: year,
            mm: month,
            dd: day,
          }
        );
        console.log(res);
        console.log("200");
        this.loanID = res.data.data[0]["loanId"];
        console.log(this.loanID);

        this.$router.push({
          name: "Booking Confirmation",
          params: {
            loanID: this.loanID,
          },
        });
        return res.data;
      } catch (err) {
        if (err.response.status == 401) {
          this.$router.push({ name: "Login" });
        }
        if (err.response.status == 500) {
          this.errorMsg = "Please check all input fields and ensure you have not exceed the maximum number of passes per month.";
        } else if (err.response.status == 400) {
          this.errorMsg = "You are not allowed to book pass, please check with the HR for access rights."
        }
        else {
          this.errorMsg = "An error has occured. Please try again later.";
        }
        this.makeToast(this.errorMsg);
        return {
          code: err,
        };
      }
    },
    makeToast(errorMsg: string) {
      let toast = document.createElement("sectionheader");
      toast.innerHTML = `
      <div id="hjvvhj" class="toast mb-3 me-2" style="z-index:999" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="10000">
        <div class="toast-header" data-bs-delay="10000">
          <strong class="me-auto">Error Message</strong>
          <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">` + errorMsg +
          `
        </div>
      </div>
      `;

      const html = document.querySelector("#sectionheader");
      html.appendChild(toast);

      toast = document.querySelector("#hjvvhj");

      toast = new bootstrap.Toast(toast);
      toast.show();
    },
  },
});
</script>

<style>
#sectionheader {
  width: 100%;
  background-size: cover;
  padding-top: 300px;
  padding-bottom: 300px;
  background-size: 100%;
  position:absolute;
}

.main {
  margin-bottom: 10px;
}
.title {
  color: rgb(247, 247, 132);
  font-family: "Trebuchet MS", sans-serif;
  font-weight: bold;
  font-size: 125px;
  letter-spacing: 0;
  margin-bottom: 10px;
  padding-bottom: 10px;
  line-height: 1.4;
  margin-left: 100px;
}
.toast {
  z-index: 9998;
  position: absolute;
  bottom: 0;
  right: 0;
}

.titledescription {
  color: white;
  font-family: "Trebuchet MS", sans-serif;
  font-weight: 400;
  letter-spacing: 0;
  line-height: 1.6;
  margin-left: 100px;
  padding-left: 50px;
  min-height: 54px;
  width: 1500px;
}

.calendarStyle {
  position: absolute;
  margin-top: 25px;
}
.bookingdetails {
  align-items: center;
  display: flex;
  margin-left: 150px;
  border-radius: 30px;
  box-shadow: 0px 12px 14px;
  padding: 5px;
  padding-top: 15px;
  background-color: white;
  border: 1px none;
  width: 900px;
  height: 90px;
}

#group-location {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 450px;
  margin-left: 20px;
  margin-right: 10px;
}

#group-calendar {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 300px;
  padding-left: 10px;
  margin-right: 30px;
}

#group-date {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 250px;
  padding-left: 5px;
  margin-right: 40px;
}

#group-submit {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 200px;
  padding-left: 20px;
  margin-right: 20px;
}

#location-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

#calendar-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

#date-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

.shadow {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

.btn-submit {
  background-color: #f37931!important;
  letter-spacing: 0;
  line-height: 24px;
  color: white!important;;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  padding-left: 20px;
}
.btn-submit:hover {
  background-color: #d72255!important;
  color: white;
}
</style>
