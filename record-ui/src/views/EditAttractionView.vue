<template>
  <Navbar></Navbar>
  <EditAttraction></EditAttraction>
</template>

<script lang="ts">
import { defineComponent } from "vue";

import EditAttraction from "@/components/EditAttraction.vue";
import Navbar from "@/components/Navbar.vue";

export default defineComponent({
  components: {
    EditAttraction,
    Navbar,
  },
});
</script>
