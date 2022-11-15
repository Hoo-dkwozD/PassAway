<!-- eslint-disable prettier/prettier -->
<!-- eslint-disable prettier/prettier -->
<template>
<div class="container-fluid">

    <div class="card m1-3">
  <div class="ml-3 "><h3 class="card-title text-primary text-centre">
    {{attract.name}}
  </h3></div>
  <div class="card-body text-left">
    <div class="card-header">{{attract.description}}</div>
    <p class="card-text">Address : {{attract.address}}</p>
    <p class="card-text">Benefits : {{attract.benefits}}</p>
    <p class="card-text">Pass Type : {{attract.passType}}</p>
    <p class="card-text">Replacement Fee : {{attract.replacementFee}}</p>
    <p class="card-text">Number of Accompanying Guests : {{attract.numAccompanyingGuests}}</p>
    <p class="card-text">Max PassesPer Loan : {{attract.maxPassesPerLoan}}</p>
    <p class="card-text">Max Loans Per Month : {{attract.maxLoansPerMonth}}</p>
    <p class="card-text">Membership ID : {{attract.membershipId}}</p>
    <p class="card-text">Expiry Date : {{attract.expiryDate}}</p>
    
  </div>
  <div class="card-footer text-muted">
    <router-link :to="`/Attract`" >
    <a href="#" class="btn btn-primary">Back to Attractions</a>
    </router-link>
  </div>
</div>

</div>
</template>

<script lang="ts">
import axios from "axios";
import { DatePicker } from "v-calendar";
import { defineComponent } from "vue";
import CalendarPicker from "./CalendarPicker.vue";

export default ({
    
    data() {
        return {
            id: this.$route.params.id ,
            attract: [],
        }
    },

    async created() {
        const response = await axios.get("http://localhost:8080/api/attraction/" + this.id);
        this.attract=response.data.data;
        
       
    }
})


</script>

<style>
.title {
  color: rgb(43, 43, 43);
  font-family: "Trebuchet MS", sans-serif;
  font-weight: bold;
  font-size: 50px;
  letter-spacing: 0;
  margin-top:10px ;
  
  /* margin-bottom: 10px;
  padding-bottom: 10px; */
  /* line-height: 1.4; */
  /* margin-left: 100px; */
}
</style>