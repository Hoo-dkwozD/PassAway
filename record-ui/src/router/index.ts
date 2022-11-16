import { createRouter, createWebHistory } from "vue-router";
import AttractionView from "../views/AttractionView.vue"
import SingleAttractionView from "../views/singleAttractionView.vue"
import EditAttractionView from "../views/EditAttractionView.vue"
import CreateAttractionView from "../views/CreateAttractionView.vue"
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
import AdminStaffsView from "../views/AdminStaffsView.vue";
import StaffUpdateProfileView from "../views/StaffUpdateProfileView.vue";
import SignupRedirectView from "../views/SignupRedirectView.vue";

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
      path: "/admin/staffs/admin",
      name: "update admins",
      component: AdminView
    },
    {
      path: "/analytics",
      name: "analytics",
      component: AnalyticsView,
    },
    {
      path: "/admin/attractions",
      name: "attractions",
      component: AttractionView
    },
    {
      path:'/admin/attraction/:id',
      name : 'single attraction',
      component: SingleAttractionView
    },
    {
      path:'/admin/attraction/:id/edit',
      name : 'edit attraction',
      component: EditAttractionView
    },
    {
      path:'/admin/attraction/create',
      name : 'CreateAttraction',
      component: CreateAttractionView
    },
    {
      path: "/booking/:loanID/confirmation",
      name: "booking confirmation",
      component: BookingConfirmationView,
      props: true,
    },
    {
      path: "/attraction/barcode/edit",
      name: "edit bar code",
      component: EditBarCodeView,
    },
    {
      path: "/GOP/bookings",
      name: "GOP landing",
      component: GOPLandingPageView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/bookings",
      name: "personal bookings",
      component: PersonalBookingsView,
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
      path: "/signup/redirect",
      name: "signup redirect",
      component: SignupRedirectView,
    },
    {
      path: "/admin/bookings",
      name: "admin all bookings",
      component: AdminAllBookings,
    },
    {
      path: "/admin/staffs",
      name: "AdminStaffs",
      component: AdminStaffsView,
    },
    {
      path: "/admin/updateStaff/:staffId",
      name: "AdminUpdateStaff",
      component: StaffUpdateProfileView
    }
  ],
});

export default router;
