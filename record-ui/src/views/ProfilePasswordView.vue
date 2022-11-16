<script lang="ts">
import axios from 'axios';
import { defineComponent } from 'vue';

import Navbar from '../components/Navbar.vue';

// Typings
interface ProfilePasswordData {
    staffId: Number | null,
    role: String | null,
    editPassword: Boolean,
    oldPassword: String | null,
    newPassword: String | null,
    confirmNewPassword: String | null
}

interface LoginData {
    staffId: Number,
    role: String
}

export default defineComponent({
    name: 'ProfilePassword',
    components:{
        Navbar
    },
    data(): ProfilePasswordData {
        return {
            staffId: null,
            role: null,
            editPassword: false,
            oldPassword: null,
            newPassword: null,
            confirmNewPassword: null,
        }
    },
    created() {
        const loginData: LoginData | undefined= this.checkLogin();

        if (loginData === undefined) {
            this.$router.push('/login');
            return;
        }

        this.staffId = loginData.staffId;
        this.role = loginData.role;
    },
    methods: {
        checkLogin(): LoginData | undefined {
            let staffIdStr = localStorage.getItem("staffId");
            let role = localStorage.getItem("role");

            if (staffIdStr === null || role === null) {
                this.$router.push({ name: 'login' });
            } else {
                let staffId = parseInt(staffIdStr);

                return {
                    staffId: staffId,
                    role: role,
                };
            }
        },
        async changePassword() {
            try {
                const res = await axios.put(
                    import.meta.env.VITE_API_URL + `api/staff/${this.staffId}/password`,
                    {
                        staffId: this.staffId,
                        oldPassword: this.oldPassword,
                        newPassword: this.newPassword,
                        confirmPassword: this.confirmNewPassword,
                    }
                );
                const data = await res.data;

                if (data.code === 200) {
                    this.$router.push({ name: 'Profile' });
                } else if (data.code === 401) {
                    this.$router.push({ name: 'login' });
                } else {
                    console.error(data.message);
                }
            } catch (err) {
                console.error(err);
            }
        }
    }
});

</script>
<template>
    <Navbar></Navbar>
    <div class="content">
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

                            <div class="mx-auto col-6 mb-5">
                                <div class="row">

                                    <form>
                                        <div class="form-floating mb-2">
                                            <input v-model="oldPassword" type="password" id="oldPassword" class="form-control">
                                            <label for="oldPassword">Old Password</label>
                                        </div>

                                        <div class="form-floating mb-2">
                                            <input v-model="newPassword" type="password" id="newPassword" class="form-control">
                                            <label for="newPassword">New Password</label>
                                        </div>

                                        <div class="form-floating">
                                            <input v-model="confirmNewPassword" type="password" id="confirmNewPassword" class="form-control">
                                            <label for="confirmNewPassword">Confirm New Password</label>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="text-center">
                                <button @click="changePassword()" type="submit"
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