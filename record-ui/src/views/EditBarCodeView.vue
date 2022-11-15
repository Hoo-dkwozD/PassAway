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
                  >
                    <option selected disabled>Attraction Name</option>
                    <option value="gardensbytheway">Gardens By the Way</option>
                    <option value="singaporezoo">Singapore Zoo</option>
                  </select>
                </div>
  
                <div class="barCode mb-3"> 
                  <div class="d-flex flex-column flex-md-row justify-content-between align-items-center mb-3 w-100">
                      <label id="barCode" class="text-start w-25" style="margin-right: 10px"> Barcode Image </label>
                      <label for="formFile" class="form-label"></label>
                      <input class="form-control" type="file" id="formFile">
                  </div>
                </div>
              </div>
              <div class="text-center">
                <button
                  type="submit"
                  class="w-100 btn btn-outline-success text-uppercase fw-bold"
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
import Navbar from "../components/Navbar.vue";

export default defineComponent({
  name: "EditBarCode",
  components: {
    Navbar,
  },
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