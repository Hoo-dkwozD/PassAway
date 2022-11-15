import { createRouter, createWebHistory } from "vue-router";

import Admin from '../views/Admin.vue';
import AnalyticsView from '../views/AnalyticsView.vue';
import BookingConfirmationView from '../views/BookingConfirmationView.vue';
import BookView from '../views/BookView.vue';
import GOPLandingPage from '../views/GOPLandingPage.vue';
import LoginView from '../views/LoginView.vue';
import Profile from '../views/Profile.vue';
import ProfilePassword from '../views/ProfilePassword.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "Home",
      component: BookView,
      props: true,
    },
    {
      path: "/Admin",
      name: "Admin",
      component: Admin,
    },
    {
      path: "/Analytics",
      name: 'Analytics',
      component: AnalyticsView,
    },
    {
      path: "/Bookings",
      name: "Bookings",
      component: BookView,
      props: true,
    },
    {
      path: "/bookingconfirmation/:loanID",
      name: "Booking Confirmation",
      component: BookingConfirmationView,
      props: true,
    },
    {
      path: "/GOPLanding",
      name: 'GOPLanding',
      component: GOPLandingPage,
    },
    {
      path: "/login",
      name: "Login",
      component: LoginView,
    },
    {
      path: "/profile",
      name: 'Profile',
      component: Profile,
    },
    {
      path: "/profilepassword",
      name: 'ProfilePassword',
      component: ProfilePassword,
    },
  ]
});

export default router;
