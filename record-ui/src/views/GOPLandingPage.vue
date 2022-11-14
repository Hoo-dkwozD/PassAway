<script lang='ts'>
import { defineComponent } from 'vue'
export default defineComponent({
    data(){
        return{
            isActive: false,
        }
    },
  components: {
    
  },
  methods:{
    searchBorrower(){
        //generate html table in the DOM
        this.isActive = !this.isActive;
        console.log("hello");
        
    }
  }
});
</script>

<template>
    <div class="content" style="margin-top: 80px;">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="h-100">
                        <div class="text-center">
                            <router-link to="/">
                                <img src="../assets/SSSlogo.png" class="img mx-auto image-style">
                            </router-link>
                        </div>

                        <div class="borrower-search">
                            <h1 class="h2 text-center">
                                Borrower Information
                            </h1>

                            <div class="form-floating mx-auto mb-3 col-6">
                                <input type="email" th:field="*{username}" class="form-control" id="email"
                                    placeholder="name@example.com">
                                <label for="email">Email address</label>
                            </div>
                            <div class="text-center">
                                <button type="button"
                                    class="btn btn-outline-secondary text-uppercase fw-bold search-btn" @click="searchBorrower()">Search</button>
                            </div>
                            <div :class="{active: isActive}">
                                <table class="table table-striped table-hover mt-3">
                                    <thead>
                                        <tr>
                                            <th scope="col">Name</th>
                                            <th scope="col">Email</th>
                                            <th scope="col">Contact Number</th>
                                            <th scope="col">Card Number</th>
                                            <th scope="col">Start Date</th>
                                            <th scope="col">Place of Interest</th>
                                            <th scope="col">Status</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Mark</td>
                                            <td>Otto</td>
                                            <td>@mdo</td>
                                            <td>@mdo</td>
                                            <td>Date Returned</td>
                                            <td>Gardens By the Bay</td>
                                            <td><button type="button" class="btn btn-outline-secondary">Collected</button></td>

                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    </div>
</template>

<style>
.image-style {
    width: 200px;
    height: 200px;
}

.borrower-search {
    text-align: center;
}
.active{
    display: none;
}
</style>