<template>
    <div
      id="sectionheader"
      style="backgroundImage: `url(${currentBackground})`"
    >
        <div class="main">
            <!-- <img v-if="currentBackground == 'SingaporeZoo'" src="/assets/header.png"/>
            <img v-else-if="currentBackground == 'sportsschool'" src="/assets/sportsschool.png"/>
            <img v-else src="/assets/gardensbybay.png"/> -->
            <h1 class="title">Bookings</h1>
            <h3 class="titledescription">
            Booking Details
            </h3>
        </div>

        <div>
            <ProfileGroup/>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"
import { useCounterStore } from "../stores/counter";
import ProfileGroup from '../components/ProfileGroup.vue'

interface Data{
    currentBackground: String,
}

export default defineComponent({
    data(): Data {
        return {
            currentBackground: "",
        };
    },
    setup() {
    },
    components: { ProfileGroup }
})
</script>

<style>

</style>