<script lang="ts">
import { defineComponent } from 'vue';
import axios from 'axios';
import Navbar from '../components/Navbar.vue';

// Typings
interface ProfileData {
    staffId: Number | null,
    firstName: String | null,
    lastName: String | null,
    email: String | null,
    role: String | null,
    contactNumber: String | null,
    bookingStatus: Boolean | null,
}

interface LoginData {
    staffId: Number,
    role: String,
}

export default defineComponent({
    name: 'StaffAddView',
    data(): ProfileData {
        return {
            role: null,
            staffId: null,
            firstName: null,
            lastName: null,
            email: null,
            contactNumber: null,
            bookingStatus: false,
        };
    },
    methods: {
        checkLogin(): LoginData | undefined {
            let staffIdStr = localStorage.getItem("staffId");
            let role = localStorage.getItem("role");

            if (staffIdStr === null || role === null) {
                this.$router.push({ name: 'Login' });
            } else {
                let staffId = parseInt(staffIdStr);

                return {
                    staffId: staffId,
                    role: role,
                };
            }
        },
        async addUser() {
            axios.post('http://localhost:3000/staff', {
                email: this.email,
                firstName: this.firstName,
                lastName: this.lastName,
                contactNumber: this.contactNumber,
                role: this.role,
            })
        }
    },
    computed: {
        fullName(): String {
            return this.firstName + " " + this.lastName;
        }
    },
    components: {
        Navbar
    }
})
</script>

<template>
    <Navbar></Navbar>

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
                        Add a New Staff
                    </h1>

                    <div class="form-information">
                        <div class="mx-auto col-6">
                            <div class="row">
                                <div class="col-12">
                                    <div class="align-items">
                                        <div class="text-start" id="personal-details">Legal Name</div>
                                        <div>
                                            <form>
                                                <div class="form-floating firstName mb-2">
                                                    <input type="text" id="firstName" class="form-control"
                                                        v-model="firstName">
                                                    <label for="firstName">First Name</label>
                                                </div>

                                                <div class="form-floating lastName">
                                                    <input type="text" id="lastname" class="form-control"
                                                        v-model="lastName">
                                                    <label for="lastname">Last Name</label>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr class="hr-block">

                        <div class="mx-auto col-6">
                            <div class="row">
                                <div class="col-12">
                                    <div id="personal-details" class="text-start">Contact Number</div>
                                    <div>
                                        <form class="form-floating">
                                            <input type="text" id="name" class="form-control" v-model="contactNumber">
                                            <label for="name">Contact Number</label>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr class="hr-block">

                        <div class="mx-auto col-6 mb-3">
                            <div class="row">
                                <div class="col-12">
                                    <div id="personal-details" class="text-start">
                                        Role
                                    </div>
                                    <div>
                                        <form class="form-floating">
                                            <input type="text" id="name" class="form-control" v-model="role">
                                            <label for="name">Role</label>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="w-100 btn btn-outline-success text-uppercase fw-bold">
                                Save
                            </button>
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