<template>
  <Navbar></Navbar>
  <div class="container-fluid">
    <div class="row align-items-stretch">
      <div class="col-12">
        <div class="h-100">
          <div class="text-center">
            <router-link to="/">
              <img
                src="../assets/SSSlogo.png"
                class="img mx-auto image-style"
              />
            </router-link>
          </div>

          <h1 class="h2 text-center">Create New Attraction</h1>

          <div class="form-information">
            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Attraction Name
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="text"
                          id="attractionName"
                          class="form-control"
                          v-model="attractionName"
                        />
                        <label for="attractionName">Attraction Name</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Description
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="text"
                          id="description"
                          class="form-control"
                          v-model="description"
                        />
                        <label for="description">Description</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Address
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="text"
                          id="address"
                          class="form-control"
                          v-model="address"
                        />
                        <label for="address">Address</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Membership ID
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="text"
                          id="membershipID"
                          class="form-control"
                          v-model="membershipID"
                        />
                        <label for="membershipID">Membership ID</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Expiry Date
                  </div>
                  <div>
                    <form>
                      <div class="form-floating mb-2">
                        <input
                          type="number"
                          id="expiryDay"
                          class="form-control"
                          v-model="expiryDay"
                        />
                        <label for="expiryDay">Expiry Day</label>
                      </div>
                      <div class="form-floating mb-2">
                        <input
                          type="number"
                          id="expiryMonth"
                          class="form-control"
                          v-model="expiryMth"
                        />
                        <label for="expiryMonth">Expiry Month</label>
                      </div>
                      <div class="form-floating">
                        <input
                          type="number"
                          id="expiryYear"
                          class="form-control"
                          v-model="expiryYr"
                        />
                        <label for="expiryYear">Expiry Year</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Benefits
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="text"
                          id="benefits"
                          class="form-control"
                          v-model="benefits"
                        />
                        <label for="benefits">Benefits</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Terms and Conditions
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="text"
                          id="terms-conditions"
                          class="form-control"
                          v-model="termsConditions"
                        />
                        <label for="terms-conditions"
                          >Terms and Conditions</label
                        >
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Pass Type
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="text"
                          id="passType"
                          class="form-control"
                          v-model="passType"
                        />
                        <label for="passType">Pass Type</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Replacement Fee
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="number"
                          min="0"
                          id="replacementFee"
                          class="form-control"
                          v-model="replacementFee"
                        />
                        <label for="replacementFee">Replacement Fee</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Maximum Number of Passes
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="number"
                          min="0"
                          id="maximumPasses"
                          class="form-control"
                          v-model="maximumPasses"
                        />
                        <label for="maximumPasses"
                          >Maximum Number of Passes</label
                        >
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Number of Accompanying Guests
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="number"
                          min="0"
                          id="accompanyingGuests"
                          class="form-control"
                          v-model="accompanyingGuests"
                        />
                        <label for="accompanyingGuests"
                          >Number of Accompanying Guests</label
                        >
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <hr class="hr-block" />

            <div class="d-flex">
              <div class="row flex-grow-1 mx-2">
                <div class="align-items">
                  <div class="d-flex flex-grow-1 justify-content-between pb-2">
                    Maximum Number of Loans
                  </div>
                  <div>
                    <form>
                      <div class="form-floating">
                        <input
                          type="number"
                          min="1"
                          max="99"
                          id="maxLoans"
                          class="form-control"
                          v-model="maxLoans"
                        />
                        <label for="maxLoans">Maximum Number of Loans</label>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
            <div class="text-center mt-4">
              <button
                @click="postreq"
                type="submit"
                class="w-40 btn btn-outline-success text-uppercase fw-bold"
              >
                Save
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";

// Typings
interface CreateAttractionData {
  attractionName: string | null;
  description: string | null;
  passType: string | null;
  replacementFee: number | null;
  accompanyingGuests: number | null;
  maximumPasses: number | null;
  maxLoans: number | null;
  address: string | null;
  membershipID: string | null;
  expiryYr: number | null;
  expiryMth: number | null;
  expiryDay: number | null;
  benefits: string | null;
  termsConditions: string | null;
  staffId: number | null;
  role: string | null;
}

interface LoginData {
  staffId: number;
  role: string;
}

export default defineComponent({
  data(): CreateAttractionData {
    return {
      attractionName: null,
      description: null,
      address: null,
      membershipID: null,
      expiryYr: null,
      expiryMth: null,
      expiryDay: null,
      benefits: null,
      termsConditions: null,
      passType: null,
      replacementFee: null,
      accompanyingGuests: null,
      maximumPasses: null,
      maxLoans: null,
      staffId: null,
      role: null,
    };
  },
  created() {
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
    async postreq(e: Event) {
      e.preventDefault();

      axios({
        url: import.meta.env.VITE_API_URL + "api/attraction",
        method: "post",
        data: {
          name: this.attractionName,
          description: this.description,
          passType: this.passType,
          replacementFee: this.replacementFee,
          numAccompanyingGuests: this.accompanyingGuests,
          maxPassesPerLoan: this.maximumPasses,
          maxLoansPerMonth: this.maxLoans,
          address: this.address,
          membershipId: this.membershipID,
          expiryDateYYYY: this.expiryYr,
          expiryDateMM: this.expiryMth,
          expiryDateDD: this.expiryDay,
          benefits: this.benefits,
          termsConditions: this.termsConditions,
        },
        headers: {'authorization': `${localStorage.getItem("token")}`}
      })
        .then((res) => {
          if (res.data.code === 200) {
            this.$router
              .push({ name: "attraction" })
              .then(() => this.$router.go(0));
          }
        })
        .catch((err: any) => {
          if (err.response) {
            if (err.response.status === 401) {
              this.$router
                .push({ name: "login" })
                .then(() => this.$router.go(0));
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
        });
    },
  },
});
</script>

<style scoped>
.form-information {
  margin-top: 5px;
  margin-bottom: 30px;
  border: 2px solid black;
  border-radius: 10px;
  margin-left: 20%;
  margin-right: 20%;
  padding: 10px;
}
</style>
