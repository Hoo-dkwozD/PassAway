<template>
  <div id="top" class="overflow-auto" :style="{ backgroundImage: `url(${currentBackground})` }">
    <div class="content">
      <div class="container-fluid">
        <div class="row align-items-stretch">
          <div class="col-12">
            <div class="h-100 content">
              <div class="text-center">
                <router-link to="/">
                  <img src="../assets/SSSlogo.png" class="img mx-auto image-style" />
                </router-link>
              </div>

              <h1 class="h2 text-center">Attraction: {{ attraction.name }}</h1>
              <div class="form-information">
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Name
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0" @click="editFlags.name = !editFlags.name">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div v-if="!editFlags.name">
                          <div class="d-flex border rounded p-2">
                            {{ attraction.name }}
                          </div>
                        </div>
                        <div v-else>
                          <form>
                            <div class="form-floating firstName mb-2">
                              <input type="text" id="firstName" class="form-control" :value="attraction.name" />
                              <label for="firstName">Name</label>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Membership ID
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0"
                                  @click="editFlags.membershipId = !editFlags.membershipId">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div v-if="!editFlags.membershipId">
                          <div class="d-flex border rounded p-2">
                            {{ attraction.membershipId }}
                          </div>
                        </div>
                        <div v-else>
                          <form>
                            <div class="form-floating firstName mb-2">
                              <input type="text" id="firstName" class="form-control" :value="attraction.membershipId" />
                              <label for="firstName">Membership ID</label>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Expiry Date
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0" @click="editFlags.expiryDate = !editFlags.expiryDate">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div v-if="!editFlags.expiryDate">
                          <div class="d-flex border rounded p-2">
                            {{ attraction.expiryDate }}
                          </div>
                        </div>
                        <div v-else>
                          <form>
                            <div class="form-floating firstName mb-2">
                              <input type="text" id="firstName" class="form-control" :value="attraction.expiryDate" />
                              <label for="firstName">Expiry Date</label>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Description
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0"
                                  @click="editFlags.description = !editFlags.description">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div v-if="!editFlags.description">
                        <div v-if="attraction.description != ''" class="d-flex border rounded p-2">
                          {{ attraction.description }}
                        </div>
                      </div>
                      <div v-else>
                        <form class="form-floating">
                          <input type="text" id="name" class="form-control" :value="attraction.description" />
                          <label for="name">Description</label>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="hr-block" />

                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Address
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0" @click="editFlags.address = !editFlags.address">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div v-if="!editFlags.address">
                        <span class="d-flex border rounded p-2">
                          {{ attraction.address }}
                        </span>
                      </div>
                      <div v-else>
                        <form class="form-floating">
                          <input type="text" id="name" class="form-control" :value="attraction.address" />
                          <label for="name">Address</label>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Benefits
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0" @click="editFlags.benefits = !editFlags.benefits">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div v-if="!editFlags.benefits">
                        <div class="d-flex border rounded p-2">
                          {{ attraction.benefits }}
                        </div>
                      </div>
                      <div v-else>
                        <form>
                          <div class="form-floating firstName mb-2">
                            <input type="text" id="firstName" class="form-control" :value="attraction.benefits" />
                            <label for="firstName">Benefits</label>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Pass Type
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0" @click="editFlags.passType = !editFlags.passType">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div v-if="!editFlags.passType">
                        <div class="d-flex border rounded p-2">
                          {{ attraction.passType }}
                        </div>
                      </div>
                      <div v-else>
                        <form>
                          <div class="form-floating firstName mb-2">
                            <input type="text" id="firstName" class="form-control" :value="attraction.passType" />
                            <label for="firstName">Pass Type</label>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Replacement Fee
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0"
                                  @click="editFlags.replacementFee = !editFlags.replacementFee">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div v-if="!editFlags.replacementFee">
                        <div class="d-flex border rounded p-2">
                          {{ attraction.replacementFee }}
                        </div>
                      </div>
                      <div v-else>
                        <form>
                          <div class="form-floating firstName mb-2">
                            <input type="text" id="firstName" class="form-control" :value="attraction.replacementFee" />
                            <label for="firstName">Replacement Fee</label>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Accompanying Guests
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0"
                                  @click="editFlags.numAccompanyingGuests = !editFlags.numAccompanyingGuests">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div v-if="!editFlags.numAccompanyingGuests">
                        <div class="d-flex border rounded p-2">
                          {{ attraction.numAccompanyingGuests }}
                        </div>
                      </div>
                      <div v-else>
                        <form>
                          <div class="form-floating firstName mb-2">
                            <input type="text" id="firstName" class="form-control"
                              :value="attraction.numAccompanyingGuests" />
                            <label for="firstName">Number of Accompanying Guests</label>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Max Passes Per Loan
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0"
                                  @click="editFlags.maxPassesPerLoan = !editFlags.maxPassesPerLoan">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div v-if="!editFlags.maxPassesPerLoan">
                        <div class="d-flex border rounded p-2">
                          {{ attraction.maxPassesPerLoan }}
                        </div>
                      </div>
                      <div v-else>
                        <form>
                          <div class="form-floating firstName mb-2">
                            <input type="text" id="firstName" class="form-control"
                              :value="attraction.maxPassesPerLoan" />
                            <label for="firstName">Max Passes Per Loan</label>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="hr-block" />
                <div class="d-flex">
                  <div class="row flex-grow-1">
                    <div>
                      <div class="align-items">
                        <div id="personal-details">
                          <div class="d-flex flex-grow-1 justify-content-between pb-2">
                            Max Loan Per Month
                            <div>
                              <div id="edit">
                                <button class="btn btn-link p-0"
                                  @click="editFlags.maxLoansPerMonth = !editFlags.maxLoansPerMonth">
                                  Edit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div v-if="!editFlags.maxLoansPerMonth">
                        <div class="d-flex border rounded p-2">
                          {{ attraction.maxLoansPerMonth }}
                        </div>
                      </div>
                      <div v-else>
                        <form>
                          <div class="form-floating firstName mb-2">
                            <input type="text" id="firstName" class="form-control"
                              :value="attraction.maxLoansPerMonth" />
                            <label for="firstName">Max Loans Per Month</label>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="text-center">
                  <div>
                    <button type="submit" class="w-100 btn btn-outline-success text-uppercase fw-bold">
                      Save
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import axios from "axios";
import Navbar from "./Navbar.vue";

// Typings
interface Attraction {
  name: String;
  description: String;
  address: String;
  benefits: String;
  passType: String;
  replacementFee: String;
  numAccompanyingGuests: String;
  maxPassesPerLoan: String;
  maxLoansPerMonth: String;
  membershipId: String;
  expiryDate: String;
}

interface EditFlags {
  name: Boolean;
  description: Boolean;
  address: Boolean;
  benefits: Boolean;
  passType: Boolean;
  replacementFee: Boolean;
  numAccompanyingGuests: Boolean;
  maxPassesPerLoan: Boolean;
  maxLoansPerMonth: Boolean;
  membershipId: Boolean;
  expiryDate: Boolean;
}

interface AttractionData {
  id: String | String[];
  attraction: Attraction;
  currentBackground: string;
  editFlags: EditFlags;
}

export default defineComponent({
  name: "Profile",
  data(): AttractionData {
    return {
      id: this.$route.params.id,
      currentBackground: "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935",
      attraction: {
        name: "name",
        description: "description",
        address: "address",
        benefits: "benefits",
        passType: "passType",
        replacementFee: "replacementFee",
        numAccompanyingGuests: "numAccompanyingGuests",
        maxPassesPerLoan: "maxPassesPerLoan",
        maxLoansPerMonth: "maxLoansPerMonth",
        membershipId: "membershipId",
        expiryDate: "expiryDate",
      },
      editFlags: {
        name: false,
        description: false,
        address: false,
        benefits: false,
        passType: false,
        replacementFee: false,
        numAccompanyingGuests: false,
        maxPassesPerLoan: false,
        maxLoansPerMonth: false,
        membershipId: false,
        expiryDate: false,
      }
    }
  },
  methods: {
  },
  async created() {
  },
  components: {
    Navbar,
  },
});
</script>

<style>
.change-btn {
  margin: auto;
}

.image-style {
  width: 200px;
  height: 200px;
}

.form-information {
  margin-top: 5px;
  margin-bottom: 30px;
  border: 2px solid black;
  border-radius: 10px;
  margin-left: 30%;
  margin-right: 30%;
  padding: 10px;
}

#top {
  background-image: "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935";
}

.hr-block {
  margin-left: 5%;
  margin-right: 5%;
}
</style>