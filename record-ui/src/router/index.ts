import { createRouter, createWebHistory } from "vue-router";
import SampleView from "../views/SampleView.vue";
import AboutView from "../views/AboutView.vue";
import BookView from '../views/BookView.vue'
import BookingConfirmationView from "../views/BookingConfirmationView.vue"

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
      props: true
    },
    {
      path: "/signin",
      name: "Sign In",
      component: AboutView,
    },
    {
      path: "/bookingconfirmation",
      name: "Booking Confirmation",
      component: BookingConfirmationView
    }
  ],
});

export default router;
