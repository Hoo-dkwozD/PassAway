<template>
    <Navbar></Navbar>
    <div class="content" style = "margin-top: 80px;">
      <div class="container-fluid">
        <div class="row align-items-stretch no-gutters contact-wrap">
          <div class="col-md-12">
            <div class="form h-100">
              <router-link to="/">
                <img src="../assets/fflogo.png" class="img mx auto" style="width: 30%;">
              </router-link>
                <h1 class="display-5 mb-3 section-titles">Log In</h1>
                <div class="form-floating mx-auto mb-3 col-6">
                  <input type="email" th:field="*{username}" class="form-control" id="email" placeholder="name@example.com">
                  <label for="email">Email address</label>
                  <div id="emailHelpBlock" class="form-text text-danger" v-if="emailInvalid">
                      {{emailMsg}}
                  </div>
                </div>
                <div class="form-floating mx-auto mb-3 col-6">
                  <input type="password" th:field="*{password}" class="form-control" id="password" placeholder="Password" @keyup.enter="handlesignin()">
                  <label for="password">Password</label>
                  <div id="passwordHelpBlock" class="form-text text-danger" v-if="passwordInvalid">
                      {{passwordMsg}}
                  </div>
                </div>
                
                <button class="btn btn-outline-success col-5 btn-login text-uppercase fw-bold signin-btn m-1" @click="handlesignin()">Sign in</button>
              
              <div class="d-grid" style="align-items: center;"> 
                <router-link to="/signupinit">
                  <p style = "margin-top: 20px; text-decoration: underline; color: blue;">
                    Don't Have An Account? Sign Up
                  </p>
                </router-link>
              </div>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent } from "vue";
  import Navbar from '../components/Navbar.vue'
  export default defineComponent({
    name: 'Login',
    data() {
      return {
        emailInvalid: false,
        passwordInvalid: false,
        useremail: '',
        userpass: '',
        emailMsg: "",
        passwordMsg: "",
      }
    },
    components:{
      Navbar
    },
    methods:{
      
    }
  })
  </script>