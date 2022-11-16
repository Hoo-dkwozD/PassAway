<template>
  <Navbar></Navbar>
  <Attraction></Attraction>
</template>

<script lang="ts">
import {defineComponent} from "vue";

import Attraction from "../components/Attraction.vue";
import Navbar from "../components/Navbar.vue";

export default defineComponent({
  components: {
    Attraction,
    Navbar
  },
});
</script>