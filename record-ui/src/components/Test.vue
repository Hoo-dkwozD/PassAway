<template>
  <div class="container-fluid px-0">
    <div
      id="sectionheader"
      :style="{ backgroundImage: `url(${currentBackground})` }"
      class="row"
    >
      <div class="main container-fluid">
        <h1 class="title row">Singapore Zoo</h1>
        <h3 class="titledescription row">
          Each physical pass equates to 2 entries to the attraction, you are
          entitled to 2 passes a month.
        </h3>
      </div>

      <div class="bookingdetails container-fluid">
        <div class="dropdown" id="group-location">
          <select
            v-model="attraction"
            class="form-select shadow"
            @change="populateNoOfTickets()"
          >
            <option disabled value="">Attractions</option>
            <option
              v-for="(id, location) in locations"
              :value="{ location }"
              @onselect="populateNoOfTickets()"
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
            <option v-for="pass in numberofPasses" :value="{ pass }">
              {{ pass }}
            </option>
          </select>
        </div>

        <div id="group-submit">
          <router-link
            to="{name: 'BookingConfirmation', params: { id: {{loanID}} }}"
          >
            <button
              type="submit"
              class="btn btn-submit btn-lg"
              @click="addLoan()"
            >
              Book Now
            </button>
          </router-link>
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
  attraction: string;
  dateSelected: Date;
  numPassesSelected: string;
  numberofPasses: number[];
  type: boolean;
  showCalendar: boolean;
  currentBackground: string;
  loanID: number;
  attractionId: number;
}

export default defineComponent({
  data(): Data {
    return {
      type: true,
      attraction: "",
      locations: {},
      dateSelected: new Date(),
      numPassesSelected: "",
      numberofPasses: [],
      showCalendar: false,
      currentBackground: "/assets/header.png",
      loanID: 0,
      attractionId: 0,
    };
  },

  async created() {
    const attractions = await axios.get(
      import.meta.env.VITE_API_URL + "api/attraction/list"
    );

    for (let attraction of attractions.data.data) {
      const location = attraction.name;
      const attractionId = attraction.attractionId;
      const maxPassesPerLoan = attraction.maxPassesPerLoan;
      this.locations[location] = [attractionId, maxPassesPerLoan];
    }
  },
  computed: {},
  methods: {
    async populateNoOfTickets(): Promise<any> {
      this.numberofPasses = [];
      const selectedAttraction = this.attraction["location"];
      const maxPasses = this.locations[selectedAttraction][1];

      for (let i = 1; i <= maxPasses; i++) {
        this.numberofPasses.push(i);
      }
    },
    async addLoan(): Promise<any> {
      const staffId = this.staffId;
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
          import.meta.env.VITE_API_URL + "loan/add",
          {
            staffId: parseInt(staffId), //retrieve from cookie
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
  background-image: url("assets/header.png");
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
  width: 700px;
}

.calendarStyle {
  position: absolute;
  margin-top: 25px;
}
.bookingdetails {
  align-items: center;
  display: flex;
  margin-left: 90px;
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
  color:black;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  padding-left: 20px;
}

.btn-submit:hover {
  background-color: #d72255;
  color: white;
}
</style>
