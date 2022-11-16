<template>
  <Navbar></Navbar>
  <SingleAttraction></SingleAttraction>
</template>

<script lang="ts">
import {defineComponent} from "vue";

import SingleAttraction from "../components/SingleAttraction.vue";
import Navbar from "../components/Navbar.vue";

export default defineComponent({
  name: 'SingleAttractionView',
  components: {
    SingleAttraction,
    Navbar
  },
});
</script>