<template>
  <Navbar></Navbar>
  <personal-bookings></personal-bookings>
</template>
  
  <script lang="ts">
import PersonalBookings from "@/components/PersonalBookings.vue";
import { defineComponent } from "vue";
import Navbar from "../components/Navbar.vue";
export default defineComponent({
  name: "PersonalBookingsView",
  components: { PersonalBookings, Navbar },
  data() {
    return {};
  },
  methods: {},
});
</script>
  