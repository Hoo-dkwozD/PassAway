<script lang="ts">
import { defineComponent } from "vue";
interface SignupData {
    email: String | null,
    emailInvalid: Boolean | null
    emailCaptured: Boolean | null
}

export default defineComponent({
    name: 'Signup',
    data() {
        return {
            email: null,
            emailCaptured: null,
            emailInvalid: false,
            useremail: '',
            userpass: '',
            name: '',
            age: '',
            gender: 'Gender',
            emailText: ""
        }
    },
    methods: {
    },
    computed: {
        checkEmail() {
            if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.useremail)) {
                this.emailInvalid = true

            } else {
                this.emailInvalid = false
            }
        },
    }
})
</script>

<template>
    <div class="content" style="margin-top: 80px;">
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

                            <div class="form-floating mx-auto mb-3 col-6">
                                <input type="email" th:field class="form-control" id="email" v-model="useremail">
                                <label for="email">Email address</label>
                                <div id="emailHelpBlock" class="form-text text-danger" v-if="emailInvalid">
                                    Email address is invalid! Please key in a valid email address
                                </div>

                                <div v-if="emailCaptured" id="emailNotification">
                                    <div class="alert alert-success" role="alert">
                                        <strong>Success!</strong> You have successfully registered an account! A
                                        registration email has been sent to {{ useremail }}. Please check your email to
                                        verify your account.
                                    </div>
                                </div>

                                <div v-else id="emailNotification">
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