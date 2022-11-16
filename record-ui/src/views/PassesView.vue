
<template>

  <div class="container w-80">

    <div class="row mt-3">
      <div class="col">
        <select name="attractions" id="attractions" class="form-select text-center mb-3" @change="showPasses"
          v-model="attId" placeholder="Select attraction">
          <option value="0">All</option>
          <option v-for="attraction in allAttractions" :value="attraction['attractionId']">{{ attraction['name'] }}
          </option>
        </select>

        <table class="table table-striped table-hover table-bordered table-sm">
          <thead>
            <tr>
              <th scope="col" class="fw-bold">Attraction Name</th>
              <th scope="col" class="fw-bold">Pass ID</th>
              <th scope="col" class="fw-bold">Pass Type</th>
              <th scope="col" class="fw-bold">Lost</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="pass in allPasses">
              <td scope="row">{{ pass['name'] }}</td>
              <td>{{ pass['passId'] }}</td>
              <td>{{ pass['passType'] }}</td>
              <td :class="{ 'lost': pass['lost'] }">{{ pass['lost'] ? "Yes" : "No" }}</td>
            </tr>

          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
    
<script lang="ts">
import axios from "axios";
import Navbar from '../components/Navbar.vue';
export default {
  components: {
    Navbar
  },
  data() {
    return {
      allAttractions: [],
      allPasses: [],
      attId: null
    }
  },

  methods: {
    async showPasses() {
      // console.log(this.attId);
      let res;
      if (this.attId == 0) {
        res = await axios.get(
          import.meta.env.VITE_API_URL + `api/pass/list`
        );
      } else {
        res = await axios.get(
          import.meta.env.VITE_API_URL + `api/pass/list-by-attraction?attractionId=${this.attId}`
        );
      }
      this.allPasses = res.data.data
      // console.log(this.allPasses);

    }
  },
  async created() {
    // this.checkLogin();
    try {
      let res = await axios.get(
        import.meta.env.VITE_API_URL + "api/pass/list"
      );
      this.allPasses = res.data.data

      res = await axios.get(
        import.meta.env.VITE_API_URL + "api/attractions"
      );
      this.allAttractions = res.data.data;
      // console.log(this.allAttractions);
    }

    catch (err) {
      // console.log(err);
      if (err.response.status == 401) {
        this.$router.push({ name: "Login" });
      }
    }
  },
}
</script>
    
<style>
.lost {
  color: red;
}
</style>