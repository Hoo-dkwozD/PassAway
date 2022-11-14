<template>
  <div class="container-fluid px-0">
    <div
      id="sectionheader"
      :style="{ backgroundImage: `url(${currentBackground})` }"
      class="row"
    >
      <div class="main container-fluid">
        <h1 class="title row">{{ title }}</h1>
        <h3 class="titledescription row">
          You are entitled to 2 passes a month. {{}}
        </h3>
        <h3 v-if="attractionDetails.length != 0" class="titledescription row">
          Each pass entitles you to {{ numOfAccompanyingGuests }} accompanying
          guests to {{ title }}
        </h3>
      </div>

      <div class="bookingdetails container-fluid">
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
          <calendar-picker />
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
          <!-- <router-link
          to="{name: 'BookingConfirmation', params: {loanID}}"
          > -->
          <button
            type="submit"
            class="btn btn-submit btn-lg"
            @click="addLoan()"
          >
            Book Now
          </button>
          <!-- </router-link> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { DatePicker } from "v-calendar";
import { defineComponent } from "vue";
import CalendarPicker from "./CalendarPicker.vue";

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
  staffId: string;
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
      staffId: "",
    };
  },

  async created() {
    this.staffId = document.cookie;
    const attractions = await axios.get(
      import.meta.env.VITE_API_URL + "api/attraction/list"
    );

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
  computed: {},
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
      // this.currentBackground = this.attractionDetails.id[4];
      this.attractionId = this.attractionDetails["id"][1];
      this.title = this.attractionDetails["id"][0];
    },
    async addLoan(): Promise<any> {
      const staffId = this.staffId; //need to call api, json response that returns me {code: 200, data: {staffId: 1}}
      const attractionId = this.attractionId;
      const numPassesSelected = this.numPassesSelected;
      const dateArr = this.dateSelected
        .toISOString()
        .substring(0, 10)
        .split("-");
      const day = dateArr[2];
      const month = dateArr[1];
      const year = dateArr[0];

      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "api/loan/add",
          {
            staffId: parseInt("1"), //retrieve from cookie
            attractionId: attractionId,
            numPasses: parseInt(numPassesSelected),
            yyyy: parseInt(year),
            mm: parseInt(month),
            dd: parseInt(day),
          }
        );
        this.loanID = res.data.loanId;
        console.log(res.data);
        console.log("200");
        return res.data;
      } catch (err) {
        return {
          code: err,
        };
      }
    },
  },
  components: {
    "calendar-picker": CalendarPicker,
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
  width: 850px;
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
  background-color: #f37931;
  letter-spacing: 0;
  line-height: 24px;
  color: black;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  padding-left: 20px;
}

.btn-submit:hover {
  background-color: #d72255;
  color: white;
}
</style>
