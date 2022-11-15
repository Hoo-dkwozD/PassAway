<template>
    <Navbar></Navbar>
    <personal-bookings-view></personal-bookings-view>
  </template>
  
  <script lang="ts">
  import PersonalBookingsView from '@/components/PersonalBookings.vue';
  import {defineComponent} from 'vue'
  import Navbar from '../components/Navbar.vue'
  export default defineComponent({
    name: 'BookingConfirmationView',
    components: { PersonalBookingsView },
    data() {
      return {};
    },
    methods: {
      
    }
  });
  </script>
  