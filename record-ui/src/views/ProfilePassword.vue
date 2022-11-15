<script lang="ts">
import { defineComponent } from 'vue'
import Navbar from '../components/Navbar.vue'
export default defineComponent({
    name: 'ProfilePassword',
    components:{
        Navbar
    },
    data() {
        return {
            editPassword: false,
            oldPassword: '',
            newPassword: '',
            confirmNewPassword: '',
        }
    }
})

</script>
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
                            Change Password
                        </h1>
                        <div class="form-information">

                            <div class="d-flex mb-5">
                                <div class="row flex-grow-1">
                                    <form>
                                        <div class="form-floating mb-3">
                                            <input type="text" id="oldPassword" class="form-control">
                                            <label for="oldPassword">Old Password</label>
                                        </div>

                                        <div class="form-floating mb-3">
                                            <input type="text" id="newPassword" class="form-control">
                                            <label for="newPassword">New Password</label>
                                        </div>

                                        <div class="form-floating">
                                            <input type="text" id="confirmNewPassword" class="form-control">
                                            <label for="confirmNewPassword">Confirm New Password</label>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="text-center">
                                <button type="submit"
                                    class="w-100 btn btn-outline-success text-uppercase fw-bold">Save</button>
                            </div>
                        </div>


                    </div>
                </div>

            </div>
        </div>
    </div>

</template>

<style scoped>

.content {
    padding: 20px;
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
    padding: 30px;
}

@media screen and (max-width: 700px){
    .form-information {
        margin: 0;
    }
}
</style>