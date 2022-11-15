<template>
    <CreateAttraction></CreateAttraction>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import CreateAttraction from "../components/CreateAttraction.vue";

export default defineComponent({
  components: {
    CreateAttraction,
  },
});
</script>