<script lang="ts">
import { defineComponent } from "vue";
import axios from "axios";
import Navbar from "../components/Navbar.vue";

// Typings
interface ProfileData {
  staffId: number | null;
  firstName: string | null;
  lastName: string | null;
  email: string | null;
  contactNumber: string | null;
  bookingStatus: boolean | null;
  editEmail: boolean;
  editNumber: boolean;
  editName: boolean;
  currentBackground: string;
}

interface LoginData {
  staffId: number;
  role: string;
}

export default defineComponent({
  name: "Profile",
  data(): ProfileData {
    return {
      staffId: null,
      firstName: null,
      lastName: null,
      email: null,
      contactNumber: null,
      bookingStatus: false,
      editEmail: false,
      editName: false,
      editNumber: false,
      currentBackground:
        "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935",
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
    async updateProfile() {
      try {
        const res = await axios.put(
          import.meta.env.VITE_API_URL + "api/staff/" + this.staffId,
          {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email,
            contactNumber: this.contactNumber,
          }
        );
        const data = await res.data;
        if (data.code === 200) {
          // this.$router.go(0);
        } else if (data.code === 401) {
          this.$router.push({ name: "Login" });
        } else {
          console.error(data.message);
        }
      } catch (err) {
        console.error(err);
      }
    },
  },
  async created() {
    const loginData: LoginData | undefined = this.checkLogin();

    if (loginData !== undefined) {
      this.staffId = loginData.staffId;

      try {
        const res = await axios.get(
          import.meta.env.VITE_API_URL + "api/staff/" + loginData.staffId
        );
        const data = await res.data;

        if (data.code === 200) {
          this.firstName = data.data.firstName;
          this.lastName = data.data.lastName;
          this.email = data.data.email;
          this.contactNumber = data.data.contactNumber;
          this.bookingStatus = data.data.cannotBook;
        } else {
          console.error(data.message);
        }
      } catch (err) {
        console.error(err);
      }
    }
  },
  computed: {
    fullName(): string {
      return this.firstName + " " + this.lastName;
    },
  },
  components: {
    Navbar,
  },
});
</script>

<template>
  <Navbar></Navbar>
  <div class="container-fluid m-0">
    <div
      class="imageDiv p-5 mb-5 row"
      :style="{ backgroundImage: `url(${currentBackground})` }"
    >
      <div class="content">
        <div class="container-fluid m-0">
          <div class="row align-items-stretch">
            <div class="col-12">
              <div class="h-100 content">
                <div class="text-center">
                  <router-link to="/">
                    <img
                      src="../assets/SSSlogo.png"
                      class="img mx-auto image-style"
                    />
                  </router-link>
                </div>

                <h1 class="h2 text-center">Personal Information</h1>
                <div class="form-information">
                  <div class="d-flex">
                    <div class="row flex-grow-1">
                      <div>
                        <div class="align-items">
                          <div id="personal-details">
                            <div
                              class="d-flex flex-grow-1 justify-content-between pb-2"
                            >
                              Legal Name
                              <div>
                                <div id="edit">
                                  <button
                                    class="btn btn-link p-0"
                                    @click="editName = !editName"
                                  >
                                    Edit
                                  </button>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div v-if="!editName">
                            <div class="d-flex border rounded p-2">
                              {{ firstName }}
                            </div>
                          </div>
                          <div v-else>
                            <form>
                              <div class="form-floating firstName mb-2">
                                <input
                                  type="text"
                                  id="firstName"
                                  class="form-control"
                                  v-model="firstName"
                                />
                                <label for="firstName">First Name</label>
                              </div>

                              <div class="form-floating lastName">
                                <input
                                  type="text"
                                  id="lastname"
                                  class="form-control"
                                  v-model="lastName"
                                />
                                <label for="lastname">Last Name</label>
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
                            <div
                              class="d-flex flex-grow-1 justify-content-between pb-2"
                            >
                              Email
                              <div>
                                <div id="edit">
                                  <button
                                    class="btn btn-link p-0"
                                    @click="editEmail = !editEmail"
                                  >
                                    Edit
                                  </button>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div v-if="!editEmail">
                          <div
                            v-if="email != ''"
                            class="d-flex border rounded p-2"
                          >
                            {{ email }}
                          </div>
                        </div>
                        <div v-else>
                          <form class="form-floating">
                            <input
                              type="text"
                              id="name"
                              class="form-control"
                              v-model="email"
                            />
                            <label for="name">Email</label>
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
                            <div
                              class="d-flex flex-grow-1 justify-content-between pb-2"
                            >
                              Contact Number
                              <div>
                                <div id="edit">
                                  <button
                                    class="btn btn-link p-0"
                                    @click="editNumber = !editNumber"
                                  >
                                    Edit
                                  </button>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div v-if="!editNumber">
                          <span class="d-flex border rounded p-2">{{
                            contactNumber
                          }}</span>
                        </div>
                        <div v-else>
                          <form class="form-floating">
                            <input
                              type="text"
                              id="name"
                              class="form-control"
                              v-model="contactNumber"
                            />
                            <label for="name">Contact Number</label>
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
                            <div
                              class="d-flex flex-grow-1 justify-content-between pb-2"
                            >
                              Booking Status
                            </div>
                          </div>
                        </div>
                        <div>
                          <span
                            class="d-flex border rounded p-2 mb-3"
                            v-if="!bookingStatus"
                          >
                            <p class="text-success">
                              You are allowed to make bookings
                            </p>
                          </span>
                          <span class="d-flex border rounded p-2 mb-3" v-else>
                            <p class="text-danger">
                              You are not allowed to make bookings
                            </p>
                          </span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="text-center">
                    <div>
                      <button
                        @click="updateProfile"
                        type="submit"
                        class="w-100 btn btn-outline-success text-uppercase fw-bold mb-2"
                      >
                        Save
                      </button>

                      <RouterLink to="/profilepassword">
                        <button
                          type="submit"
                          class="w-100 btn btn-outline-dark text-uppercase fw-bold"
                        >
                          Change Password
                        </button>
                      </RouterLink>
                    </div>
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

<style scoped>
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

#app {
  height: 70%!important;
}
</style>
