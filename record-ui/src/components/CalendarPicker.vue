<template>
  <!-- how many passes are available for this date -->
  <!-- div block with hi -->
  <div>
    <div class="position-absolute">
      <button
        id="calendar-details"
        class="form-select shadow"
        @click="showCalendar = !showCalendar"
      >
        {{
          selectedDay != null ? selectedDay.toDateString() : "No date selected"
        }}
      </button>
      <div v-if="showCalendar">
        <v-date-picker
          v-model="populateDate"
          :attributes="attributes"
        ></v-date-picker>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import "v-calendar/dist/style.css";

type ticketInformation = {
  description: string;
  isComplete: boolean;
  dates: Date;
  color: string;
};


// type date = {
//   weekdays: number
// }
interface Data {
  //array of custom type Array<todos>
  ticketInformation: ticketInformation[];
  selectedDay: Date;
  showCalendar: boolean;
}
export default defineComponent({
  name: "CalendarPicker",
  data(): Data {
    //contain dates where there are passes booked

    let apiResponse = [{
      date: "2022-11-1",
      description: "some description"
    }]

    const ticketInformation2 = apiResponse.map( apiResponseElement => {
      return {
        description: apiResponseElement.description,
        isComplete: false,
        dates: new Date(apiResponseElement.date),
        color: "red"
      }
    })


    const ticketInformation = [
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
    ];
    // const selectedDay = new Date()
    return {
      ticketInformation,
      selectedDay: new Date(),
      showCalendar: false,
    };
  },
  methods: {
    //   closeDateMenu(){
    //       this.selectedDay= this.selectedDay.format('dddd, MMMM Do YYYY');
    // }
  },
  props: ["date"],
  computed: {
    populateDate: {
      get() {
        return this.date;
      },
      set(val: any) {
        this.$emit("change", val);
      },
    },
  },
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
});
</script>


<style>
#calendar-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
  width: 180px;
  padding-right: 30px;
}

.shadow {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
</style>
