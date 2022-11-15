import { createRouter, createWebHistory } from "vue-router";
import AdminView from '../views/AdminView.vue';
import AdminAllBookings from "../views/adminAllBookings.vue";
import AnalyticsView from '../views/AnalyticsView.vue';
import BookingConfirmationView from '../views/BookingConfirmationView.vue';
import BookView from '../views/BookView.vue';
import EditBarCodeView from "../views/EditBarCodeView.vue";
import GOPLandingPageView from "../views/GOPLandingPageView.vue";
import LoginView from "../views/LoginView.vue";
import PersonalBookingsView from "../views/PersonalBookingsView.vue";
import ProfileView from "../views/ProfileView.vue";
import ProfilePasswordView from "../views/ProfilePasswordView.vue";
import SignupView from "../views/SignupView.vue";
import SignupRedirectView from "../views/SignupRedirectView.vue";
import StaffAddView from "../views/StaffAddView.vue";

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
      path: "/staffs/add",
      name: "add staff",
      component: StaffAddView
    },
    {
      path: "/analytics",
      name: "analytics",
      component: AnalyticsView,
    },
    {
      path: "/admin/bookings",
      name: "book",
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
      path: "/attraction/barcode",
      name: "edit bar code",
      component: EditBarCodeView,
    },
    {
      path: "/GOP",
      name: "GOP landing",
      component: GOPLandingPageView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/profile",
      name: "profile",
      component: ProfileView,
    },
    {
      path: "/profile/password",
      name: "profile password",
      component: ProfilePasswordView,
    },
    {
      path: "/signup",
      name: "signup",
      component: SignupView,
    },
    {
      path: "/bookings",
      name: "personal bookings",
      component: PersonalBookingsView,
    },
    {
      path: "/signupredirect",
      name: "signup redirect",
      component: SignupRedirectView,
    },
    {
      path: "/adminAllBookings",
      name: "admin all bookings",
      component: AdminAllBookings,
    }
  ],
});

export default router;
