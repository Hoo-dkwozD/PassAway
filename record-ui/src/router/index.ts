import { createRouter, createWebHistory } from "vue-router";
import BookView from '../views/BookView.vue'
import BookingConfirmationView from '../views/BookingConfirmationView.vue'
import Profile from '../views/Profile.vue'
import ProfilePassword from '../views/ProfilePassword.vue'
import GOPLandingPage from '../views/GOPLandingPage.vue'
import AnalyticsView from '../views/AnalyticsView.vue'
import Admin from '../views/Admin.vue'
import EditBarCode from '../views/EditBarCode.vue'
import LoginView from '../views/LoginView.vue'
import SignUp from '../views/Signup.vue'
import SignupRedirect from '../views/SignupRedirect.vue'
import AdminAllBookings from '../views/adminAllBookings.vue'

function requireAuth(to, from, next){
  console.log(this);
  console.log(this.$cookies.get('id'));
  next();
}

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
      path: "/Bookings",
      name: "Bookings",
      component: BookView,
      props: true,
    },
    {
      path: "/Admin",
      name: "Admin",
      component: Admin,
    },
    {
      path: "/bookingconfirmation/:loanID",
      name: "Booking Confirmation",
      component: BookingConfirmationView,
      props: true,
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
    {
      path: "/GOPLanding",
      name: 'GOPLanding',
      component: GOPLandingPage,
    },
    {
      path: "/Analytics",
      name: 'Analytics',
      component: AnalyticsView,
    },
    {
      path: '/EditBarCode',
      name: 'EditBarCode',
      component: EditBarCode
    },
    {
      path: '/Login',
      name: 'Login',
      component: LoginView
    },
    {
      path: '/signup',
      name: 'Signup',
      component: SignUp
    },
    {
      path: '/signupredirect',
      name: 'SignupRedirect',
      component: SignupRedirect
    },
    {
      path:'/adminAllBookings',
      name: 'AdminAllBookings',
      component: AdminAllBookings
    }
  ],
});

export default router;
