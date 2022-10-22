<template>
  <div class="container-fluid">
    <div
      id="sectionheader"
      :style="{ backgroundImage: `url(${currentBackground})` }"
    >
      <div class="main">
        <!-- <img v-if="currentBackground == 'SingaporeZoo'" src="/assets/header.png"/>
        <img v-else-if="currentBackground == 'sportsschool'" src="/assets/sportsschool.png"/>
        <img v-else src="/assets/gardensbybay.png"/> -->
        <h1 class="title">Singapore Zoo</h1>
        <h3 class="titledescription">
          Each physical pass equates to 2 entries to the attraction, you are
          entitled to 2 passes a month.
        </h3>
      </div>

      <div class="bookingdetails">
        <div class="dropdown" id="group-location">

          <select v-model="locationSelected" class="form-select">
            <option disabled value="">Attractions</option>
            <option v-for="location in locationSelected" :value="{location}">{{ location }}</option>
          </select>

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
          <Calendar class="calendarStyle" v-if="showCalendar" ref="calendar" />
          <v-date-picker v-model="dateSelected" />
        </div>

        <div class="dropdown" id="group-date">

          <select v-model="numPasses" class="form-select">
            <option disabled value="">Passes</option>
            <option v-for="pass in numberofPasses" :value="{pass}">{{ pass }}</option>
          </select>
        </div>

        <div id="group-submit">
          <router-link to="/bookingconfirmation">
            <button
              type="button"
              class="btn btn-submit btn-lg"
              @click="
                addTaskBackend(staffId, attractionId, numPasses, yyyy, mm, dd)
              "
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
import { defineComponent } from "vue";
import axios from "axios";

// Typings
// Typings
interface Data {
  locationSelected: string[];
  dateSelected: string;
  numberofPasses: number[];
  type: boolean;
  showCalendar: boolean;
  currentBackground: string;
}

export default defineComponent({
  data() {
    const staffId: any = "";
    const attractionId: any = "";
    const numPasses: any = "";
    const yyyy: any = "";
    const mm: any = "";
    const dd: any = "";

    return {
      staffId: staffId,
      attractionId: attractionId,
      numPasses: numPasses,
      yyyy: yyyy,
      mm: mm,
      dd: dd,
      type: true,
      locationSelected: ["Singapore Zoo", "Gardens By the Bay", "USS"],
      dateSelected: "",
      numberofPasses: [1, 2],
      showCalendar: false,
      currentBackground: "/assets/header.png",
    };
  },
  props: {},
  async created() {},
  methods: {
    async addTaskBackend(
      staffId: string,
      attractionId: string,
      numPasses: string,
      yyyy: string,
      mm: string,
      dd: string
    ): Promise<JSONResponse | AddTaskJSONResponse | any> {
      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "loan/add",
          {
            staffId: parseInt(staffId),
            attractionId: parseInt(attractionId),
            numPasses: parseInt(numPasses),
            yyyy: parseInt(yyyy),
            mm: parseInt(mm),
            dd: parseInt(dd),
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
    isAddTaskJSONResponse(obj: any): obj is AddTaskJSONResponse {
      return "data" in obj;
    },
    isRemoveTaskJSONResponse(obj: any): obj is RemoveTaskJSONResponse {
      return "message" in obj;
    },
    async complete(e: Event, idx: number) {
      const response: JSONResponse | RemoveTaskJSONResponse | any =
        await this.removeTaskBackend(this.list.items[idx]);
      this.localList.pop();

      if (response.code === 200 && this.isRemoveTaskJSONResponse(response)) {
        this.list.remove(idx);
      }
    }
  },
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
