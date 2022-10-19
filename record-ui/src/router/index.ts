import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import SampleView from '../views/SampleView.vue'
import Booking from '../views/BookingView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: MainView
    },
    {
      path: '/sample',
      name: 'sample',
      component: SampleView
    },
    {
      path: '/',
      name: 'booking',
      component: Booking
    }
  ]
});

export default router
