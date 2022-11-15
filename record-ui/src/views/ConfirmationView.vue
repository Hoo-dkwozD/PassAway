<template>
    <Navbar></Navbar>
    <div class="container-fluid">
        <div class="row">
            <div class="col-12">
                <h1>Confirmation</h1>

                <p>Thank you for your booking</p>
                
            </div>
        </div>

    </div>

</template>

<script lang="ts">
import {defineComponent} from 'vue'
import Navbar from '../components/Navbar.vue'
export default defineComponent({
    name: 'Confirmation',
    components:{
        Navbar
    }
})
</script>


