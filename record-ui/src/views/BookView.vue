<template>
  <NavBar></NavBar>
  <Booking></Booking>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Booking from "../components/Booking.vue";
import NavBar from '../components/Navbar.vue';

export default defineComponent({
  components: {
    Booking,
    NavBar
  },
});
</script>
