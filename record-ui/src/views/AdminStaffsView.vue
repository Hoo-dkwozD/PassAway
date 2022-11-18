<template>
  <Navbar></Navbar>
  <admin-staffs></admin-staffs>
</template>

<script lang="ts">
import AdminStaffs from "@/components/AdminStaffs.vue";
import { defineComponent } from "vue";
import Navbar from "@/components/Navbar.vue";

export default defineComponent({
  name: "AdminStaffsView",
  components: { AdminStaffs, Navbar },
  data() {
    return {};
  },
  methods: {},
});
</script>
