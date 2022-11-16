<template>
  <Navbar></Navbar>
  <div class="container w-80">
    <div class="row mt-3">
      <div class="col">
        <select
          name="attractions"
          id="attractions"
          class="form-select text-center mb-3"
          @change="showPasses"
          v-model="attId"
          placeholder="Select attraction"
        >
          <option value="0">All</option>
          <option
            v-for="(attraction, idx) in allAttractions"
            :key="idx"
            :value="attraction['attractionId']"
          >
            {{ attraction["name"] }}
          </option>
        </select>

        <div v-if="showAdd" class="row">
            <input class="btn btn-company-orange" type="button" value="Add Pass" />
        </div>

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
            <tr v-for="(pass, idx) in allPasses" :key="idx">
              <td scope="row">{{ pass["name"] }}</td>
              <td>{{ pass["passId"] }}</td>
              <td>{{ pass["passType"] }}</td>
              <td :class="{ lost: pass['lost'] }">
                {{ pass["lost"] ? "Yes" : "No" }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";
import Navbar from "@/components/Navbar.vue";

// Typings
interface LoginData {
  staffId: number;
  role: string;
}

interface Attraction {
  attractionId: number;
  name: string;
}

interface Pass {
  passId: string;
  name: string;
  passType: string;
  lost: boolean;
}

interface PassesViewData {
  allAttractions: Attraction[];
  allPasses: Pass[];
  attId: number | null;
  staffId: number | null;
  role: string | null;
  showAdd: boolean;
}

export default defineComponent({
  components: {
    Navbar,
  },
  data(): PassesViewData {
    return {
      allAttractions: [],
      allPasses: [],
      attId: 1,
      staffId: null,
      role: null,
      showAdd: true,
    };
  },
  methods: {
    checkLogin(): LoginData | undefined {
      const staffIdStr = localStorage.getItem("staffId");
      const role = localStorage.getItem("role");

      if (staffIdStr === null || role === null) {
        this.$router.push({ name: "login" });
      } else {
        const staffId = parseInt(staffIdStr);

        return {
          staffId: staffId,
          role: role,
        };
      }
    },
    async showPasses() {
      // console.log(this.attId);
      let res;
      if (this.attId == 0) {
        this.showAdd = false;
        res = await axios.get(import.meta.env.VITE_API_URL + `api/pass/list`);
      } else {
        res = await axios.get(
          import.meta.env.VITE_API_URL +
            `api/pass/list-by-attraction?attractionId=${this.attId}`
        );
      }
      this.allPasses = res.data.data;
      // console.log(this.allPasses);
      this.showAdd = true;
    },
  },
  async created() {
    const loginData: LoginData | undefined = this.checkLogin();

    if (loginData === undefined) {
      this.$router.push({ name: "login" }).then(() => this.$router.go(0));
      return;
    }

    if (loginData.role !== "ADMINISTRATOR") {
      this.$router.push({ name: "home" }).then(() => this.$router.go(0));
      return;
    }

    this.staffId = loginData.staffId;
    this.role = loginData.role;

    try {
      let res = await axios.get(import.meta.env.VITE_API_URL + "api/pass/list");
      this.allPasses = await res.data.data;

      res = await axios.get(import.meta.env.VITE_API_URL + "api/attractions");
      this.allAttractions = await res.data.data;
      // console.log(this.allAttractions);
    } catch (err: any) {
      if (err.response) {
        if (err.response.status == 401) {
          this.$router.push({ name: "Login" });
        } else {
          console.error(err.response.data.message);
        }
      } else {
        console.error(err);
      }
    }
  },
});
</script>

<style>

.btn-company-red {
  background-color: #d91c53!important;
  border-color: #d91c53!important;
  color: white!important;
}

.btn-company-orange {
  background-color: #f57921!important;
  border-color: #f57921!important;
  color: white!important;
}

.lost {
  color: red;
}
</style>
