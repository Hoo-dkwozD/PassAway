<template>
  <div class="container-fluid">
    <div
      id="sectionheader"
      :style="{ backgroundImage: `url(${currentBackground})` }"
    >
      <div class="main">
        <h1 class="title">Singapore Zoo</h1>
        <h3 class="titledescription">
          Each physical pass equates to 2 entries to the attraction, you are
          entitled to 2 passes a month.
        </h3>
      </div>

      <div class="bookingdetails">
        <div class="dropdown" id="group-location">
          <select v-model="attraction" class="form-select">
            <option disabled value="">Attractions</option>
            <option v-for="location in locations" :value="{ location }">
              {{ location }}
            </option>
          </select>
        </div>

        <div class="dropdown" id="group-calendar">
          <button
            class="form-select"
            id="calendar-details"
            data-bs-toggle="dropdown"
            @click="showCalendar = !showCalendar"
          >
            {{ dateSelected }}
          </button>
          <!-- <Calendar
            class="calendarStyle"
            v-if="showCalendar"
            @click="showCalendar = !showCalendar"
          /> -->
          <DatePicker
            placeholder="Calendar"
            v-model="dateSelected"
            class="calendarStyle"
            mode="date"
            v-if="showCalendar"
            @click="showCalendar = !showCalendar"
          />
        </div>

        <div class="dropdown" id="group-date">
          <select v-model="numPassesSelected" class="form-select">
            <option disabled value="">Passes</option>
            <option v-for="pass in numberofPasses" :value="{ pass }">
              {{ pass }}
            </option>
          </select>
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
import axios from "axios";
import { DatePicker } from "v-calendar";
import { defineComponent } from "vue";
import Calendar from "../components/Calendar.vue";
import { useCounterStore } from "../stores/counter";

// Typings
interface Data {
  locations: string[];
  attraction: string;
  dateSelected: Date;
  numPassesSelected: string;
  numberofPasses: number[];
  type: boolean;
  showCalendar: boolean;
  currentBackground: string;
  store: any;
}

export default defineComponent({
  data(): Data {
    return {
      type: true,
      attraction: "",
      locations: ["Singapore Zoo", "Gardens By the Bay", "USS"],
      dateSelected: new Date().toLocaleDateString(),
      numPassesSelected: "",
      numberofPasses: [],
      showCalendar: false,
      currentBackground: "/assets/header.png",
      store: useCounterStore(),
    };
  },
  async created() {
    const MAX_PASS = 3;

    for (let i = 0; i < MAX_PASS; i++) {
      this.numberofPasses.push(i);
    }
  },

  methods: {
    getAttractionId(attractionName: string) {
      return axios.get(
        import.meta.env.VITE_API_URL + "attraction/get/" + attractionName
      );
    },
    async addTaskBackend(
      staffId: string,
      attraction: string,
      numPassesSelected: string,
      dateSelected: Date
    ): Promise<JSONResponse | AddTaskJSONResponse | any> {
      const attractionId = this.getAttractionId(attraction);
      const date = dateSelected.getDate();
      const month = dateSelected.getMonth();
      const year = dateSelected.getFullYear();

      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "loan/add",
          {
            staffId: parseInt(staffId),
            attractionId: parseInt(attractionId),
            numPasses: parseInt(numPassesSelected),
            yyyy: year,
            mm: month,
            dd: date,
          }
        );
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
  props: {},
  components: {
    Calendar,
    DatePicker,
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
  white-space: nowrap;
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
  margin-left: 100px;
  border-radius: 30px;
  box-shadow: 0px 12px 14px;
  padding: 10px;
  background-color: white;
  border: 1px none;
  width: 850px;
  height: 100px;
}

#group-location {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 450px;
  margin-left: 20px;
  margin-right: 60px;
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
  margin-right: 40px;
}

#group-date {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 250px;
  padding-left: 10px;
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

.btn-submit {
  background-color: orange !important;
  letter-spacing: 0;
  line-height: 24px;
  white-space: nowrap;
  padding-left: 20px;
}
</style>
