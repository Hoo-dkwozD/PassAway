import { createApp } from "vue";
import { createPinia } from "pinia";
import { VueCookie } from "vue-cookie";

import App from "./App.vue";
import router from "./router";

import "./assets/main.css";
import VCalendar from 'v-calendar'

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(VCalendar,{});
app.use(VueCookie);

app.mount('#app');
