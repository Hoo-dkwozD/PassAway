<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="header">
    <img alt="Vue logo" class="logo header-logo" src="@/assets/logo.svg" width="25" height="25" />

    <div class="header-nav">
      <nav>
        <RouterLink v-for="route in routes" class="header-nav-item  text-success" :to="route.path">{{ route.name }}</RouterLink>
      </nav>
    </div>
  </div>

  <div class="main-content">
    <RouterView />
  </div>
</template>

<script lang="ts">
// Typings
interface Route {
  path: string,
  name: string
}

export default {
  data() {
    const routes: Route[] = [
      {
        path: "/",
        name: "Home"
      },
      {
        path: "/sample",
        name: "Sample"
      }
    ];

    return {
      routes
    };
  }
}
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
</style>
