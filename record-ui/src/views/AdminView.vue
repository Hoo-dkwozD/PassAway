
<template>
  <Navbar></Navbar>
  <div class="container align-middle">
    <h4>List of administrators</h4>


  <!-- Button trigger modal -->
  <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    Add new Admin
  </button>

  <!-- Modal -->
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h4>List of users</h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          
          <table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">Staff ID</th>
      <th scope="col">Email</th>
      <th scope="col">Name</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="staff in allStaff">
      <th scope="row" v-if="staff.role != 'ADMINISTRATOR'">{{staff.staffId}}</th>
      <td v-if="staff.role != 'ADMINISTRATOR'">{{staff.email}}</td>
      <td v-if="staff.role != 'ADMINISTRATOR'">{{staff.firstName}} {{staff.lastName}}</td>
      <td v-if="staff.role != 'ADMINISTRATOR'"><button type="button" class="btn btn-primary" :value="staff.staffId" @click="addAdmin">Add</button></td>
    </tr>
   
  </tbody>
</table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">Staff ID</th>
      <th scope="col">Email</th>
      <th scope="col">Name</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="admin in adminData">
      <th scope="row">{{admin.staffId}}</th>
      <td>{{admin.email}}</td>
      <td>{{admin.firstName}} {{admin.lastName}}</td>
      <td><button type="button" class="btn btn-danger" :value="admin.staffId" @click="removeAdmin">Remove</button></td>
    </tr>
    
  </tbody>
</table>
</div>

<div id="myModal" class="modal fade">
	<div class="modal-dialog modal-confirm">
		<div class="modal-content">
			<div class="modal-header justify-content-center">
				<div class="icon-box">
					<i class="material-icons">&#xE876;</i>
				</div>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body text-center">
				<h4>Great!</h4>	
				<p>Your account has been created successfully.</p>
				<button class="btn btn-success" data-dismiss="modal"><span>Start Exploring</span> <i class="material-icons">&#xE5C8;</i></button>
			</div>
		</div>
	</div>
</div> 
  </template>
  
  <script>
  import axios from "axios";
  import Navbar from '../components/Navbar.vue';
  export default{
    components:{
      Navbar
    },
    data(){
      return{
        adminData : "",
        allStaff : "",
        URL: "http://localhost:8000/"
      }
    },
    methods:{
      async removeAdmin(){
        var buttonVal = event.target.value;

        // var submit = await axios.put("http://localhost:8080/api/staff/removeAdmin/" + test);

        axios({
            url: this.URL + "api/staff/removeAdmin/" + buttonVal,
            method: 'put',
        })
       .then(res => {
        if(res.data.code == 200){
          alert("Successfully removed admin user!");
          location.reload();
        }
       })
       .catch (err => console.error(err))
        
      },

      async addAdmin(){
        var buttonValue = event.target.value;

        // var submit = await axios.put("http://localhost:8080/api/staff/removeAdmin/" + test);

        axios({
            url: this.URL + "api/staff/addAdmin/" + buttonValue,
            method: 'put',
        })
       .then(res => {
        if(res.data.code == 200){
          alert("Successfully added admin user!");
          location.reload();
        }
        console.log(res.data)
       })
       .catch (err => console.error(err))
        
      }
      
  
    },
    async created(){
      const dataAdmin = await axios.get(this.URL + "api/staff/admin");
      this.adminData = dataAdmin.data.data;

      const dataAllStaff = await axios.get(this.URL + "api/staffs");
      this.allStaff = dataAllStaff.data.data;
      console.log(dataAllStaff);
    }
  }
  </script>
  
  <style>
  
  </style>