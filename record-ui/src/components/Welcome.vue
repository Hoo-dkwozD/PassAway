<template>
    <div class="welcome-wrapper">
        <div class="welcome-card text-white">
            <h1>Record</h1>
            <p>Go to <button @click="gotoToDoList()" class="btn btn-success" type="button">To-Do List</button></p>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import router from '@/router/index';

export default defineComponent({
    data() {
        return {};
    },
    methods: {
        gotoToDoList(): void {
            router.push("/sample");
        }
    }
});
</script>

<style scoped>
.welcome-wrapper {
    height: 100%;
    width: 100%;
}

.welcome-wrapper, .welcome-card {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
</style>