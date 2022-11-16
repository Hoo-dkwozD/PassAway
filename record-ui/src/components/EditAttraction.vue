<!-- eslint-disable prettier/prettier -->
<!-- eslint-disable prettier/prettier -->
<!-- eslint-disable prettier/prettier -->
<template>
  <form class="container-fluid">
    <div class="form-group">
      <label for="name">Name </label>
      <input v-model="name" type="text" class="form-control" id="name" />
    </div>

    <div class="form-group">
      <label for="desc">Description </label>
      <input v-model="desc" type="text" class="form-control" id="desc" />
    </div>

    <div class="form-group">
      <label for="add">Address</label>
      <input v-model="add" type="text" class="form-control" id="add" />
    </div>

    <div class="form-group">
      <label for="memid">Membership ID</label>
      <input v-model="memid" type="text" class="form-control" id="memid" />
    </div>

    <div class="form-group">
      <label for="expiryyear">Expiry Date(YYYY)</label>
      <input v-model="expiryyr" type="number" class="form-control" id="expiryyear" />
    </div>

    <div class="form-group">
      <label for="expirymth">Expiry Date(MM)</label>
      <input v-model="expirymth" type="number" class="form-control" id="expirymth" />
    </div>

    <div class="form-group">
      <label for="expiryday">Expiry Date(DD)</label>
      <input v-model="expiryday" type="number" class="form-control" id="expiryday" />
    </div>

    <div class="form-group">
      <label for="benefits">Benefits: </label>
      <input v-model="benefits" type="text" class="form-control" id="benefits" />
    </div>

    <div class="form-group">
      <label for="tnc">Terms and Conditions </label>
      <input v-model="tnc" type="text" class="form-control" id="tnc" />
    </div>

    <div class="form-group">
      <label for="passType">Pass Type : </label>
      <input v-model="passType" type="text" class="form-control" id="passType" />
    </div>

    <div class="form-group">
      <label for="replacementFee">Replacement Fee : </label>
      <input v-model="replacementFee" type="number" class="form-control" id="replacementFee" />
    </div>

    <div class="form-group">
      <label for="numguests">Number of Accompanying Guests : </label>
      <input v-model="numguests" type="number" class="form-control" id="numguests" />
    </div>

    <div class="form-group">
      <label for="maxPasses">Maximum Passes per loan : </label>
      <input v-model="maxpasses" type="number" class="form-control" id="maxPasses" />
    </div>

    <div class="form-group">
      <label for="maxLoans">Maximum Loans Per Month : </label>
      <input v-model="maxloans" type="number" class="form-control" id="maxLoans" />
    </div>
    <button  class="btn btn-primary" @click="postreq">Submit</button>
  </form>
</template>

<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";

// Typings
interface CreateAttractionData {
  name: string | null,
  desc: string | null,
  passType: string | null,
  replacementFee: number | null,
  numguests: number | null,
  maxpasses: number | null,
  maxloans: number | null,
  add: string | null,
  memid: string | null,
  expiryyr: number | null,
  expirymth: number | null,
  expiryday: number | null,
  benefits: string | null,
  tnc: string | null,
  staffId: number | null,
  role: string | null,
}

interface LoginData {
  staffId: number,
  role: string
}

export default defineComponent({
  data(): CreateAttractionData {
    return {
      name: null,
      desc: null,
      add: null,
      memid: null,
      expiryyr: null,
      expirymth: null,
      expiryday: null,
      benefits: null,
      tnc: null,
      passType: null,
      replacementFee: null,
      numguests: null,
      maxpasses: null,
      maxloans: null,
      staffId: null,
      role: null
    };
  },
  async created() {
    const loginData = this.checkLogin();

    if (loginData !== undefined) {
      this.staffId = loginData.staffId;
      this.role = loginData.role;
    } else {
      this.$router.push({ name: "login" }).then(() => this.$router.go(0));
      return;
    }

    if (this.role !== 'ADMINISTRATOR') {
      this.$router.push({ name: "home" }).then(() => this.$router.go(0));
      return;
    }

    const res = await axios.get(
      import.meta.env.VITE_API_URL + `api/attraction/${this.$route.params.id}`
    );
    const data = await res.data;

    this.name = data.data.name;
    this.desc = data.data.description;
    this.add = data.data.address;
    this.memid = data.data.membershipId;
    this.expiryyr = parseInt(data.data.expiryDate.split('-')[0]);
    this.expirymth = parseInt(data.data.expiryDate.split('-')[1]);
    this.expiryday = parseInt(data.data.expiryDate.split('-')[2]);
    this.benefits = data.data.benefits;
    this.tnc = data.data.termsConditions;
    this.passType = data.data.passType === 'PHYSICAL' ? '0' : '1';
    this.replacementFee = data.data.replacementFee;
    this.numguests = data.data.numAccompanyingGuests;
    this.maxpasses = data.data.maxPassesPerLoan;
    this.maxloans = data.data.maxLoansPerMonth;
  },
  methods : {
    checkLogin(): LoginData | undefined {
      const staffIdStr = localStorage.getItem("staffId");
      const role = localStorage.getItem("role");

      if (staffIdStr === null || role === null) {
        this.$router.push({ name: "login" }).then(() => this.$router.go(0));
      } else {
        const staffId = parseInt(staffIdStr);

        return {
          staffId: staffId,
          role: role,
        };
      }
    },
    async postreq(e: Event) {
        e.preventDefault();

        axios({
            url : import.meta.env.VITE_API_URL + `api/attraction/${this.$route.params.id}`,
            method: 'put',
            data : {
                attractionId: parseInt(this.$route.params.id instanceof Array ? this.$route.params.id[0] : this.$route.params.id),
                name: this.name,
                description: this.desc,
                passType: this.passType,
                replacementFee: this.replacementFee,
                numAccompanyingGuests: this.numguests,
                maxPassesPerLoan: this.maxpasses,
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
          if(res.data.code === 200) {
            this.$router.push({ name: 'attractions' }).then(() => this.$router.go(0));
          }
        })
        .catch((err: any) => {
          if (err.response) {
            if (err.response.status === 401) {
              this.$router.push({ name: "login" }).then(() => this.$router.go(0));
            } else {
              console.log(err.response.data.message);
            }
          } else {
            console.log(err.message);
          }
        });
    }
    
  },
});
</script>

<style></style>
