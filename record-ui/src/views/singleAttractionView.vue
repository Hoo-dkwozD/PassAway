<template>
  <Navbar></Navbar>
  <SingleAttraction></SingleAttraction>
</template>

<script lang="ts">
import {defineComponent} from "vue";

import singleAttraction from "../components/singleAttraction.vue";
import Navbar from "../components/Navbar.vue";

export default defineComponent({
  name: 'SingleAttractionView',
  components: {
    singleAttraction,
    Navbar
  },
});
</script>