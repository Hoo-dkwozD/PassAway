<script setup lang="ts">
import TheWelcome from '../components/TheWelcome.vue'
</script>

<template>
  <div class="home">
  </div>
</template>
<script setup lang="ts">
import {useCounterStore} from '../stores/counter';
const storeCounter = useCounterStore();

</script>
<script lang="ts">
import { defineComponent } from 'vue';


interface Data{
  counter: number
}
// Components
import HelloWorld from '../components/HelloWorld.vue';

export default defineComponent({
  name: 'HomeView',
  data(): Data{
    return{
      counter: 0,
      

    }
  },
  methods:{
  },
  components: {
    HelloWorld,
  },
});
</script>

<style scoped>
.count{
  font-size: 60px;
  margin: 20px;
}
.buttons button{
  font-size: 40px;
  margin: 10px;
}
</style>
