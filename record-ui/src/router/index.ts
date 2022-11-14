import { createRouter, createWebHistory } from "vue-router";
import SampleView from "../views/SampleView.vue";
import AboutView from "../views/AboutView.vue";
import BookView from '../views/BookView.vue'
import BookingConfirmationView from "../views/BookingConfirmationView.vue"
import Profile from '../views/Profile.vue'
import ProfilePassword from '../views/ProfilePassword.vue'
import GOPLandingPage from '../views/GOPLandingPage.vue'
import AnalyticsView from '../views/AnalyticsView.vue'

function requireAuth(to,from,next){
  console.log(this);
  console.log(this.$cookies.get('id'));
  next();
}


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
      path: "/AnalyticsView",
      name: 'AnalyticsView',
      component: AnalyticsView,
    }
  ],
});

export default router;
