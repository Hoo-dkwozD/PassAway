<template>
    <Navbar></Navbar>
    <div class="content" style="margin-top: 80px;">
        <div class="container-fluid">
            <div class="row align-items-stretch">
                <div class="col-12">
                    <div class="h-100">
                        <div class="text-center">
                            <router-link to="/">
                                <img src="../assets/SSSlogo.png" class="img mx-auto image-style">
                            </router-link>
                        </div>
                        <h1 class="h2 text-center">
                            Edit Bar Code
                        </h1>
                        <div class="row">
                            <div class="col-12">
                                <select class="form-select mb-3" id="attraction-name">
                                    <option selected disabled>Attraction Name</option>
                                    <option value="gardensbytheway">Gardens By the Way</option>
                                    <option value="singaporezoo">Singapore Zoo</option>
                                </select>
                                <div class="barCode mb-3 text-start">
                                    <label id="barCode">
                                        Barcode Image
                                    </label>
                                    <input type="file" class="input-file"/>
                                </div>
                            </div>
                            <div class="text-center">
                                <button type="submit"
                                    class="btn btn-outline-success text-uppercase fw-bold change-btn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Navbar from '../components/Navbar.vue'

export default defineComponent({
    name: 'EditBarCode',
    components: {
        Navbar
    }
})

</script>

<style>

</style>