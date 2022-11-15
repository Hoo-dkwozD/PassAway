<template>
  <!-- how many passes are available for this date -->
  <div>
    <v-calendar :attributes="attributes" @dayclick="dayClicked">
    </v-calendar>
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
  selectedDay: null;
}
export default defineComponent({
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
    const selectedDay = null;
    return {
      ticketInformation,
      selectedDay,
    };
  },
  methods: {
    dayClicked(day: any): void {
      this.selectedDay = day;
    },
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
});
</script>


<style>
</style>
