<script lang="ts">
import { defineComponent } from "vue";

    export default defineComponent({
        name: 'appSignup',
        data() {
            return {
                emailInvalid: false,
                passwordInvalid: false,
                nameInvalid: false,
                ageInvalid: false,
                genderInvalid: false,
                useremail: '',
                userpass: '',
                name: '',
                age: '',
                gender: 'Gender',
            }
        },
        methods: {
            checkFields() {
                if (this.name.length == 0){
                    this.nameInvalid = true
                }else{
                    this.nameInvalid = false
                }

                if (this.age.length == 0){
                    this.ageInvalid = true
                }else{
                    this.ageInvalid = false
                }

                if (this.gender == 'Gender'){
                    this.genderInvalid = true
                }else{
                    this.genderInvalid = false
                }

                if (!this.genderInvalid && !this.ageInvalid && !this.nameInvalid && !this.emailInvalid && !this.passwordInvalid){
                    //register function
                }
            },
        },
        computed: {
            checkEmail() {
                if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.useremail)) {
                    this.emailInvalid = true
                } else{
                    this.emailInvalid = false
                }
            },

            checkPassword() {
                if (this.userpass.length < 8) {
                    this.passwordInvalid = true
                } else if (!/[A-Z]/.test(this.userpass) || !/[a-z]/.test(this.userpass)) {
                    this.passwordInvalid = true
                } else if (!/[^0-9a-zA-Z]/.test(this.userpass)) {
                    this.passwordInvalid = true
                } else if (!/[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/.test(this.userpass)) {
                    this.passwordInvalid = true
                } else {
                    this.passwordInvalid = false
                }
            },
            checkName() {
                if (this.name.length == 0){
                    this.nameInvalid = true
                }else{
                    this.nameInvalid = false
                }
            },
            checkAge() {
                if (this.age.length == 0){
                    this.ageInvalid = true
                }else{
                    this.ageInvalid = false
                }
            },
            checkGender() {
                if (this.gender == 'Gender'){
                    this.genderInvalid = true
                }else{
                    this.genderInvalid = false
                }
            },
            changeStyle(event){
                console.log("this is event", event)
            }
            
        }
    })
</script>

<template>
    <div class="content" style="margin-top: 80px;">
        <div class="container-fluid">
            <div class="row align-items-stretch no-gutters contact-wrap">
                <div class="col-md-12">
                    <div class="h-100">
                        <router-link to="/">
                            <img src="../assets/fflogo.png" class="img mx auto" style="width: 30%;">
                        </router-link>
                        <p class="h4 mb-3">
                            First, tell us about yourself:
                        </p>
                        <!-- !!! -->

                        <div class="form-floating mx-auto mb-3 col-6">
                            <input class="form-control" id="name" placeholder="Name" v-model="name">
                            <label for="name">Name</label>
                            <div id="nameHelpBlock" class="form-text text-danger" v-if="nameInvalid">
                                Name field cannot be left empty.
                            </div>
                        </div>
                        <div class="form-floating mx-auto mb-3 col-6">
                            <input type="number" class="form-control" id="age" placeholder="Input your age" min="1"
                                max="99" v-model="age">
                            <label for="age">Age</label>
                            <div id="ageHelpBlock" class="form-text text-danger" v-if="ageInvalid">
                                Age field cannot be left empty.
                            </div>
                        </div>

                        <div class="form-floating mx-auto mb-3 col-6">
                            <select class="form-select" id="gender" v-model="gender">
                                <option selected disabled>Gender</option>
                                <option value="female">Female</option>
                                <option value="male">Male</option>
                            </select>
                            <div id="genderHelpBlock" class="form-text text-danger" v-if="genderInvalid">
                                Gender field cannot be left empty.
                            </div>
                        </div>

                        <div class="form-floating mx-auto mb-3 col-6">
                            <input class="form-control" id="familyId" placeholder="Input your family ID (optional)" />
                            <label for="familyId">Family ID (optional)</label>
                            <img src="/info-svgrepo-com.svg" alt="info" class="icon-image" data-bs-delay="0"
                                data-bs-trigger="click" data-bs-toggle="tooltip" data-bs-placement="top" role="button"
                                title="If you are a new user, please leave this field blank. You can retrieve your family referral code on your profile">
                        </div>

                        <!-- <hr> -->
                        <div class="mt-5">
                            <p class="h4 mb-3">
                                Then, register using email or Google:
                            </p>

                            <div class="form-floating mx-auto mb-3 col-6">
                                <input type="email" th:field class="form-control" id="email"
                                    placeholder="name@example.com" v-model="useremail">
                                <label for="email">Email address</label>
                                <div id="emailHelpBlock" class="form-text text-danger" v-if="emailInvalid">
                                    Email address is invalid! Please enter a valid email address.
                                </div>
                            </div>
                            <div class="form-floating mx-auto mb-3 col-6">
                                <input type="password" th:field="*{password}" class="form-control" id="password"
                                    placeholder="Password" v-model="userpass">
                                <label for="password">Password</label>
                                <div id="passwordHelpBlock" class="form-text text-danger" v-if="passwordInvalid">
                                    Your password must be 8 characters or longer, contain letters and numbers, and must
                                    not contain spaces, special characters, or emoji.
                                </div>
                            </div>
                            <button
                                class="btn btn-outline-success btn-login text-uppercase fw-bold signin-btn m-1 col-6"
                                @click="checkFields()">Sign up</button>
                        </div>

                        <div class="d-grid" style="align-items: center;">
                            <router-link to="/login">
                                <p style="margin-top: 20px; text-decoration: underline; color: blue;">
                                    Have an account? Log in here!
                                </p>
                            </router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>


<style scoped>
    familyId {
        position: relative;
    }
    .icon-image {
        position: absolute;
        top: 32%;
        margin-right: 5px;
        right: 0;
        width: 20px;
        height: 20px;
    }

</style>