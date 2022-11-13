import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import SampleView from "../views/SampleView.vue";
import AboutView from "../views/AboutView.vue";
import Booking from '../views/BookingView.vue'
import BookView from '../views/BookView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/Admin",
      name: "Admin",
      component: BookView,
    },
    {
      path: "/Analytics",
      name: "Analytics",
      component: SampleView,
    },
    {
      path: "/signin",
      name: "Sign In",
      component: AboutView,
    }
  ],
});

export default router;
