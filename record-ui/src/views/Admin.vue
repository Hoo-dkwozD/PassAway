<template>
    <Navbar></Navbar>
    <div class="container-fluid">
        <div class="row">
            <div class="col-12">
                <h1>Admin View</h1>

            </div>
        </div>
        <div class="row">
            <div class="col-12">
            </div>
        </div>
    </div>
</template>
<script lang="ts">
import {defineComponent} from 'vue'
import Navbar from '../components/Navbar.vue'
export default defineComponent ({
    name: 'Admin',
    components:{
        Navbar
    }
})
</script>
<style>

</style>