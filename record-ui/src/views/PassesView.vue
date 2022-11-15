
<template>
    <div class="container-fluid">

        <div class="mb-3 row">
    <label for="attractions" class="col-sm-2 col-form-label">Attraction Name</label>
    <div class="col-sm-8">
      

        <select name="attractions" id="attractions" class="form-select">
        <option v-for="attraction in this.allAttractions" :value="attraction.attractionId">{{attraction.name}}</option>
        </select>

        <button type="submit" class="btn btn-primary mt-2" @click="showPasses">Show</button>

        <table class="table table-hover">
        <thead>
            <tr>
            <th scope="col">Pass ID</th>
            <th scope="col">is_lost</th>
            <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="pass in allPasses">
            <th scope="row">{{admin.staffId}}</th>
            <td>{{admin.email}}</td>
            <td>{{admin.firstName}} {{admin.lastName}}</td>
            <td><button type="button" class="btn btn-danger" :value="admin.staffId" @click="removeAdmin">Remove</button></td>
            </tr>
            
        </tbody>
        </table>
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
          allAttractions : "",
          allPasses : "",
          URL: "http://localhost:8000/"
        }
      },
      methods:{
        
        async showPasses(){
          var buttonVal = event.target.value;
  
          // var submit = await axios.put("http://localhost:8080/api/staff/removeAdmin/" + test);
  
          axios({
              url: this.URL + "api/attraction/" + buttonVal,
              method: 'get',
          })
         .then(res => {
          if(res.data.code == 200){
            console.log(res.data);
            this.allPasses = res.data.data;
          }
         })
         .catch (err => console.error(err))
          
        },
  
       
        
    
      },
      async created(){
        const attractions = await axios.get(this.URL + "api/attractions");
        this.allAttractions = attractions.data.data;

        console.log(this.allAttractions);
      }
    }
    </script>
    
    <style>
    
    </style>