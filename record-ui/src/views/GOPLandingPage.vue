<script lang='ts'>
import { defineComponent } from 'vue'
export default defineComponent({
  components: {
    
  },
  methods:{
    searchBorrower(){
        //generate html table in the DOM
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
</style>