<script lang="ts">
import { defineComponent } from "vue";
import axios from 'axios';

// Typings
interface SignupData {
    canSignup: boolean,
    hasSent: boolean,
    email: string,
    invalidEmail: boolean
}

export default defineComponent({
    name: 'Signup',
    data() {
        return {
            canSignup: false,
            hasSent: false,
            email: '',
            invalidEmail: false
        };
    },
    methods: {
        async checkEmail() {
            if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email)) {
                this.invalidEmail = true
            } else {
                this.invalidEmail = false

                try {
                    const res = await axios.post(
                        import.meta.env.VITE_API_URL + 'api/staff/register',
                        {
                            email: this.email
                        }
                    );
                    if (res.status === 204) {
                        this.hasSent = true;
                        this.canSignup = true;
                    }
                } catch (err: any) {
                    if (err.response) {
                        this.hasSent = true;
                        this.canSignup = false;
                    } else {
                        console.error(err.message);
                    }
                }
            }
        }
    }
});
</script>

<template>
    <div class="content">
        <div class="container-fluid h-100">
            <div class="row align-items-stretch no-gutters contact-wrap">
                <div class="col-md-12">
                    <div class="h-100">
                        <router-link to="/">
                            <img src="../assets/SSSlogo.png" class="img mx auto" style="width: 10%;">
                        </router-link>
                        <p class="h4 mb-3">
                            Registration
                        </p>

                        <div class="mt-5">
                            <div v-if="hasSent && canSignup" id="emailNotification">
                                <div class="alert alert-success" role="alert">
                                    <strong>Success!</strong> You have successfully registered an account! A
                                    registration email has been sent to {{ email }}. Please check your email to
                                    verify your account.
                                </div>
                            </div>
                            <div v-else class="form-floating mx-auto mb-3 col-6">
                                <input type="email" th:field class="form-control" id="email" v-model="email">
                                <label for="email">Email address</label>
                                <div id="emailHelpBlock" class="form-text text-danger" v-if="invalidEmail">
                                    Email address is invalid! Please key in a valid email address
                                </div>

                                <div v-if="hasSent && !canSignup" id="emailNotification">
                                    <div class="alert alert-danger" role="alert">
                                        <strong>Failed!</strong> Email address already exists! Please contact the
                                        General Office Personnel for assistance.
                                    </div>
                                </div>
                                <button
                                    class="btn btn-outline-success btn-login text-uppercase fw-bold signin-btn m-1 col-6"
                                    @click="checkEmail()">Send Registration Link</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>


<style scoped>

.icon-image {
    position: absolute;
    top: 32%;
    margin-right: 5px;
    right: 0;
    width: 20px;
    height: 20px;
}
</style>