<template>
    <singleAttraction></singleAttraction>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import singleAttraction from "../components/singleAttraction.vue";

export default defineComponent({
  components: {
    singleAttraction,
  },
});
</script>