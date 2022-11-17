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
      bookingStatus: true,
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
        this.$router.push({ name: "Login" });
      } else {
        const staffId = parseInt(staffIdStr);

        if (role !== "ADMINISTRATOR") {
          this.$router.push({ name: "home" });
        }

        return {
          staffId: staffId,
          role: role,
        };
      }
    },
    returnToStaffs() {
      this.$router.push({ name: "admin staffs" });
    },
    async updateProfile() {
      try {
        const res = await axios.put(
          import.meta.env.VITE_API_URL +
            "api/staff/" +
            this.$route.params.staffId,
          {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email,
            contactNumber: this.contactNumber,
          }
        );
        const data = await res.data;

        if (data.code === 200) {
          this.$router.go(0);
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
          import.meta.env.VITE_API_URL +
            "api/staff/" +
            this.$route.params.staffId
        );
        console.log(res);

        const details = res.data.data;

        this.firstName = details.firstName;
        this.lastName = details.lastName;
        this.email = details.email;
        this.contactNumber = details.contactNumber;
      } catch (err: any) {
        if (err.response.status === 401) {
          this.$router.push({ name: "Login" });
        }
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
  <div
    class="vh-100 container-fluid p-0 mx-0 position-relative w-100 d-flex flex-column"
    :style="{ backgroundImage: `url(${currentBackground})` }"
    style="margin-top: 0px"
  >
    <div class="container-fluid">
      <div class="row align-items-stretch">
        <div class="col-12">
          <div class="h-100">
            <div class="text-center">
              <div class="p-5"></div>
              <router-link to="/">
                <img
                  src="@/assets/SSSlogo.png"
                  class="img mx-auto image-style"
                />
              </router-link>
            </div>

            <h1 class="h2 text-center">Staff Personal Information</h1>
            <div v-if="!bookingStatus" class="form-information">
              <div class="mx-auto mb-5 col-6">
                <h3 class="text-danger text-center">Account Locked!</h3>
              </div>
            </div>
            <div v-else class="form-information">
              <div class="mx-auto col-6">
                <div class="row">
                  <div class="col-7">
                    <div class="align-items">
                      <div id="personal-details">Legal Name</div>
                      <div v-if="!editName">
                        <span>{{ fullName }}</span>
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
                  <div class="col-5">
                    <div id="edit">
                      <button
                        class="btn btn-link"
                        @click="() => (editName = !editName)"
                      >
                        Edit
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <hr class="hr-block" />

              <div class="mx-auto col-6">
                <div class="row">
                  <div class="col-7">
                    <div id="personal-details">Email</div>
                    <div v-if="!editEmail">
                      <span>{{ email }}</span>
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
                  <div class="col-5">
                    <div id="edit">
                      <button
                        class="btn btn-link"
                        @click="() => (editEmail = !editEmail)"
                      >
                        Edit
                      </button>
                    </div>
                  </div>
                </div>
              </div>

              <hr class="hr-block" />

              <div class="mx-auto col-6">
                <div class="row">
                  <div class="col-7">
                    <div id="personal-details">Contact Number</div>
                    <div v-if="!editNumber">
                      <span>{{ contactNumber }}</span>
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
                  <div class="col-5">
                    <div id="edit">
                      <button
                        class="btn btn-link"
                        @click="() => (editNumber = !editNumber)"
                      >
                        Edit
                      </button>
                    </div>
                  </div>
                </div>
              </div>

              <div class="text-center">
                <button
                  @click="updateProfile()"
                  type="submit"
                  class="btn btn-outline-success text-uppercase fw-bold change-btn m-2"
                >
                  Save
                </button>
                <button
                  @click="returnToStaffs()"
                  class="btn btn-light btn-outline-warning text-uppercase fw-bold change-btnm-2"
                >
                  Back
                </button>
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

.hr-block {
  margin-left: 5%;
  margin-right: 5%;
}
</style>
