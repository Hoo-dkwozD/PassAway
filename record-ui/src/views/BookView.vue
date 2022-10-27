<template>
  <ToDo question="To-dos:"></ToDo>
  <BookAttraction></BookAttraction>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ToDo from "../components/ToDo.vue";
import ToDo from "../components/BookAttraction.vue";

export default defineComponent({
  components: {
    ToDo,
    BookAttraction,
  },
});
</script>
