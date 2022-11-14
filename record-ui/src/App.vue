<template>
  <router-view />
</template>

<style>
*{
padding: 0;
margin: 0;
box-sizing: border-box;
}
#app {
font-family: Avenir, Helvetica, Arial, sans-serif;
-webkit-font-smoothing: antialiased;
-moz-osx-font-smoothing: grayscale;
text-align: center;
color: #2c3e50;

}
</style>



<script lang="ts">
import { defineComponent } from "vue";
import { RouterLink, RouterView } from "vue-router";
import VCalendar from "v-calendar";
import NavBar from '@/components/Navbar.vue'
export default defineComponent({
    name: 'App',
    components: {
    NavBar
}
})
// Typings
interface Route {
  path: string;
  name: string;
}

</script>
