<template>
    <ToDo question="To-dos:"></ToDo>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import ToDo from '@/components/ToDo.vue';

export default defineComponent({
    components: {
        ToDo
    }
});
</script>
