import { createRouter, createWebHistory } from "vue-router";

/**
 * Routes for Administrators only
 */
// Attractions
import AttractionView from "@/views/AttractionView.vue";
import CreateAttractionView from "@/views/CreateAttractionView.vue";
import EditAttractionView from "@/views/EditAttractionView.vue";
import EditBarCodeView from "@/views/EditBarCodeView.vue";
// import singleAttraction from "@/components/singleAttraction.vue";
import SingleAttractionView from "@/views/singleAttractionView.vue";

// Loans
import AdminAllBookings from "@/views/adminAllBookings.vue";

// Passes
import PassesView from "@/views/PassesView.vue";

// Staffs
import AdminView from '@/views/AdminView.vue';
import AdminStaffsView from "@/views/AdminStaffsView.vue";
// import StaffAddView from "@/views/StaffAddView.vue";
import StaffUpdateProfileView from "@/views/StaffUpdateProfileView.vue";

/**
 * Routes for GOP only
 */
// Loans
import GOPLandingPageView from "@/views/GOPLandingPageView.vue";

/**
 * Routes for All Users
 */
// LILO/Registration
import LoginView from "@/views/LoginView.vue";
import SignupView from "@/views/SignupView.vue";
import SignupRedirectView from "@/views/SignupRedirectView.vue";

// Bookings
import BookingConfirmationView from '@/views/BookingConfirmationView.vue';
import BookView from '@/views/BookView.vue';
import PersonalBookingsView from "@/views/PersonalBookingsView.vue";

// Staffs
import ProfileView from "@/views/ProfileView.vue";
import ProfilePasswordView from "@/views/ProfilePasswordView.vue";

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
      path: "/admin/passes",
      name: "passes",
      component: PassesView,
    },
    {
      path: "/admin/staffs/role",
      name: "add admin",
      component: AdminView,
    },
    {
      path: "/admin/attractions",
      name: "attraction",
      component: AttractionView
    },
    {
      path:'/admin/attraction/:id/edit',
      name : 'edit attraction',
      component: EditAttractionView
    },
    {
      path:'/admin/attraction/add',
      name : 'create attraction',
      component: CreateAttractionView
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
      path: "/booking/:loanID/confirmation",
      name: "booking confirmation",
      component: BookingConfirmationView,
      props: true,
    },
    {
      path: "/admin/attraction/barcode/edit",
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
      name: "admin bookings",
      component: AdminAllBookings,
    },
    {
      path: "/admin/staffs",
      name: "admin staffs",
      component: AdminStaffsView,
    },
    {
      path: "/admin/staff/:staffId/update",
      name: "admin update staff",
      component: StaffUpdateProfileView
    },
  ],
});

export default router;
