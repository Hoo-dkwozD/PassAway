
<template>
  <div class="container-fluid" :style="`{ backgroundImage: url(${currentBackground}) }`">
    <div class="vh-100">
      <div>
        <h1 class="text-center">{{ title }}</h1>
      </div>
      <table class="table table-bordered table-hover">
        <thead>
          <tr class="table-light">
            <th scope="col">Loan ID</th>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Start Date</th>
            <th scope="col">Attraction Name</th>
            <th scope="col">Pass ID</th>
            <th scope="col">Previous Borrower</th>
            <th scope="col">Previous Borrower Contact</th>
            <th scope="col">Cancel Booking</th>
            <th scope="col">Report Loss Pass</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="loan in loans.reverse()" :key="loan[0]">
            <th scope="col">{{ loan[0] }}</th>
            <td scope="col">{{ loan[1] }}</td>
            <td scope="col">{{ loan[2] }}</td>
            <td scope="col">{{ loan[3] }}</td>
            <td scope="col">{{ loan[4] }}</td>
            <td scope="col">{{ loan[5] }}</td>
            <td scope="col">{{ loan[6] }}</td>
            <td scope="col">{{ loan[7] }}</td>
            <td>
              <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#staticBackdrop" :disabled="loan[8]"
                @click="loanId = loan[0]">
                Cancel
              </button>
              <!-- modal -->
              <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false"
                tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h3 class="modal-title fs-5" id="staticBackdropLabel">
                        Confirm Cancel Booking?
                      </h3>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary btn-outline-warning" v-on:click="cancel(loanId)">
                        Yes
                      </button>
                      <button type="button" class="btn btn-light btn-outline-danger" data-bs-dismiss="modal">
                        No
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <!-- modal -->
            </td>
            <td>
              <button class="btn btn-danger" @click="loanId = loan[0]" :disabled="loan[8]" data-bs-toggle="modal"
                data-bs-target="#staticBackdrop1">
                Report Loss
              </button>


<!-- modal2 -->
              <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false"
                tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h3 class="modal-title fs-5" id="staticBackdropLabel">
                        Are you sure you want to report loss pass?
                      </h3>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <!-- <div class="modal-body">
                      HR will reach out to you follow-up shortly.
                    </div> -->
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary btn-outline-warning" @click="report(loanId)">
                        Yes
                      </button>
                      <button type="button" class="btn btn-light btn-outline-danger" data-bs-dismiss="modal">
                        No
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <!-- modal -->
            </td>
          </tr>
        </tbody>
      </table>

    </div>
  </div>
</template>
  
<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";
import NavBar from "../components/Navbar.vue";
import { isIntegerKey } from "@vue/shared";
import { load } from "webfontloader";
import ModalComponent from "./ModalComponent.vue";

//pass the output from the api to the respective table rows
interface Data {
  title: string;
  currentBackground: string;
  loans: [];
  staffId: number;
  email: string;
  loanId: number;
}
interface LoginData {
  staffId: number;
  role: string;
}
export default defineComponent({
  components: { ModalComponent },
  data(): Data {
    return {
      loanId: 0,
      title: "All Bookings",
      loans: [],
      staffId: 0,
      email: "",
      showModal: false,
      currentBackground:
        "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935",
    };
  }, 
  async created() {
    this.checkLogin();
    try {
      const staffDetails = await axios.get(
        import.meta.env.VITE_API_URL + "api/staff/" + this.staffId
      );
      this.email = staffDetails.data.data.email;
    } catch (err) {
      if (err.response.status == 401) {
        this.$router.push({ name: "Login" });
      }
    }


try {
      const loanDetails = await axios.get(
        import.meta.env.VITE_API_URL +
        "api/loan/list-by-email?email=" +
        this.email
      );
      for (const index in loanDetails.data.data) {
        const detail = loanDetails.data.data[index];
        const loanId = detail["loanId"];
        const staffName = detail.staffName;
        const staffEmail = detail.staffEmail;
        const visitDate = detail.visitDate;
        const attractionName = detail.attractionName;
        const passId = detail.passId;
        const prevBorrowerName = detail.prevBorrowerName;
        const prevBorrowerContact = detail.prevBorrowerContact;
        const lost = detail.lost;
        this.loans.push([
          loanId,
          staffName,
          staffEmail,
          visitDate,
          attractionName,
          passId,
          prevBorrowerName,
          prevBorrowerContact,
          lost,
        ]);
      }
      console.log(this.loans);
    } catch (err) {
      if (err.response.status == 401) {
        this.$router.push({ name: "Login" });
      }
    }
  },
  methods: {
    test(a) {
      console.log(a);
    },
    async cancel(loanId: any) {
      console.log("cancelling for : " + loanId);
      try {
        const res = await axios.delete(
          import.meta.env.VITE_API_URL + "api/loan/cancel",
          { data: { loanIds: [loanId] } }
        );
        console.log(res);
        this.loanId = 0;
        // this.$router.go(0);
      } catch (err) {
        if (err.response.status == 401) {
          this.$router.push({ name: "Login" });
        }
      }
    },
    checkLogin(): LoginData | undefined {
      const staffIdStr = localStorage.getItem("staffId");
      const role = localStorage.getItem("role");

      if (staffIdStr === null || role === null) {
        this.$router.push({ name: "Login" });
      } else {
        this.staffId = parseInt(staffIdStr);

        return {
          staffId: this.staffId,
          role: role,
        };
      }
    },
    async report(loanId: any) {
      console.log("reporting for : " + loanId);
      try {
        console.log(JSON.stringify([parseInt(loanId)]));
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "api/loan/report-lost",
          { loanIds: [loanId] }
        );
        console.log(res);
        this.loanId = 0;
        // this.$router.go(3);
      } catch (err) {
        if (err.response.status == 401) {
          this.$router.push({ name: "Login" });
        }
      }
    },
  },
});
</script>
  
<style>
.imageDiv {
  width: 100%;
  background-size: cover;
  background-size: 100%;
}

h1 {
  font-size: 60px;
  padding: 40px;
  margin: 20px;
  font-family: "Trebuchet MS", sans-serif;
  color: black;
  font-style: italic;
}

h2 {
  font-size: 50px;
  padding-bottom: 40px;
}

.bookingfont {
  font-size: 20px;
  font-family: "Trebuchet MS", sans-serif;
  padding: 15px;
}

.tableblock {
  margin-right: 400px;
  margin-left: 400px;
  padding-left: 100px;
  padding-right: 100px;
}

.align-right {
  text-align: right;
}

.btn-block,
.btn-block:focus {
  background-color: #d72255;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  color: white;
}

.btn-block:hover {
  background-color: #f37931;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  color: black;
}
</style>