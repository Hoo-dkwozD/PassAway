<template>
  <!-- how many passes are available for this date -->
  <div>
    <v-calendar :attributes="attributes"> </v-calendar>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import "v-calendar/dist/style.css";
type ticketInformation = {
  description: String;
  isComplete: boolean;
  dates: Date;
  color: String;
};
// type date = {
//   weekdays: number
// }
interface Data {
  //array of custom type Array<todos>
  ticketInformation: ticketInformation[];
}
export default defineComponent({
  data(): Data {
    //contain dates where there are passes booked
    const ticketInformation = [
      {
        description: "2 Passes left",
        isComplete: false,
        dates: new Date(2022, 9, 1), // date should be array of objects where one object is one date, format month year and day into an object
        color: "red",
      },
      {
        description: "1 Pass left",
        isComplete: false,
        dates: new Date(2022, 9, 2), // date should be array of objects where one object is one date
        color: "red",
      },
    ];
    return {
      ticketInformation,
    };
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