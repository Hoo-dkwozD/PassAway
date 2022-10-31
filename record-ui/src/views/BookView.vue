<template>
  <Test></Test>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ToDo from "../components/ToDo.vue";
import Test from "../components/Test.vue";

export default defineComponent({
  components: {
    Test,
  },
});
</script>
