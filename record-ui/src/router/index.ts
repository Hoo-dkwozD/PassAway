import { createRouter, createWebHistory } from "vue-router";
import SampleView from "../views/SampleView.vue";
import AboutView from "../views/AboutView.vue";
import BookView from '../views/BookView.vue'
import BookingConfirmationView from "../views/BookingConfirmationView.vue"
import Profile from '../views/Profile.vue'
import ProfilePassword from '../views/ProfilePassword.vue'
import GOPLandingPage from '../views/GOPLandingPage.vue'

function requireAuth(to,from,next){
  console.log(this);
  console.log(this.$cookies.get('id'));
  next();
}


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/Home",
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
      component: AboutView,
    },
    {
      path: "/Analytics",
      name: "Analytics",
      component: AboutView,
    },
    {
      path: "/signin",
      name: "Sign In",
      component: AboutView,
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
    }
  ],
});

export default router;
