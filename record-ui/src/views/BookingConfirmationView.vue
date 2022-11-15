<template>
  <Navbar></Navbar>
  <booking-confirmation></booking-confirmation>
</template>

<script lang="ts">
import BookingConfirmation from "../components/BookingConfirmation.vue"
import {defineComponent} from 'vue'
import Navbar from '../components/Navbar.vue'
export default defineComponent({
  name: 'BookingConfirmationView',
  components: { BookingConfirmation, Navbar },
  data() {
    return {};
  },
  methods: {
    
  }
});
</script>
