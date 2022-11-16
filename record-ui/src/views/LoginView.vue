<template>
  <div class="content">
    <div class="container-fluid">
      <div class="text-center mt-5">
          <router-link to="/">
            <img src="../assets/SSSlogo.png" class="img mx-auto image-style" />
          </router-link>
        </div>
      <div class="row align-items-stretch no-gutters contact-wrap">
        <div class="col-md-12">
          <div class="form h-100">
            <h1 class="display-5 mb-3 section-titles">Log In</h1>
            <div class="form-floating mx-auto mb-3 col-6">
              <input v-model="email" type="email" th:field="*{username}" class="form-control" id="email" placeholder="name@example.com">
              <label for="email">Email address</label>
              <div id="emailHelpBlock" class="form-text text-danger" v-if="emailInvalid">
                {{emailMsg}}
              </div>
            </div>
            <div class="form-floating mx-auto mb-3 col-6">
              <input v-model="password" @keyup.enter="signin()" type="password" th:field="*{password}" class="form-control" id="password" placeholder="Password">
              <label for="password">Password</label>
              <div id="passwordHelpBlock" class="form-text text-danger" v-if="passwordInvalid">
                {{passwordMsg}}
              </div>
            </div>
            
            <button @click="signin()" class="btn btn-outline-success col-5 btn-login text-uppercase fw-bold signin-btn m-1">Sign in</button>
            
            <div class="d-grid" style="align-items: center;"> 
              <router-link to="/signup" style = "margin-top: 20px; text-decoration: underline; color: blue;">
                Don't Have An Account? Sign Up
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
  import axios from "axios";

  // Typings
  interface LoginViewData {
    emailInvalid: boolean;
    passwordInvalid: boolean;
    email: string | null;
    password: string | null;
    emailMsg: string;
    passwordMsg: string;
  }
  
  export default defineComponent({
    name: 'appLogin',
    data(): LoginViewData {
      return {
        emailInvalid: false,
        passwordInvalid: false,
        email: null,
        password: null,
        emailMsg: "Valid email required",
        passwordMsg: "Valid password required",
      };
    },
    methods:{
      async signin() {
        try {
          const res = await axios.post(
            import.meta.env.VITE_API_URL + "api/auth/signin", 
            {
              email: this.email,
              password: this.password,
            }
          );
          const data = await res.data;

          if (data.code === 200) {
            localStorage.setItem("staffId", data.data.staffId);
            localStorage.setItem("role", data.data.role);
            localStorage.setItem("token", data.data.token);
            this.$router.push({ name: 'home'});
          } else {
            this.emailInvalid = true;
            this.passwordInvalid = true;
          }
        } catch (err) {
          console.error(err);
        }
      }
    }
  });
</script>