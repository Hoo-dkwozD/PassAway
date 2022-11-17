<template>
  <NavBar></NavBar>
  <div
    class="container-fluid p-0 mx-0 position-relative w-100 d-flex flex-column"
    id="top"
  >
    <div
      id="sectionheader"
      class="mx-0 bg-light vh-100"
      :style="{ backgroundImage: `url(${currentBackground})` }"
    >
      <div class="row mt-5">
        <h1 class="title px-0 w-auto">{{ title }}</h1>
        <h3 v-if="attractionDetails['name']" class="text-start px-0" style="margin-left: 100px;">
          You are entitled to {{ attractionDetails["maxLoansPerMonth"] }} passes a
          month. 
        </h3>
        <h3 v-if="attractionDetails['name']" class="text-start px-0" style="margin-left: 100px;">
          Each pass entitles you to
          {{ attractionDetails["numAccompanyingGuests"] }} accompanying guests to
          {{ attractionDetails["name"] }}
        </h3>
      </div>
      <div class="bookingdetails px-2 mx-5">
        <div class="dropdown" id="group-location">
          <select
            v-model="attractionId"
            class="form-select shadow"
            @change="() => {
              updateAttrDetails();
              populateNumPasses();
            }"
          >
            <option disabled value="">Attractions</option>
            <option
              v-for="value, idx in allAttractions"
              :key="idx"
              :value="value['attractionId']"
            >
              {{ value["name"] }}
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
              <div v-if="showCalendar" class="position-absolute">
                <v-date-picker
                  v-model="dateSelected"
                  :attributes="attributes"
                  @dayclick="showCalendar = false"
                  :min-date="new Date()"
                  :max-date="maxDate"
                  :disabled-dates="disabledDates"
                  @update:fromPage="populateNumPassesPage"
                />
              </div>
            </div>
          </div>
        </div>

        <div class="dropdown" id="group-date">
          <select v-model="numPassesSelected" class="form-select shadow">
            <option disabled value="">Passes</option>
            <option
              v-for="pass in attractionDetails['maxPassesPerLoan']"
              :key="pass"
              :value="pass"
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
import { defineComponent } from "vue";
import axios from "axios";
import NavBar from "../components/Navbar.vue";
import "v-calendar/dist/style.css";
type availablePassesInfo = {
  description: string;
  // isComplete: boolean;
  date: Date;
  color: string;
};

// Typings
interface Data {
  addedMMYYYY: any;
  disabledDates: Date[];
  maxDate: Number;
  allAttractions: any;
  locations: object;
  title: string;
  attraction: string;
  attractionDetails: any;
  dateSelected: Date;
  numOfAccompanyingGuests: number;
  numPassesSelected: number;
  numberofPasses: number[];
  type: boolean;
  showCalendar: boolean;
  currentBackground: string;
  loanID: number;
  attractionId: number;
  staffId: number;
  availablePassesInfo: availablePassesInfo[];
  errorMsg: string;
}

interface LoginData {
  staffId: number;
  role: string;
}

export default defineComponent({
  components: {
    NavBar,
  },
  data(): Data {
    return {
      addedMMYYYY: {},
      disabledDates: [],
      maxDate: new Date().setMonth(new Date().getMonth() + 8),
      allAttractions: {},
      attractionDetails: {},
      attractionId: 0,
      // brant codes
      type: true,
      attraction: "",
      locations: {},
      title: "Book Your Passes Now!",
      dateSelected: new Date(),
      numOfAccompanyingGuests: 0,
      numPassesSelected: 0,
      numberofPasses: [],
      showCalendar: false,
      currentBackground:
        "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935",
      loanID: 0,
      errorMsg: "",
      staffId: 0,
      availablePassesInfo: [],
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
      const res = await axios.get(
        import.meta.env.VITE_API_URL + "api/attractions",
        {
          headers: {
            'authorization': `${localStorage.getItem("token")}`
          }
        }
      );
      for (const att of res.data.data) {
        this.allAttractions[att.attractionId] = att;
      }
      // console.log(this.allAttractions);
    } catch (err: any) {
      if (err.response.status == 401) {
        this.$router.push({ name: "Login" });
      }
    }
  },
  computed: {
    attributes() {
      return [
        ...this.availablePassesInfo.map((info) => ({
          dates: info.date,
          dot: {
            color: info.color,
            // class: info.isComplete ? "opacity-75" : "",
          },
          popover: {
            label: info.description,
          },
          customData: info,
        })),
      ];
    },
  },
  methods: {
    updateAttrDetails() {
      this.attractionDetails = this.allAttractions[this.attractionId];
      // console.log(this.attractionDetails);
    },
    async populateNumPasses(): Promise<any> {
      this.addedMMYYYY = {};
      const month = this.dateSelected.getMonth() + 1;
      const year = this.dateSelected.getFullYear();
      let monthStr: string = month < 10 ? `0${month}` : `${month}`;
      let yearStr: string = year + "";
      let mmYYYY: string = monthStr + yearStr;

      try {
        const res = await axios.get(
          import.meta.env.VITE_API_URL +
            `api/loan/available-passes?aId=${this.attractionId}&yyyy=${year}&mm=${month}`,
            {
              headers: {
                'authorization': `${localStorage.getItem("token")}`
              }
            }
        );
        const monthNumPassObj = res.data.data;
        this.availablePassesInfo = [];
        for (const monthStr in monthNumPassObj) {
          const numAvailPasses = monthNumPassObj[monthStr];
          if (numAvailPasses == 0) {
            this.disabledDates.push(new Date(monthStr));
          } else {
            this.availablePassesInfo.push({
              description: `${numAvailPasses} Passes left`,
              date: new Date(monthStr),
              color: numAvailPasses < 10 ? "red" : "green",
            });
          }
        }
        this.addedMMYYYY[mmYYYY] = true;
      } catch (err: any) {
        // console.log(err)
        if (err.response.status == 401) {
          this.$router.push({ name: "Login" });
        }
      }
    },
    async populateNumPassesPage(page: any): Promise<any> {
      // console.log(page);
      const month = page.month;
      const year = page.year;
      let monthStr: string = month < 10 ? `0${month}` : `${month}`;
      let yearStr: string = year + "";
      let mmYYYY: string = monthStr + yearStr;
      if (this.addedMMYYYY[mmYYYY]) {
        return; // skip if date has previously been added
      }

      try {
        const res = await axios.get(
          import.meta.env.VITE_API_URL +
            `api/loan/available-passes?aId=${this.attractionId}&yyyy=${year}&mm=${month}`,
          {
            headers: {
              'authorization': `${localStorage.getItem("token")}`
            }
          }
        );
        const monthNumPassObj = res.data.data;
        this.availablePassesInfo = [];
        for (const monthStr in monthNumPassObj) {
          const numAvailPasses = monthNumPassObj[monthStr];
          if (numAvailPasses == 0) {
            this.disabledDates.push(new Date(monthStr));
          } else {
            this.availablePassesInfo.push({
              description: `${numAvailPasses} Passes left`,
              date: new Date(monthStr),
              color: numAvailPasses < 10 ? "red" : "green",
            });
          }
        }
        this.addedMMYYYY[mmYYYY] = true;
        // console.log(res.data.data);
      } catch (err: any) {
        // console.log(err)
        if (err.response.status == 401) {
          this.$router.push({ name: "Login" });
        }
      }
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
      if (this.attractionId == 0) {
        this.errorMsg += "Please select an attraction.<br>";
      }
      if (this.dateSelected == null) {
        this.errorMsg += "Please select a date.<br>";
      }
      if (this.numPassesSelected == 0) {
        this.errorMsg += "Please select number of passes.<br>";
      }
      if (this.errorMsg != "") {
        this.makeToast(this.errorMsg);
        this.errorMsg = "";
        return;
      }
      const staffId: number = this.staffId;
      const attractionId: number = this.attractionId;
      const numPassesSelected: number = this.numPassesSelected;
      const day: number = this.dateSelected.getDate();
      const month: number = this.dateSelected.getMonth() + 1;
      const year: number = this.dateSelected.getFullYear();
      console.log(numPassesSelected, typeof numPassesSelected);
      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "api/loan/add",
          {
            staffId: staffId, //retrieve from cookie
            attractionId: attractionId,
            numPasses: numPassesSelected,
            yyyy: year,
            mm: month,
            dd: day,
          }
        );
        this.loanID = res.data.data[0]["loanId"];
        console.log(this.loanID);

        this.$router.push({
          name: "personal bookings",
        });
        return res.data;
      } catch (err: any) {
        console.log(err);
        if (err.response.status == 401) {
          this.$router.push({ name: "login" });
        }
        if (err.response.status == 500) {
          this.errorMsg = err.response.data + "<br>";
        } else if (err.response.status == 400) {
          this.errorMsg =
            "You are not allowed to book pass, please check with the HR for access rights.<br>";
        } else {
          this.errorMsg = "An error has occured. Please try again later.<br>";
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
      <div id="hjvvhj" class="toast my-3 me-2" style="position:absolute; z-index:999; right:5px; top:5px; max-height: min-content;" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="5000">
        <div class="toast-header" data-bs-delay="5000">
          <strong class="me-auto">Error Message</strong>
          <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">${errorMsg}</div>
      </div>
      `;

      const html = document.querySelector("#sectionheader") as HTMLElement;
      html.appendChild(toast);

      toast = document.querySelector("#hjvvhj") as HTMLElement;

      toast = new bootstrap.Toast(toast);
      toast.show();
      this.errorMsg = "";
    },
  },
});
</script>

<style>
body {
  position: relative;
}

.toast {
  z-index: 9998;
  position: absolute;
  bottom: 0;
  right: 0;
}

.titledescription {
  color: black;
  font-family: "Trebuchet MS", sans-serif;
  /* letter-spacing: 0; */
  /* line-height: 1.6; */
}

.calendarStyle {
  /* position: absolute; */
  margin-top: 25px;
}

.bookingdetails {
  align-items: center;
  display: flex;
  /* margin-left: 150px; */
  border-radius: 30px;
  box-shadow: 0px 12px 14px;
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
  /* margin-left: 20px; */
  margin-right: 10px;
}

#group-calendar {
  /* position: relative; */
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
  background-color: #f37931 !important;
  letter-spacing: 0;
  line-height: 24px;
  color: white !important;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  padding-left: 20px;
}

.btn-submit:hover {
  background-color: #d72255 !important;
  color: white;
}
</style>
