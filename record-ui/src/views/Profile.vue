<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
    name: 'Profile',
    data() {
        return {
            firstName: "hello",
            editEmail: false,
            editName: false,
            editNumber: false,
            lastName: "",
            email: "",
            contactNumber: 0,
            bookingStatus: "",
        }
    },
    setup() {
        return {
            name: 'Profile'
        }
    }
})

</script>
<template>
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
                            Personal Information
                        </h1>
                        <div class="form-information">

                            <div class="mx-auto col-6">
                                <div class="row">
                                    <div class="col-7">
                                        <div class="align-items">
                                            <div id="personal-details">Legal Name</div>
                                            <div v-if="!editName">
                                                <span>{{ firstName }}</span>
                                            </div>
                                            <div v-else>
                                                <form>
                                                    <div class="form-floating firstName mb-2">
                                                        <input type="text" id="firstName" class="form-control"
                                                            :value="firstName">
                                                        <label for="firstName">First Name</label>
                                                    </div>

                                                    <div class="form-floating lastName">
                                                        <input type="text" id="lastname" class="form-control"
                                                            :value="lastName">
                                                        <label for="lastname">Last Name</label>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-5">
                                        <div id="edit">
                                            <button class="btn btn-link" @click="editName = !editName">
                                                Edit
                                            </button>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <hr class="hr-block">



                            <div class="mx-auto col-6">
                                <div class="row">
                                    <div class="col-7">
                                        <div id="personal-details">Email</div>
                                        <div v-if="!editEmail">
                                            <span>{{ email }}</span>
                                        </div>
                                        <div v-else>
                                            <form class="form-floating">
                                                <input type="text" id="name" class="form-control" :value="email">
                                                <label for="name">Email</label>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col-5">
                                        <div id="edit">
                                            <button class="btn btn-link" @click="editEmail = !editEmail">
                                                Edit
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <hr class="hr-block">

                            <div class="mx-auto col-6">
                                <div class="row">
                                    <div class="col-7">
                                        <div id="personal-details">Contact Number</div>
                                        <div v-if="!editNumber">
                                            <span>{{ contactNumber }}</span>
                                        </div>
                                        <div v-else>
                                            <form class="form-floating">
                                                <input type="text" id="name" class="form-control"
                                                    :value="contactNumber">
                                                <label for="name">Contact Number</label>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col-5">
                                        <div id="edit">
                                            <button class="btn btn-link" @click="editNumber = !editNumber">
                                                Edit
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <hr class="hr-block">

                            <div class="form-floating mx-auto mb-5 col-6">
                                <div id="personal-details">Booking Status</div>
                                <span>{{ bookingStatus }}</span>
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

<style scoped>
.change-btn {
    margin: auto;
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
    padding: 10px;
}

.hr-block {
    margin-left: 5%;
    margin-right: 5%;
}
</style>