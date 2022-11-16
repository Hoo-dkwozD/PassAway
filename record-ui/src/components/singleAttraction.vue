<!-- eslint-disable prettier/prettier -->
<!-- eslint-disable prettier/prettier -->
<template>
  <div class="container-fluid">
    <div class="card m1-3">
      <template v-if="attract !== null">
        <div class="ml-3 ">
          <h3 class="card-title text-primary text-centre">
            {{ attract.name }}
          </h3>
        </div>
        <div class="card-body text-left">
          <div class="card-header">{{ attract.description }}</div>
          <p class="card-text">Address: {{ attract.address }}</p>
          <p class="card-text">Benefits: {{ attract.benefits }}</p>
          <p class="card-text">Pass Type: {{ attract.passType }}</p>
          <p class="card-text">Replacement Fee: {{ attract.replacementFee }}</p>
          <p class="card-text">Number of Accompanying Guests: {{ attract.numAccompanyingGuests }}</p>
          <p class="card-text">Max PassesPer Loan: {{ attract.maxPassesPerLoan }}</p>
          <p class="card-text">Max Loans Per Month: {{ attract.maxLoansPerMonth }}</p>
          <p class="card-text">Membership ID: {{ attract.membershipId }}</p>
          <p class="card-text">Expiry Date: {{ attract.expiryDate }}</p>
        </div>
      </template>
      <div class="card-footer text-muted">
        <router-link :to="`/admin/attractions`" >
          <a href="#" class="btn btn-primary">Back to Attractions</a>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";

// Typings
interface Attraction {
  attractionId: number,
  name: string,
  description: string,
  passType: string,
  replacementFee: number,
  numAccompanyingGuests: number,
  maxPassesPerLoan: number,
  maxLoansPerMonth: number,
  cannotBook: boolean,
  address: string,
  membershipId: string,
  expiryDate: string,
  barcodeImage: string,
  backgroundImage: string,
  benefits: string,
  termsConditions: string
}

interface SingleAttractionData {
  id: number,
  attract: Attraction | null,
  staffId: number | null,
  role: string | null
}

interface LoginData {
  staffId: number,
  role: string
}

export default defineComponent({
  data(): SingleAttractionData {
      return {
          id: parseInt(this.$route.params.id instanceof Array ? this.$route.params.id[0] : this.$route.params.id),
          attract: null,
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

    const response = await axios.get(
      import.meta.env.VITE_API_URL + `api/attraction/${this.id}`
    );
    this.attract = await response.data.data;
  },
  methods: {
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
  }
});
</script>

<style>
.title {
  color: rgb(43, 43, 43);
  font-family: "Trebuchet MS", sans-serif;
  font-weight: bold;
  font-size: 50px;
  letter-spacing: 0;
  margin-top:10px ;
}
</style>