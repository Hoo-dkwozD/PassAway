<template>
  <Navbar></Navbar>
  <Booking></Booking>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Booking from "@/components/Booking.vue";
import Navbar from '@/components/Navbar.vue';

export default defineComponent({
  components: {
    Booking,
    Navbar
  },
});
</script>
