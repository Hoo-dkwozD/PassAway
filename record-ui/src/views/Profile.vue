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
    contactNumber: String | null,
    bookingStatus: Boolean | null,
    editEmail: Boolean,
    editNumber: Boolean,
    editName: Boolean
}

interface LoginData {
    staffId: Number;
    role: String;
}

export default defineComponent({
    name: 'Profile',
    data(): ProfileData {
        return {
            staffId: null,
            firstName: null,
            lastName: null,
            email: null,
            contactNumber: null,
            bookingStatus: false,
            editEmail: false,
            editName: false,
            editNumber: false
        };
    },
    methods: {
        checkLogin(): LoginData | undefined {
            let staffIdStr = localStorage.getItem("staffId");
            let role = localStorage.getItem("role");

            if (staffIdStr === null || role === null) {
                this.$router.push("/signup");
            } else {
                let staffId = parseInt(staffIdStr);

                return {
                    staffId: staffId,
                    role: role,
                };
            }
        },
        async updateProfile() {
            try {
                const res = await axios.put(
                    import.meta.env.VITE_API_URL + "api/staff/" + this.staffId,
                    {
                        firstName: this.firstName,
                        lastName: this.lastName,
                        email: this.email,
                        contactNumber: this.contactNumber,
                    }
                );
                const data = await res.data;

                if (data.code === 200) {
                    this.$router.go(0);
                } else {
                    console.error(data.message);
                }
            } catch (err) {
                console.error(err);
            }
        }
    },
    async created() {
        let loginData: LoginData | undefined = this.checkLogin();

        if (loginData !== undefined) {
            this.staffId = loginData.staffId;

            try {
                const res = await axios.get(
                    import.meta.env.VITE_API_URL + "api/staff/" + loginData.staffId
                );
                const data = await res.data;

                if (data.code === 200) {
                    this.firstName = data.data.firstName;
                    this.lastName = data.data.lastName;
                    this.email = data.data.email;
                    this.contactNumber = data.data.contactNumber;
                    this.bookingStatus = !data.data.cannotBook;
                } else {
                    console.error(data.message);
                }
            } catch (err) {
                console.error(err);
            }
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
    <div class="content" style="margin-top: 80px">
        <div class="container-fluid">
            <div class="row align-items-stretch">
                <div class="col-12">
                    <div class="h-100 content">
                        <div class="text-center">
                            <router-link to="/">
                                <img src="../assets/SSSlogo.png" class="img mx-auto image-style" />
                            </router-link>
                        </div>

                        <h1 class="h2 text-center">Personal Information</h1>
                        <div class="form-information">
                            <div class="d-flex">
                                <div class="row flex-grow-1">
                                    <div>
                                        <div class="align-items">
                                            <div id="personal-details">
                                                <div class="d-flex flex-grow-1 justify-content-between pb-2">
                                                    Legal Name
                                                    <div>
                                                        <div id="edit">
                                                            <button class="btn btn-link p-0"
                                                                @click="editName = !editName">
                                                                Edit
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div v-if="!editName">
                                                <div v-if=firstName && lastName class="d-flex border rounded p-2">
                                                    {{ firstName }} {{ lastName }}
                                                </div>
                                            </div>
                                            <div v-else>
                                                <form>
                                                    <div class="form-floating firstName mb-2">
                                                        <input type="text" id="firstName" class="form-control"
                                                            :value="firstName" />
                                                        <label for="firstName">First Name</label>
                                                    </div>

                                                    <div class="form-floating lastName">
                                                        <input type="text" id="lastname" class="form-control"
                                                            :value="lastName" />
                                                        <label for="lastname">Last Name</label>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr class="hr-block" />
                            <div class="d-flex">
                                <div class="row flex-grow-1">
                                    <div>
                                        <div class="align-items">
                                            <div id="personal-details">
                                                <div class="d-flex flex-grow-1 justify-content-between pb-2">
                                                    Email
                                                    <div>
                                                        <div id="edit">
                                                            <button class="btn btn-link p-0"
                                                                @click="editEmail = !editEmail">
                                                                Edit
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div v-if="!editEmail">
                                            <div v-if="email" class="d-flex border rounded p-2">
                                                {{ email }}
                                            </div>
                                        </div>
                                        <div v-else>
                                            <form class="form-floating">
                                                <input type="text" id="name" class="form-control" :value="email" />
                                                <label for="name">Email</label>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <hr class="hr-block" />

                            <div class="d-flex">
                                <div class="row flex-grow-1">
                                    <div>
                                        <div class="align-items">
                                            <div id="personal-details">
                                                <div class="d-flex flex-grow-1 justify-content-between pb-2">
                                                    Contact Number
                                                    <div>
                                                        <div id="edit">
                                                            <button class="btn btn-link p-0"
                                                                @click="editNumber = !editNumber">
                                                                Edit
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div v-if="!editNumber">
                                            <span v-if="contactNumber" class="d-flex border rounded p-2">
                                                {{ contactNumber }}
                                            </span>
                                        </div>
                                        <div v-else>
                                            <form class="form-floating">
                                                <input type="text" id="name" class="form-control"
                                                    :value="contactNumber" />
                                                <label for="name">Contact Number</label>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <hr class="hr-block" />
                            <div class="form-floating mx-auto mb-5 col-6">
                                <div id="personal-details">Booking Status</div>
                                <span>{{ bookingStatus }}</span>
                            </div>
                            <div class="button-flex d-flex text-center">

                                <button type="submit" class="w-100 btn btn-outline-success text-uppercase fw-boldb mb-3">
                                    Save
                                </button>

                                <button type="button" class="btn btn-outline-dark button-style">
                                    <router-link to="/profilePassword">
                                        <i class="fas fa-arrow-left"></i>
                                    </router-link>
                                    Change Password
                                </button>
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
    margin-left: 25%;
    margin-right: 25%;
    padding: 16px 30px;
}

@media screen and (max-width: 768px) {
    .form-information {
        margin: 0;
    }
}
.button-flex{
    flex-direction:column;
}
</style>