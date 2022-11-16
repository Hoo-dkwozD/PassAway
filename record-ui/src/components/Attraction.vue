<!-- eslint-disable prettier/prettier -->
<template>
  <div class="container-fluid">
    <div id="sectionheader"
      :style="{'background-image':'url(https://www.planetware.com/photos-large/SIN/singapore-marina-bay-sands.jpg)'}"
    ><div class="row">
      <div class="col-10">
        <div  class="title">Attractions</div>
      </div>
      <div class="col-2">
         <router-link :to="`/admin/attraction/create`" >
        <button class="btn btn-primary">Create Attraction</button>
         </router-link>
      </div>
      
    </div>
     

     
     <div  v-for="attract, idx in attractions" :key='idx'  class="bookingdetails container-fluid">
        <div class="row">
             <div class="row ">
              <div class="col-8">

                <h3 id="attract-title">{{attract.name}}</h3>
            <p>{{attract.description}}</p>
              </div>
            <div class="col-2 ">
              <router-link :to="`/admin/attraction/${attract.attractionId}/edit`" >
               <button
              
              class="btn btn-info  "
            >
              Edit 
            </button>
             </router-link>
            </div>
            <div class="col-2 ">
               <router-link :to="`/admin/attraction/${attract.attractionId}`" >
              <button
              
              class="btn btn-info" 
            >
              View 
            </button>
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
interface Attraction {
  attractionId: number,
  name: string,
  description: string,
  passType: string,
  replacementFee: number,
  numAccompanyingGuests: number,
  maxPassesPerLoan: number,
  maxLoansPerMonth: number,
  cannotBook: boolean,
  address: string,
  membershipId: string,
  expiryDate: string,
  barcodeImage: string,
  backgroundImage: string,
  benefits: string,
  termsConditions: string
}

interface AttractionData {
  attractions: Attraction[],
  staffId: number | null,
  role: string | null
}

interface LoginData {
  staffId: number,
  role: string
}

export default defineComponent({
  data(): AttractionData {
    return {
      attractions: [],
      staffId: null,
      role: null
    };
  },
  async created() {
    const loginData = this.checkLogin();

    if (loginData !== undefined) {
      this.staffId = loginData.staffId;
      this.role = loginData.role;
    } else {
      this.$router.push({ name: "login" }).then(() => this.$router.go(0));
      return;
    }

    if (this.role !== 'ADMINISTRATOR') {
      this.$router.push({ name: "home" }).then(() => this.$router.go(0));
      return;
    }

    const res = await axios.get(
      import.meta.env.VITE_API_URL + `api/attractions`
    );
    this.attractions = await res.data.data;
  },
  methods: {
    checkLogin(): LoginData | undefined {
      const staffIdStr = localStorage.getItem("staffId");
      const role = localStorage.getItem("role");

      if (staffIdStr === null || role === null) {
        this.$router.push({ name: "login" }).then(() => this.$router.go(0));
      } else {
        const staffId = parseInt(staffIdStr);

        return {
          staffId: staffId,
          role: role,
        };
      }
    },
  }
});
</script>

<style>
#sectionheader {
  width: 100%;
  background-size: cover;
  padding-top: 30px;
  padding-bottom: 300px;
  background-size: 100%;
}

.main {
  margin-bottom: 10px;
}

#attract-title{
    margin-left:10px ;
    color : blueviolet ;
    

}
.title {
  color: rgb(241, 241, 241);
  font-family: "Trebuchet MS", sans-serif;
  font-weight: bold;
  font-size: 50px;
  letter-spacing: 0;
  /* margin-bottom: 10px;
  padding-bottom: 10px; */
  /* line-height: 1.4; */
  margin-left: 100px;
}

.titledescription {
  color: white;
  font-family: "Trebuchet MS", sans-serif;
  font-weight: 400;
  letter-spacing: 0;
  line-height: 1.6;
  margin-left: 100px;
  padding-left: 50px;
  min-height: 54px;
  width: 1500px;
}

.calendarStyle {
  position: absolute;
  margin-top: 25px;
}
.bookingdetails {
  /* align-items: center; */
  /* display: flex; */
  margin-left: 150px;
  margin-top:20px ;
  border-radius: 30px;
  box-shadow: 0px 10px 10px;
  /* padding: 5px; */
  /* padding-top: 15px; */
  padding-left:5px;
  background-color: rgba(255, 248, 255, 0.678);
  border: 1px none;
  width: 1000px;
  height: 90px;
}

#group-location {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 450px;
  margin-left: 20px;
  margin-right: 10px;
}

#group-calendar {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 300px;
  padding-left: 10px;
  margin-right: 30px;
}

#group-date {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 250px;
  padding-left: 5px;
  margin-right: 40px;
}

#group-submit {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 50px;
  min-height: 56px;
  width: 200px;
  padding-left: 20px;
  margin-right: 20px;
}

#location-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

#calendar-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

#date-details {
  left: 0;
  letter-spacing: 0;
  line-height: 24px;
  top: 0;
  white-space: nowrap;
  padding-left: 20px;
}

.shadow {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}



.btn-submit {
  /* background-color: #31ccf3; */
  /* letter-spacing: 0;
  line-height: 24px;
  color: black;
  /* box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); */
  padding-left: 20px;
} */

.btn-submit:hover {
  background-color: #d72255;
  color: white;
}
</style>