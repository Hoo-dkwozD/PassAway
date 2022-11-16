<template>
    <Navbar></Navbar>
    <ViewAttraction></ViewAttraction>
</template>

<script lang="ts">
import { defineComponent } from "vue";

import ViewAttraction from "@/components/ViewAttraction.vue";
import Navbar from "@/components/Navbar.vue";

export default defineComponent({
    components: {
        ViewAttraction,
        Navbar,
    },
});
</script>
