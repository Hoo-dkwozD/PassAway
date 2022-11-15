import { createRouter, createWebHistory } from "vue-router";

import AdminView from '../views/AdminView.vue';
import AnalyticsView from '../views/AnalyticsView.vue';
import BookingConfirmationView from '../views/BookingConfirmationView.vue';
import BookView from '../views/BookView.vue';
import EditBarCodeView from '../views/EditBarCodeView.vue';
import GOPLandingPageView from '../views/GOPLandingPageView.vue';
import LoginView from '../views/LoginView.vue';
import ProfileView from '../views/ProfileView.vue';
import ProfilePasswordView from '../views/ProfilePasswordView.vue';
import SignupView from '../views/SignupView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: BookView,
      props: true,
    },
    {
      path: "/staffs/admin",
      name: "update admin",
      component: AdminView
    },
    {
      path: "/analytics",
      name: 'analytics',
      component: AnalyticsView,
    },
    {
      path: "/bookings",
      name: "bookings",
      component: BookView,
      props: true,
    },
    {
      path: "/booking/:loanID/confirmation",
      name: "booking confirmation",
      component: BookingConfirmationView,
      props: true,
    },
    {
      path: '/attraction/barcode',
      name: 'edit bar code',
      component: EditBarCodeView
    },
    {
      path: "/GOP",
      name: 'GOP landing',
      component: GOPLandingPageView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/profile",
      name: 'profile',
      component: ProfileView,
    },
    {
      path: "/profile/password",
      name: 'profile password',
      component: ProfilePasswordView,
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    }
  ],
});

export default router;
