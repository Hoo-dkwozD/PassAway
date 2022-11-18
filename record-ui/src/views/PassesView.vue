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
          <option :value="0">All</option>
          <option
            v-for="(attraction, idx) in allAttractions"
            :key="idx"
            :value="attraction['attractionId']"
          >
            {{ attraction["name"] }}
          </option>
        </select>

        <div
          v-if="showAdd"
          class="row d-flex flex-row justify-content-end pb-2"
        >
          <div class="input-group mb-3 w-auto">
            <input
              @change="changeFile"
              type="file"
              class="form-control"
              accept="text/csv"
              id="passCSV"
            />
            <button
              @click="uploadFile()"
              class="btn btn-outline-secondary"
              type="button"
              id="passCSVSubmit"
            >
              Upload
            </button>
          </div>
          <input
            class="btn btn-company-orange w-auto mx-3 mb-3"
            type="button"
            value="Add Pass"
            data-bs-toggle="modal"
            data-bs-target="#staticBackdrop"
          />
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
          <h3 class="modal-title fs-5" id="staticBackdropLabel">Add Pass</h3>
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
              <label for="exampleInputEmail1" class="form-label">Pass ID</label>
              <input
                v-model="passIdToAdd"
                type="text"
                class="form-control"
                id="exampleInputEmail1"
                aria-describedby="emailHelp"
              />
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button @click="addPass()" type="button" class="btn btn-danger">
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
  passIdToAdd: string | null;
  uploadingFile: any | null;
}

export default defineComponent({
  components: {
    Navbar,
  },
  data(): PassesViewData {
    return {
      allAttractions: [],
      allPasses: [],
      attId: null,
      staffId: null,
      role: null,
      showAdd: false,
      passIdToAdd: null,
      uploadingFile: null,
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

        try {
          res = await axios.get(
            import.meta.env.VITE_API_URL + `api/pass/list`,
            {
              headers: {'authorization': `${localStorage.getItem("token")}`},
            }
          );

          this.allPasses = await res.data.data;
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
      } else {
        try {
          res = await axios.get(
            import.meta.env.VITE_API_URL +
              `api/pass/list-by-attraction?attractionId=${this.attId}`,
            {
              headers: {'authorization': `${localStorage.getItem("token")}`},
            }
          );
          this.showAdd = true;

          this.allPasses = await res.data.data;
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
      }
    },
    async addPass() {
      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "api/pass/add",
          {
            attractionId: this.attId,
            passId: this.passIdToAdd,
          },
          {
            headers: {'authorization': `${localStorage.getItem("token")}`},
          }
        );
        const data = res.data;

        if (data.code === 200) {
          this.$router.push({ name: "passes" }).then(() => this.$router.go(0));
        }
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
    changeFile(e: Event) {
      if (e.target) {
        this.uploadingFile = e.target.files[0];
      }
    },
    async uploadFile() {
      const reqData = new FormData();

      if (this.uploadingFile !== null) {
        reqData.append("file", this.uploadingFile);

        try {
          const res = await axios.post(
            import.meta.env.VITE_API_URL + `api/pass/add-csv/${this.attId}`,
            reqData,
            {
              headers: {
                "Content-Type": this.uploadingFile.type,
                'authorization': `${localStorage.getItem("token")}`,
              },
            }
          );
          const data = await res.data;

          if (data.code === 200) {
            this.$router
              .push({ name: "passes" })
              .then(() => this.$router.go(0));
          }
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
      }
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
      let res = await axios.get(
        import.meta.env.VITE_API_URL + "api/pass/list",
        {
          headers: {'authorization': `${localStorage.getItem("token")}`},
        }
      );
      this.allPasses = await res.data.data;

      res = await axios.get(
        import.meta.env.VITE_API_URL + "api/attractions",
        {
          headers: {'authorization': `${localStorage.getItem("token")}`},
        }
      );
      this.allAttractions = await res.data.data;
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
});
</script>

<style>
.lost {
  color: red;
}
</style>
