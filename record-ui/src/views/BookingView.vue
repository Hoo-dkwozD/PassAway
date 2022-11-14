<template>
  <Navbar></Navbar>
  <div class="container">
    <div class="row" id="sectionheader">
      <h1>Singapore Zoo</h1>
      <h3>Each physical pass equates to 2 entries to the attraction, you are entitled to condition</h3>
    </div>
    <div class="bookingdetails">
      <div class="location">
        <h4>Location</h4>
        <div>{{locationSelected}}</div>
        <i class="fas fa-angle-down"></i>
      </div>

      <div class="bookingdetails">
        <div class="dropdown" id="group-location">
          <button
            class="btn btn-outline-secondary dropdown-toggle btn-lg"
            id="location-details"
            type="button"
            data-bs-toggle="dropdown"
          >
            Location
          </button>
          <ul class="dropdown-menu">
            <li v-for="location in locationSelected">
              <a class="dropdown-item">{{ location }}</a>
            </li>
          </ul>
        </div>

        <div class="dropdown" id="group-calendar">
          <button
            class="btn btn-outline-secondary dropdown-toggle btn-lg"
            id="calendar-details"
            type="button"
            data-bs-toggle="dropdown"
            @click="showCalendar = !showCalendar"
          >
            Calendar
          </button>
          <Calendar class="calendarStyle" v-if="showCalendar" />
        </div>

        <div class="dropdown" id="group-date">
          <button
            class="btn btn-outline-secondary dropdown-toggle btn-lg"
            id="date-details"
            type="button"
            data-bs-toggle="dropdown"
          >
            Pass
          </button>
          <ul class="dropdown-menu">
            <li v-for="pass in numberofPasses">
              <a class="dropdown-item">{{ pass }}</a>
            </li>
          </ul>
        </div>

        <div id="group-submit">
          <router-link to="/bookingconfirmation">
            <button type="button" class="btn btn-submit btn-lg">
              Book Now
            </button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import type { ComponentPublicInstance } from "vue";
import { defineComponent } from "vue";
import Calendar from "../components/Calendar.vue";
import { useCounterStore } from "../stores/counter";
import Navbar from '../components/Navbar.vue'
// Typings
interface Data {
  locationSelected: String[];
  dateSelected: String;
  numberofPasses: Number[];
  type: boolean;
  showCalendar: boolean;
  currentBackground: String;
  store: any;
}

export default defineComponent({
  data(): Data {
    return {
      type: true,
      locationSelected: ["SingaporeZoo", "Gardens By the Bay", "USS"],
      dateSelected: "",
      numberofPasses: [],
      showCalendar: false,
      currentBackground: "/assets/header.png",
      store: useCounterStore(),
    };
  },
  components:{
    Navbar
  },
  async created() {
    const MAX_PASS = 3;

    for (let i = 0; i < MAX_PASS; i++) {
      this.numberofPasses.push(i);
    }
  },

  methods: {},
  props: {},
});
</script>

<style>
#sectionheader {
  width: 100%;
  background-size: cover;
  padding-top: 300px;
  padding-bottom: 300px;
}

.main {
  margin-bottom: 10px;
}
.title {
  color: rgb(247, 247, 132);
  font-family: Arial, sans-serif;
  font-weight: bold;
  letter-spacing: 0;
  line-height: 1.6;
  margin-left: 100px;
  white-space: nowrap;
}
.titledescription {
  color: white;
  font-family: Arial, sans-serif;
  font-weight: 400;
  letter-spacing: 0;
  line-height: 1.6;
  margin-left: 100px;
  min-height: 54px;
  width: 405px;
}

.calendarStyle {
  position: absolute;
  margin-top: 50px;
}
.bookingdetails {
  align-items: center;
  display: flex;
  margin-left: 100px;
  border-radius: 30px;
  box-shadow: 0px 12px 14px;
  padding: 10px;
  background-color: white;
  border: 1px none;
  width: 800px;
  height: 200px;
}

#group-location {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 150px;
  margin-left: 20px;
  margin-right: 50px;
}

#group-calendar {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 150px;
  padding-left: 20px;
  margin-right: 50px;
}

#group-date {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 132px;
  padding-left: 20px;
  margin-right: 50px;
}

#group-submit {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 132px;
  padding-left: 20px;
  margin-right: 50px;
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

.btn-submit {
  background-color: orange !important;
  letter-spacing: 0;
  line-height: 24px;
  white-space: nowrap;
  padding-left: 20px;
}
</style>
