<template>
<div class="header">
    <RouterLink to="/">

    <img
      alt="Vue logo"
      class="logo header-logo"
      src="@/assets/logo.png"
      width="160"
      height="75" role="button"
    />
  </RouterLink>
    
    <div class="header-nav">
      <nav class="topnav-right">
        <RouterLink
          :key="route.path"
          v-for="route in routes"
          class="place lato mx-auto"
          :to="route.path"
        >
          <div
            class="auto-layout-horizontal display"
            v-if="route.name == 'Sign In'"
          >
            {{ route.name }}
          </div>
          <div v-else-if="role == 'Admin'" class="lato2 display">
            {{ route.name }}
          </div>
        </RouterLink>
      </nav>
    </div>
  </div>
</template>

<script lang="ts">
interface Route{
    path: string,
    name: string,
}

import { defineComponent } from "vue";
import {RouterLink} from 'vue-router'
export default defineComponent({
    name: 'NavBar',
    data() {
    const role = "Admin";
    const routes: Route[] = [
      {
        path: "/Analytics",
        name: "Analytics"
      },
      {
        path: "/Admin",
        name: "Admin"
      },
      {
        path: "/Login",
        name: 'Login',
      },
    ];
    return {
      routes,
      role
    };
  },
  components: {
    RouterLink,
  },
});
</script>

<style>
.header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.header{
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