<template>
    <div>
      <v-calendar class="calendarStyle">
        <v-date-picker v-model="date"/>
      </v-calendar>
    </div>
  </template>
  
  <script lang="ts">
  import {defineComponent} from 'vue';
  import {ref} from 'vue';
  import 'v-calendar/dist/style.css'
  interface Data {
    date: String;
  }
  export default defineComponent({
    data(): Data{
      return{
        date: "",
      };
    },
  })
  </script>
  
  
  <style>
  </style>