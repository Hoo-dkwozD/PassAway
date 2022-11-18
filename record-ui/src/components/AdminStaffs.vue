<template>
  <div class="container-fluid p-0 mx-0 position-relative w-100 h-100 d-flex flex-column">
    <div class="h-100" :style="{ backgroundImage: `url(${currentBackground})` }">
      <div
        class="imageDiv p-5 row"
      >
        <div class="h-10">
          <h1 class="text-center p-0">{{ title }}</h1>
        </div>
      <div class="d-flex px-0">
        <form>
            <div class="form-group">
              <label for="exampleFormControlFile1" class="m-2"
                >Upload Staff CSV file</label
              >
              <input
                type="file"
                class="form-control-file"
                id="exampleFormControlFile1"
                @change="handleFileUpload"
              />
              <button class="btn btn-secondary m-2" @click="submit()">
                Submit
              </button>
            </div>
          </form>
        <div class="align-right">
          <button
            type="button"
            class="btn btn-secondary m-2"
            data-bs-toggle="modal"
            data-bs-target="#staticBackdrop"
          >
            Add Staff
          </button>
          <div class="modal fade" id="myModal" role="dialog"></div>
        </div>
        </div>
        <table class="table table-bordered table-hover">
          <thead>
            <tr class="table-active">
              <th scope="col">Staff ID</th>
              <th scope="col">Name</th>
              <th scope="col">Email</th>
              <th scope="col">Contact</th>
              <th scope="col">Role</th>
              <th scope="col">Registered?</th>
              <th scope="col">Lock?</th>
              <th scope="col">Edit</th>
              <th scope="col">Delete</th>
            </tr>
          </thead>
          <tbody v-for="(staff,index) in staffs">
            <tr>
              <th scope="col">{{ staff[0] }}</th>
              <td scope="col">{{ staff[1] }}</td>
              <td scope="col">{{ staff[2] }}</td>
              <td scope="col">{{ staff[3] }}</td>
              <td scope="col">{{ staff[4] }}</td>
              <td scope="col">{{ staff[5] ? "Yes" : "No" }}</td>
              <td scope="col">
                <div class="form-check form-switch d-flex justify-content-center">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="flexSwitchCheckDefault"
                    :checked="staff[7]"
                    @click="checkSwitch(staff)"
                  />
                  <label
                    class="form-check-label"
                    for="flexSwitchCheckDefault"
                  ></label>
                </div>
              </td>
              <td scope="col">
                <button
                  class="btn btn-secondary"
                  :disabled="staff[6]"
                  @click="editStaff(staff[0])"
                >
                  Edit
                </button>
              </td>
              <td>
                <button
                  class="btn btn-danger"
                  :disabled="staff[6]"
                  @click="deleteStaff(staff[0])"
                >
                  Delete
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
          <h3 class="modal-title fs-5" id="exampleModalLabel">
            Add New Staff
          </h3>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <form>
            <div class="mb-3">
              <input
                type="text"
                class="form-control m-2"
                v-model="firstName"
                placeholder="First Name"
              />

              <input
                type="text"
                class="form-control m-2"
                v-model="lastName"
                placeholder="Last Name"
              />

              <input
                type="text"
                class="form-control m-2"
                v-model="inputEmail"
                placeholder="@sportsschool.edu.sg"
              />

              <input
                type="text"
                class="form-control m-2"
                v-model="contact"
                placeholder="Contact Number"
              />
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  name="inlineRadioOptions"
                  id="inlineRadio1"
                  value="0"
                  v-model="role"
                />
                <label class="form-check-label" for="inlineRadio1"
                  >Borrower</label
                >
              </div>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  name="inlineRadioOptions"
                  id="inlineRadio2"
                  value="1"
                  v-model="role"
                />
                <label class="form-check-label" for="inlineRadio2"
                  >Admin</label
                >
              </div>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  name="inlineRadioOptions"
                  id="inlineRadio2"
                  value="2"
                  v-model="role"
                />
                <label class="form-check-label" for="inlineRadio2"
                  >GOP</label
                >
              </div>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-light btn-outline-danger"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            type="button"
            class="btn btn-secondary btn-outline-warning"
            @click="addStaff()"
            data-bs-dismiss="modal"
          >
            Add
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";

//pass the output from the api to the respective table rows
interface Data {
  title: string;
  currentBackground: string;
  staffs: any[];
  staffId: number;
  disableButton: boolean;
  file: string;
  firstName: string;
  lastName: string;
  inputEmail: string;
  email: string;
  contact: string;
  role: string;
  isChecked: any;
}
interface LoginData {
  staffId: number;
  role: string;
}
export default defineComponent({
  data(): Data {
    return {
      title: "All Staffs",
      staffs: [],
      firstName: "",
      lastName: "",
      contact: "",
      staffId: 0,
      email: "",
      inputEmail: "",
      role: "",
      disableButton: false,
      file: "",
      isChecked: "",
      currentBackground:
        "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935",
    };
  },
  async created() {
    const userInfo = this.checkLogin();

    if (userInfo !== undefined && userInfo["role"] !== "ADMINISTRATOR") {
      this.$router.push({ name: "home" });
    }
    try {
      const staffDetails = await axios.get(
        import.meta.env.VITE_API_URL + "api/staff/" + this.staffId
      );
      this.email = staffDetails.data.data.email;
    } catch (err: any) {
      if (err.response.status == 401) {
        this.$router.push({ name: "Login" });
      }
    }

    try {
      const staffDetails = await axios.get(
        import.meta.env.VITE_API_URL + "api/staffs"
      );

      for (const index in staffDetails.data.data) {
        const detail = staffDetails.data.data[index];
        const staffId = detail["staffId"];
        const staffName = detail.firstName + " " + detail.lastName;
        const staffEmail = detail.email;
        const contact = detail.contactNumber;
        const role = detail.role;
        const registered = detail.registered;
        const disabled = detail.disabled;
        const cannotBook = detail.cannotBook;
        this.staffs.push([
          staffId,
          staffName,
          staffEmail,
          contact,
          role,
          registered,
          disabled,
          cannotBook
        ]);
      }
      console.log(this.staffs[0]);
    } catch (err: any) {
      if (err.response.status == 401) {
        this.$router.push({ name: "Login" });
      }
    }
  },
  methods: {
    checkLogin(): LoginData | undefined {
      const staffIdStr = localStorage.getItem("staffId");
      const role = localStorage.getItem("role");

      if (staffIdStr === null || role === null) {
        this.$router.push({ name: "Login" });
      } else {
        this.staffId = parseInt(staffIdStr);

        return {
          staffId: this.staffId,
          role: role,
        };
      }
    },
    async checkSwitch(staff: any[]) {
      // flexSwitchCheckDefault
      this.isChecked = document.getElementById(
        "flexSwitchCheckDefault"
      )?.checked;
      //change local instance of staffs array, 
      console.log(this.isChecked);
      // console.log(staff);
      if (this.isChecked) {
        try {
          const res = await axios.put(
            import.meta.env.VITE_API_URL + "api/staff/" + staff[0] + "/lock"
          );
          console.log("lock");
        } catch (err: any) {
          if (err.response.status == 401) {
            this.$router.push({ name: "Login" });
          }
        }
      } else {
        try {
          const res = await axios.put(
            import.meta.env.VITE_API_URL + "api/staff/" + staff[0] + "/unlock"
          );
          console.log("unlock");
        } catch (err: any) {
          if (err.response.status == 401) {
            this.$router.push({ name: "Login" });
          }
        }
      }
    },
    async addStaff() {
      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "api/staff",
          {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.inputEmail,
            contactNumber: this.contact,
            role: this.role,
          }
        );
        console.log(res);
        this.$router.go(0);
      } catch (err: any) {
        if (err.response.status == 401) {
          this.$router.push({ name: "Login" });
        }
      }
    },
    handleFileUpload(e: Event) {
      if (e.target) {
        this.file = e.target.files;
        console.log(this.file);
      }
    },
    async submit() {
      try {
        const formData = new FormData();
        formData.append("file", this.file[0]);
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "api/staffs",
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
              'authorization': `${localStorage.getItem("token")}`
            },
          }
        );
      } catch (err: any) {
        if (err.response) {
          if (err.response.status == 401) {
            this.$router.push({ name: "login" }).then(() => this.$router.go(0));
          } else if (err.response.status == 403) {
            this.$router
              .push({ name: "home" })
              .then(() => this.$router.go(0));

            return;
          } else {
            console.error(err.response.data.message);
          }
        } else {
          console.error(err.message);
        }
      }
    },
    async editStaff(staffId: number) {
      this.$router.push({
        name: "admin update staff",
        params: { staffId: staffId },
      });
    },

    async deleteStaff(staffId: number) {
      console.log(staffId);
      try {
        const res = await axios.delete(
          import.meta.env.VITE_API_URL + "api/staff/" + staffId.toString()
        );
        this.$router.go(0);
        console.log(res);
      } catch (err: any) {
        if (err.response.status == 401) {
          this.$router.push({ name: "Login" });
        }
      }
    },
  },
});
</script>
    
    <style>
.imageDiv {
  width: 100%;
  background-size: cover;
  padding-top: 300px;
  padding-bottom: 300px;
  background-size: 100%;
}

h1 {
  font-size: 60px;
  padding: 40px;
  margin: 20px;
  font-family: "Trebuchet MS", sans-serif;
  color: black;
  font-style: italic;
}

h2 {
  font-size: 50px;
  padding-bottom: 40px;
}

.bookingfont {
  font-size: 20px;
  font-family: "Trebuchet MS", sans-serif;
  padding: 15px;
}

.tableblock {
  margin-right: 400px;
  margin-left: 400px;
  padding-left: 100px;
  padding-right: 100px;
}

.align-right {
  text-align: right;
}

.btn-block,
.btn-block:focus {
  background-color: #d72255;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  color: white;
}

.btn-block:hover {
  background-color: #f37931;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  color: black;
}
</style>