<template>
  <form class="container-fluid">
    <div class="form-group">
      <label for="name">Name </label>
      <input type="text" class="form-control" id="name" />
    </div>

    <div class="form-group">
      <label for="desc">Description </label>
      <input type="text" class="form-control" id="desc" />
    </div>

    <div class="form-group">
      <label for="add">Address</label>
      <input type="text" class="form-control" id="add" />
    </div>

    <div class="form-group">
      <label for="memid">Membership ID</label>
      <input type="text" class="form-control" id="memid" />
    </div>

    <div class="form-group">
      <label for="expiryyear">Expiry Date(YYYY)</label>
      <input type="number" class="form-control" id="expiryyear" />
    </div>

    <div class="form-group">
      <label for="expirymth">Expiry Date(MM)</label>
      <input type="number" class="form-control" id="expirymth" />
    </div>

    <div class="form-group">
      <label for="expiryday">Expiry Date(MM)</label>
      <input type="number" class="form-control" id="expiryday" />
    </div>

    <div class="form-group">
      <label for="benefits">Benefits: </label>
      <input type="text" class="form-control" id="benefits" />
    </div>

    <div class="form-group">
      <label for="tnc">Terms and Conditions </label>
      <input type="text" class="form-control" id="tnc" />
    </div>

    <div class="form-group">
      <label for="passType">Pass Type : </label>
      <input type="text" class="form-control" id="passType" />
    </div>

    <div class="form-group">
      <label for="replacementFee">Replacement Fee : </label>
      <input type="number" class="form-control" id="replacementFee" />
    </div>

    <div class="form-group">
      <label for="numguests">Number of Accompanying Guests : </label>
      <input type="number" class="form-control" id="numguests" />
    </div>

    <div class="form-group">
      <label for="maxPasses">Maximum Passes per loan : </label>
      <input type="number" class="form-control" id="maxPasses" />
    </div>

    <div class="form-group">
      <label for="maxLoans">Maximum Loans Per Month : </label>
      <input type="number" class="form-control" id="maxLoans" />
    </div>
    <button  class="btn btn-primary" @click="postreq">Submit</button>
  </form>
</template>

<script lang="ts">
import axios from "axios";
import { DatePicker } from "v-calendar";
import { defineComponent } from "vue";
import CalendarPicker from "./CalendarPicker.vue";

export default {
  data() {
    return {
      name: "",
      desc: "",
      add: "",
      memid: "",
      expiryyr: 0,
      expirymth: 0,
      expiryday: 0,
      benefits: "",
      tnc: "",
      passType: "",
      replacementFee: 0,
      numguests: 0,
      maxpasses: 0,
      maxloans: 0,
    };
  },

  methods : {

    async postreq(e) {
        
        e.preventDefault()
        const name1 = document.getElementById("name") as HTMLInputElement;
        this.name = name1.value ;
        console.log(this.name)
        const desc1 = document.getElementById("desc") as HTMLInputElement;
        this.desc = desc1.value ;

        const add1 = document.getElementById("add") as HTMLInputElement;
        this.add = add1.value ;

        const memid1 = document.getElementById("memid") as HTMLInputElement ;
        this.memid = memid1.value ;

        const expiryyr1 = document.getElementById("expiryyear") as HTMLInputElement ;
        this.expiryyr = expiryyr1.value ;

        const expirymth1 = document.getElementById("expirymth") as HTMLInputElement ;
        this.expirymth = expirymth1.value ;


        const expiryday1 = document.getElementById("expiryday") as HTMLInputElement ;
        this.expiryday = expiryday1.value ;

        const benefits1 = document.getElementById("benefits") as HTMLInputElement ;
        this.benefits = benefits1.value ;

        const tnc1 = document.getElementById("tnc") as HTMLInputElement ;
        this.tnc = tnc1.value ;

        const passType1 = document.getElementById("passType") as HTMLInputElement ;
        this.passType = passType1.value ;

        const replacementFee1 = document.getElementById("replacementFee") as HTMLInputElement ;
        this.replacementFee = replacementFee1.value ;

        const numguests1 = document.getElementById("numguests") as HTMLInputElement ;
        this.numguests = numguests1.value ;

        const maxpasses1 = document.getElementById("maxPasses") as HTMLInputElement ;
        this.maxpasses = maxpasses1.value ;

        const maxloans1 = document.getElementById("maxLoans") as HTMLInputElement ;
        this.maxloans = maxloans1.value ;
 
        axios({
            url : "http://localhost:8080/api/attraction",
            method: 'post',
            data : {
                name: this.name,
        description: this.desc,
        passType: this.passType,
        replacementFee: this.replacementFee,
        numAccompanyingGuests: this.numguests,
        maxPassesPerLoan: this.maxPassesPerLoan,
        maxLoansPerMonth: this.maxloans,
        address: this.add,
        membershipId: this.memid,
        expiryDateYYYY: this.expiryyr,
        expiryDateMM : this.expirymth,
        expiryDateDD : this.expiryday,
        benefits : this.benefits,
        termsConditions : this.tnc 
            }



        })
        .then((res) => {
                if(res.data.code==200) {
                    window.alert("You have created successfully!")
                }
                console.log(res)
            
        })
        .catch((err) => console.log(err));
    }
    
  },
};
</script>

<style></style>
