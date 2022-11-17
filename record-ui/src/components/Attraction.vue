<template>
  <div class="container-fluid m-0 p-0 h-100">
    <div
      id="section-header"
      :style="{
        'background-image':
          'url(https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935)',
      }"
      class="w-100 pt-5 pb-5 px-3 h-100"
    >
      <div
        class="px-5 d-flex flex-row justify-content-between align-items-center"
      >
        <div class="m-0">
          <div class="title">Attractions</div>
        </div>
        <div
          class="m-0 d-flex flex-column justify-content-center align-items-center px-3"
        >
          <router-link :to="`/admin/attraction/add`">
            <button class="btn btn-light shadow fw-bold">Create Attraction +</button>
          </router-link>
        </div>
      </div>

      <div class="bg-light p-3 rounded">
        <table class="table table-light table-striped table-hover table-sm">
          <thead>
            <tr>
              <th scope="col" class="fw-bold">Attraction Name</th>
              <th scope="col" class="fw-bold">Description</th>
              <th scope="col" class="fw-bold">View Attraction</th>
              <th scope="col" class="fw-bold">Edit Attraction</th>
              <th scope="col" class="fw-bold">Add Barcode</th>
              <th scope="col" class="fw-bold">Delete Attraction</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="attract, idx in attractions" :key="idx">
              <td class="py-2" scope="row">{{ attract.name }}</td>
              <td class="py-2">{{ attract.description }}</td>
              <td class="py-2">
                <router-link :to="`/admin/attraction/${attract.attractionId}`">
                  <button class="btn btn-outline-secondary">
                    View Attraction
                  </button>
                </router-link>
              </td>
              <td class="py-2">
                <router-link
                  :to="`/admin/attraction/${attract.attractionId}/edit`"
                >
                  <button class="btn btn-company-orange">
                    Edit Attraction
                  </button>
                </router-link>
              </td>
              <td class="py-2">
                <router-link :to="`/admin/attraction/barcode/edit`">
                  <button class="btn btn-warning">Add Barcode</button>
                </router-link>
              </td>
              <td class="py-2">
                <button
                  class="btn btn-company-red"
                  data-bs-toggle="modal"
                  data-bs-target="#staticBackdrop"
                  @click="setToDelete(attract.attractionId)"
                >
                  Delete Attraction
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <div
    class="modal fade"
    id="staticBackdrop"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title fs-5" id="staticBackdropLabel">
            Confirm Delete Attraction?
          </h3>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <p class="fw-bold">
            Are you sure you want to delete this attraction?
          </p>
        </div>
        <div class="modal-footer">
          <button
            @click="deleteAttraction()"
            type="button"
            class="btn btn-danger"
          >
            Yes
          </button>
          <button
            type="button"
            class="btn btn-outline-dark"
            data-bs-dismiss="modal"
          >
            No
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import axios from "axios";

// Typings
interface Attraction {
  attractionId: number;
  name: string;
  description: string;
  passType: string;
  replacementFee: number;
  numAccompanyingGuests: number;
  maxPassesPerLoan: number;
  maxLoansPerMonth: number;
  cannotBook: boolean;
  address: string;
  membershipId: string;
  expiryDate: string;
  barcodeImage: string;
  backgroundImage: string;
  benefits: string;
  termsConditions: string;
}

interface AttractionData {
  attractions: Attraction[];
  staffId: number | null;
  role: string | null;
  toDeleteId: number | null;
}

interface LoginData {
  staffId: number;
  role: string;
}

export default defineComponent({
  data(): AttractionData {
    return {
      attractions: [],
      staffId: null,
      role: null,
      toDeleteId: null,
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

    if (this.role !== "ADMINISTRATOR") {
      this.$router.push({ name: "home" }).then(() => this.$router.go(0));
      return;
    }

    try {
      const res = await axios.get(
        import.meta.env.VITE_API_URL + `api/attractions`,
        {
          headers: {'authorization': `${localStorage.getItem("token")}`},
        }
      );

      this.attractions = await res.data.data;
    } catch (err: any) {
      if (err.response) {
        if (err.response.status == 401) {
          this.$router
            .push({ name: "login" })
            .then(() => this.$router.go(0));

          return;
        } else if (err.response.status == 403) {
          this.$router
            .push({ name: "home" })
            .then(() => this.$router.go(0));

          return;
        } else {
          console.log(err.response.data.message);
        }
      } else {
        console.log(err.message);
      }
    }
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
    async deleteAttraction() {
      try {
        const res = await axios.delete(
          import.meta.env.VITE_API_URL + `api/attraction/${this.toDeleteId}`,
          {
            headers: {'authorization': `${localStorage.getItem("token")}`},
          }
        );
        const data = await res.data;

        if (data.code === 200) {
          this.$router
            .push({ name: "attraction" })
            .then(() => this.$router.go(0));

          return;
        }
      } catch (err: any) {
        if (err.response) {
          if (err.response.status == 401) {
            this.$router.push({ name: "login" }).then(() => this.$router.go(0));
            return;
          } else {
            console.error(err.response.data.message);
          }
        } else if (err.response.status == 403) {
          this.$router
            .push({ name: "home" })
            .then(() => this.$router.go(0));

          return;
        } else {
          console.error(err.message);
        }
      }
    },
    setToDelete(attractionId: number) {
      this.toDeleteId = attractionId;
    },
  },
});
</script>

<style>
#section-header {
  background-size: 100%;
}

.main {
  margin-bottom: 10px;
}

.btn-company-red {
  background-color: #d91c53 !important;
  border-color: #d91c53 !important;
  color: white !important;
}

.btn-company-orange {
  background-color: #f57921 !important;
  border-color: #f57921 !important;
  color: white !important;
}

#attract-title {
  margin-left: 10px;
  color: blueviolet;
}
.title {
  font-family: "Trebuchet MS", sans-serif;
  font-weight: bold;
  font-size: 50px;
  letter-spacing: 0;
  /* margin-bottom: 10px;
  padding-bottom: 10px; */
  /* line-height: 1.4; */
  margin-left: 100px;
  color: #d91c53 !important;
}

.titledescription {
  color: white;
  font-family: "Trebuchet MS", sans-serif;
  font-weight: 400;
  letter-spacing: 0;
  line-height: 1.6;
  margin-left: 100px;
  padding-left: 50px;
  min-height: 54px;
  width: 1500px;
}

.calendarStyle {
  position: absolute;
  margin-top: 25px;
}
.bookingdetails {
  /* align-items: center; */
  /* display: flex; */
  margin-left: 150px;
  margin-top: 20px;
  border-radius: 30px;
  box-shadow: 0px 10px 10px;
  /* padding: 5px; */
  /* padding-top: 15px; */
  padding-left: 5px;
  background-color: rgba(255, 248, 255, 0.678);
  border: 1px none;
  width: 1000px;
  height: 90px;
}

#group-location {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 450px;
  margin-left: 20px;
  margin-right: 10px;
}

#group-calendar {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 300px;
  padding-left: 10px;
  margin-right: 30px;
}

#group-date {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 250px;
  padding-left: 5px;
  margin-right: 40px;
}

#group-submit {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 200px;
  padding-left: 20px;
  margin-right: 20px;
}

#location-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

#calendar-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

#date-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

.shadow {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

.btn-submit {
  padding-left: 20px;
}
</style>
