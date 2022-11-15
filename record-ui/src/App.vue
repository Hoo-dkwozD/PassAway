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

// Typings
interface Route {
  path: string;
  name: string;
}

export default defineComponent({
  name: 'App',
  data() {
    const role = "Admin";
    const routes: Route[] = [
      {
        path: "/",
        name: "Home",
      },
      {
        path: "/Bookings",
        name: "Bookings",
      },
      {
        path: "/Analytics",
        name: "Analytics",
      },
      {
        path: "/Admin",
        name: "Admin",
      },
      {
        path: "/signin",
        name: "Sign In",
      },
    ];
    return {
      routes,
      role,
    };
  },
  components: {
    RouterLink,
    RouterView,
  },
});
</script>

<style scoped>
.header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.header-nav {
  flex-grow: 1;
  padding-left: 3%;
  padding-right: 3%;
}

.header-nav-item {
  padding-left: 3%;
  padding-right: 3%;
}

.main-content {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.place {
  letter-spacing: 0;
  line-height: 20px;
  margin-left: 500px;
  margin-top: 2px;
  min-height: 20px;
  min-width: 73px;
  white-space: nowrap;
}

.lato {
  color: #fff;
  font-family: "Lato", sans-serif;
  font-size: 17px;
  font-style: normal;
  text-decoration: none;
}

.lato2 {
  color: #f57921;
  font-family: "Lato", sans-serif;
  font-size: 17px;
  font-weight: bolder;
  font-style: normal;
  text-decoration: none;
  border: 1px none;
  border-radius: 16px;
  display: flex;
  gap: 10px;
  height: 55px;
  justify-content: center;
  margin-left: 45px;
  margin-top: 1px;
  padding: 20px 32px;
  width: 129px;
}

.sign-in {
  letter-spacing: 0;
  line-height: 24px;
  margin-bottom: -7.5px;
  margin-top: -9.5px;
  white-space: nowrap;
  width: fit-content;
  color: rgb(255, 255, 255);
  font-family: Arial, Helvetica, sans-serif;
  font-size: 20px;
}

.auto-layout-horizontal {
  align-items: center;
  font-weight: bolder;
  background-color: #d91c53;
  border: 1px none;
  border-radius: 16px;
  display: flex;
  gap: 10px;
  height: 55px;
  justify-content: center;
  margin-left: 45px;
  margin-top: 1px;
  padding: 20px 32px;
  width: 129px;
}

.topnav-right {
  float: right;
}

.display {
  display: inline-block;
}
</style>
