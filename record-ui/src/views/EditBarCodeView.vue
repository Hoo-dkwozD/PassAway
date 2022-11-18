<template>
    <Navbar></Navbar>
    <div class="content">
      <div class="container-fluid">
        <div class="text-center">
          <router-link to="/">
            <img src="../assets/SSSlogo.png" class="img mx-auto image-style" />
          </router-link>
        </div>
        <h1 class="h2 text-center">Edit Bar Code</h1>
        <div class="form-information">
          <div class="row">
            <div class="h-100">
              <div class="col-12" id="information-container">
                <div class="d-flex flex-column flex-md-row justify-content-between align-items-center mb-3 w-100">
                  <span for="name" class="text-start w-25" style="margin-right: 10px">Attraction Name</span>
                  <select
                    class="form-select"
                    id="attraction-name"
                    v-model="selectedAttr"
                  >
                    <option 
                      v-for="attr, idx in attractions" :value="attr.attractionId" :key="idx"
                    >
                      {{ attr.name }}
                    </option>
                  </select>
                </div>
  
                <div class="barCode mb-3"> 
                  <div class="d-flex flex-column flex-md-row justify-content-between align-items-center mb-3 w-100">
                      <label id="barCode" class="text-start w-25" style="margin-right: 10px"> Barcode Image </label>
                      <label for="formFile" class="form-label"></label>
                      <input @change="changeImage" class="form-control" type="file" accept="image/jpeg,image/jpg,image/png" id="formFile">
                  </div>
                </div>
              </div>
              <div class="text-center">
                <button
                  type="submit"
                  class="w-100 btn btn-outline-success text-uppercase fw-bold"
                  @click="uploadBarcodeImage"
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
import { defineComponent } from "vue";
import axios from 'axios';

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

interface EditBarCodeView {
  staffId: number | null;
  role: string | null;
  attractions: Attraction[],
  selectedAttr: number | null,
  uploadedImg: File | null
}

export default defineComponent({
  name: "EditBarCode",
  components: {
    Navbar,
  },
  data(): EditBarCodeView {
    return {
      staffId: null,
      role: null,
      attractions: [],
      selectedAttr: null,
      uploadedImg: null
    };
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
    changeImage(e: Event) {
      if (e.target) {
        this.uploadedImg = e.target.files[0];
      }
    },
    async uploadBarcodeImage() {
      const reqData = new FormData();

      if (this.uploadedImg !== null) {
        reqData.append("file", this.uploadedImg);

        try {
          const res = await axios.post(
            import.meta.env.VITE_API_URL + `api/attraction/${this.selectedAttr}/barcodeImage`,
            reqData,
            {
              headers: {
                'Content-Type': this.uploadedImg.type
              }
            }
          );
          const data = await res.data;

          if (data.code === 200) {
            this.$router.push({ name: 'attraction' }).then(() => this.$router.go(0));
          }
        } catch (err: any) {
          if (err.response) {
            if (err.response.status === 401) {
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
              console.error(err.response.data.message);
            }
          } else {
            console.error(err.message);
          }
        }
      }
    }
  },
  async created() {
    const loginData: LoginData | undefined = this.checkLogin();

    if (loginData !== undefined) {
      if (loginData.role !== "ADMINISTRATOR") {
        this.$router.push({ name: "home" }).then(() => this.$router.go(0));
      } else {
        this.staffId = loginData.staffId;
        this.role = loginData.role;

        try {
          const attrRes = await axios.get(
            import.meta.env.VITE_API_URL + `api/attractions`,
            {
              headers: {
                "authorization": `${localStorage.getItem("token")}`,
              },
            }
          );
          this.attractions = await attrRes.data.data;
        } catch (err: any) {
          if (err.response) {
            if (err.response.status === 401) {
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
              console.error(err.response.data.message);
            }
          } else {
            console.error(err.message);
          }
        }
      }
    }
  }
});
</script>

<style scoped>
.content {
  padding: 20px;
  margin-top: 80px;
}

#information-container {
  display: flex;
  flex-direction: column;
}

.form-information {
  margin-top: 5px;
  margin-bottom: 30px;
  border: 2px solid black;
  border-radius: 10px;
  margin-left: 25%;
  margin-right: 25%;
  padding: 30px;
}

@media screen and (max-width: 768px) {
  .form-information {
    margin: 0;
  }
}
</style>

<style>
#information-container{
    margin: auto;
}
</style>