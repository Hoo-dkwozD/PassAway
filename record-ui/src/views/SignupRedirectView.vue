<script lang="ts">
import { defineComponent } from "vue";
import axios from 'axios';

interface SignupData {
    firstName: String | null,
    firstNameInvalid: Boolean | null,
    lastName: String | null,
    lastNameInvalid: Boolean | null,
    contactNumber: Number | null,
    contactNumberInvalid: Boolean | null,
    email: String,
    emailInvalid: Boolean | null
    userPassword: String,
    userPasswordInvalid : Boolean | null,
    confirmUserPassword: String,
    confirmUserPasswordInvalid: Boolean | null,
    hasFailedSent: Boolean
}

export default defineComponent({
    name: 'SignupRedirectView',
    data(): SignupData {
        return {
            firstName: null,
            firstNameInvalid: false,

            lastName: null,
            lastNameInvalid: false,

            contactNumber: null,
            contactNumberInvalid: false,

            email: "",
            emailInvalid: false,

            userPassword: "",
            userPasswordInvalid: false,
            
            confirmUserPassword: "",
            confirmUserPasswordInvalid: false,

            hasFailedSent: false
        }
    },
    methods: {
        async checkFields() {
            if(this.email === null){
                this.emailInvalid = true
            }
            else{
                this.emailInvalid = false
            }
            if (this.firstName === null){
                this.firstNameInvalid = true
            }else{
                this.firstNameInvalid = false
            }

            if (this.lastName === null){
                this.lastNameInvalid = true
            }else{
                this.lastNameInvalid = false
            }

            if (this.contactNumber === null){
                this.contactNumberInvalid = true
            }else{
                this.contactNumberInvalid = false
            }

            if(this.userPassword === null){
                this.userPasswordInvalid = true
            }
            else{
                this.userPasswordInvalid = false
            }

            if(this.confirmUserPassword === null){
                this.confirmUserPasswordInvalid = true
            }
            else{
                this.confirmUserPasswordInvalid = false
            }

            if (!this.firstNameInvalid && !this.lastNameInvalid 
                && !this.contactNumberInvalid && !this.emailInvalid 
                && !this.userPasswordInvalid && !this.confirmUserPasswordInvalid){
                //insert java code to register
                const queryParams = new URLSearchParams(window.location.search);
                const registerKey = queryParams.get('key') === null ? '' : queryParams.get('key');

                try {
                    const res = await axios.post(
                        import.meta.env.VITE_API_URL + `api/staff/register/complete`,
                        {
                            email: this.email,
                            firstName: this.firstName,
                            lastName: this.lastName,
                            contactNumber: this.contactNumber,
                            password: this.userPassword,
                            registerKey: registerKey
                        }
                    )
                    const data = await res.data;

                    if (data.code === 201) {
                        await this.signin();
                    }
                } catch (err: any) {
                    if (err.response) {
                        this.hasFailedSent = true;
                    } else {
                        console.error(err.message);
                    }
                }
            }
        },
        async signin() {
            try {
                const res = await axios.post(
                    import.meta.env.VITE_API_URL + "api/auth/signin", 
                    {
                        email: this.email,
                        password: this.userPassword,
                    }
                );
                const data = await res.data;

                if (data.code === 200) {
                    localStorage.setItem("staffId", data.data.staffId);
                    localStorage.setItem("role", data.data.role);
                    this.$router.push({ name: 'home'});
                } 
            } catch (err) {
                console.error(err);
            }
        }
    },
    computed: {
        checkEmail() {
            if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email.toString())) {
                this.emailInvalid = true

            } else {
                this.emailInvalid = false
            }
        },
        checkfirstName() {
            if (this.firstName !== null && this.firstName.length == 0) {
                this.firstNameInvalid = true
            } else {
                this.firstNameInvalid = false
            }
        },
        checklastName() {
            if (this.lastName !== null && this.lastName.length == 0) {
                this.lastNameInvalid = true
            } else {
                this.lastNameInvalid = false
            }
        },
        checkNumber(){
            if (this.contactNumber !== null && this.contactNumber == 0) {
                this.contactNumberInvalid = true
            } else {
                this.contactNumberInvalid = false
            }
        },
        checkPassword() {
            var stringRegexp = new RegExp(/[A-Z]/);
            var stringNumRegexp = new RegExp(/[^0-9a-zA-Z]/);
            var specialCharacterRegexp = new RegExp(/[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/);
                if (this.userPassword.length < 8 || this.userPassword == null) {
                    this.userPasswordInvalid = true
                } else if (!stringRegexp.test(this.userPassword.toString()) || !stringRegexp.test(this.userPassword.toString())) {
                    this.userPasswordInvalid = true
                } else if (!stringNumRegexp.test(this.userPassword.toString())) {
                    this.userPasswordInvalid = true
                } else if (!specialCharacterRegexp.test(this.userPassword.toString())) {
                    this.userPasswordInvalid = true
                } else {
                    this.userPasswordInvalid = false
                }
            },
        checkConfirmPassword() {
            var stringRegexp = new RegExp(/[A-Z]/);
            var stringNumRegexp = new RegExp(/[^0-9a-zA-Z]/);
            var specialCharacterRegexp = new RegExp(/[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/);

                if (this.confirmUserPassword.length < 8 || this.confirmUserPassword == null) {
                    this.confirmUserPasswordInvalid = true
                } else if (!stringRegexp.test(this.confirmUserPassword.toString()) || !stringRegexp.test(this.confirmUserPassword.toString())) {
                    this.confirmUserPasswordInvalid = true
                } else if (!stringNumRegexp.test(this.confirmUserPassword.toString())) {
                    this.confirmUserPasswordInvalid = true
                } else if (!specialCharacterRegexp.test(this.confirmUserPassword.toString())) {
                    this.confirmUserPasswordInvalid = true
                } else {
                    this.confirmUserPasswordInvalid = false
                }
            },
    }
})
</script>

<template>
    <div class="container-fluid h-100">
        <div class="row align-items-stretch no-gutters contact-wrap">
            <div class="col-md-12">
                <div class="h-100">
                    <router-link to="/">
                        <img src="../assets/SSSlogo.png" class="img mx auto" style="width: 10%;">
                    </router-link>
                    <p class="h4 mb-3">
                        Registration Confirmation
                    </p>

                    <div class="form-information">

                        <div class="form-floating mx-auto mb-3 col-6">
                            <input class="form-control" id="email" placeholder="Email" v-model="email"
                                on:blur="checkEmail">
                            <label for="email">Email</label>
                            <div id="emailHelpBlock" class="form-text text-danger" v-if="emailInvalid">
                                Email field cannot be empty
                            </div>
                        </div>

                        <div class="form-floating mx-auto mb-3 col-6">
                            <input type="text" class="form-control" id="firstName" placeholder="First Name"
                                v-model="firstName" on:blur="checkfirstName">
                            <label for="firstName">First Name</label>
                            <div id="firstNameHelpBlock" class="form-text text-danger" v-if="firstNameInvalid">
                                First Name field cannot be left empty.
                            </div>
                        </div>

                        <div class="form-floating mx-auto mb-3 col-6">
                            <input type="text" class="form-control" id="lastName" placeholder="Last Name"
                                v-model="lastName" on:blur="checklastName">
                            <label for="lastName">Last Name</label>
                            <div id="lastNameHelpBlock" class="form-text text-danger" v-if="lastNameInvalid">
                                Last Name field cannot be left empty.
                            </div>
                        </div>

                        <div class="form-floating mx-auto mb-3 col-6">
                            <input type="text" class="form-control" id="contactNumber" placeholder="Contact Number" v-model="contactNumber"
                            on:blur="checkNumber">
                            <label for="contactNumber">Contact Number</label>
                            <div id="contactNumberHelpBlock" class="form-text text-danger" v-if="contactNumberInvalid">
                                Contact Number is invalid! Please key in a valid contact number
                            </div>
                        </div>

                        <div class="form-floating mx-auto mb-3 col-6">
                            <input type="password" th:field="*{password}" class="form-control" id="password"
                                placeholder="Password" on:blur="checkPassword" v-model="userPassword">
                            <label for="password">Password</label>
                            <div id="passwordHelpBlock" class="form-text text-danger" v-if="userPasswordInvalid">
                                Your password must be 8 characters or longer, contain letters and numbers, and must
                                not contain spaces, special characters, or emoji.
                            </div>
                        </div>

                        <div class="form-floating mx-auto mb-3 col-6">
                            <input type="password" th:field="*{password}" class="form-control" id="confirmpassword"
                                placeholder="Confirm Password" on:blur="checkConfirmPassword" v-model="confirmUserPassword">
                            <label for="confirmpassword">Confirm Password</label>
                            <div id="confirmPasswordHelpBlock" class="form-text text-danger" v-if="confirmUserPasswordInvalid">
                                Your password must be 8 characters or longer, contain letters and numbers, and must
                                not contain spaces, special characters, or emoji.
                            </div>
                        </div>

                        <button class="btn btn-outline-success btn-login text-uppercase fw-bold signin-btn m-1 col-6"
                            @click="checkFields()">Sign up</button>

                    </div>
                </div>
            </div>
        </div>
    </div>

</template>


<style scoped>
.form-information {
    margin-top: 5px;
    margin-bottom: 30px;
    border: 2px solid black;
    border-radius: 10px;
    margin-left: 25%;
    margin-right: 25%;
    padding: 16px 30px;
}
</style>