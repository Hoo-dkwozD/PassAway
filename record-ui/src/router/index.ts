import { createRouter, createWebHistory } from "vue-router";
import SampleView from "../views/SampleView.vue";
import AboutView from "../views/AboutView.vue";
import BookView from '../views/BookView.vue'
import BookingConfirmationView from "../views/BookingConfirmationView.vue"
import AttractionView from "../views/AttractionView.vue"
import singleAttractionView from "../views/singleAttractionView.vue"
import EditAttractionView from "../views/EditAttractionView.vue"
import CreateAttractionView from "../views/CreateAttractionView.vue"

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
      path:"/Attract",
      name:"Attract",
      component: AttractionView
    },
    {
      path:'/Attract/:id',
      name : 'singleAttraction',
      component: singleAttractionView
    },
    {
      path:'/Attract/edit/:id',
      name : 'EditAttraction',
      component: EditAttractionView

    },
    {
      path:'/create',
      name : 'CreateAttraction',
      component: CreateAttractionView

    }

  ],
});

export default router;
