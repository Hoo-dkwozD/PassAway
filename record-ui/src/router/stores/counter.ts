import { defineStore } from 'pinia'
import { toHandlers } from 'vue';

export const useCounterStore = defineStore({
  id: 'counter',
  state: () => ({
    //types
    counter: 0
    
  }),
  getters: {
    doubleCount: (state) => state.counter * 2,
    oddOrEven: (state) => {
      if(state.counter % 2 === 0) return 'even'
      return 'odd'
    },

  },
  actions: {
    increment(val:any) {
      this.counter+= val;
    },
    decrement(val:any){
      this.counter-= val;
    },
    
    },
  });