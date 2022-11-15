<template>
  <div class="container-fluid p-0 h-100" :style="{ backgroundImage: `url(${currentBackground})` }">
    <div
      class="pt-3 px-5"
    >
      <div>
        <h1 class="text-center">{{ title }}</h1>
      </div>

      <table class="table table-bordered table-hover">
        <thead>
          <tr class="table-active">
            <th scope="col">Loan ID</th>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Start Date</th>
            <th scope="col">Attraction Name</th>
            <th scope="col">Pass ID</th>
            <th scope="col">Previous Borrower</th>
            <th scope="col">Previous Borrower Contact</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th scope="col">{{ loans[0] }}</th>
            <td scope="col">{{ loans[1] }}</td>
            <td scope="col">{{ loans[2] }}</td>
            <td scope="col">{{ loans[3] }}</td>
            <td scope="col">{{ loans[4] }}</td>
            <td scope="col">{{ loans[5] }}</td>
            <td scope="col">{{ loans[6] }}</td>
            <td scope="col">{{ loans[7] }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { defineComponent } from "vue";

//pass the output from the api to the respective table rows
interface Data {
  title: string;
  checkLoaner: boolean;
  loanID: number;
  currentBackground: string;
  loans: Loan[];
  staffId: number;
  role: string;
}

interface LoginData {
  staffId: number;
  role: string;
}

interface Loan {
  loanId: number,
  staffName: string,
  staffEmail: string,
  visitDate: string,
  attractionName: string,
  passId: string,
  prevBorrowerName: string,
  prevBorrowerContact: string
}

export default defineComponent({
  data(): Data {
    return {
      title: "Your booking is successful!",
      checkLoaner: false,
      loans: [],
      loanID: parseInt(this.loanID),
      staffId: 0,
      role: "",
      currentBackground:
        "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935",
    };
    //find outer element with border of everything, then define the toast as the child of that element then set the css of position of toast
    // as absolute bottom and right define based on how much i want it to space
  },
  async created() {
    this.checkLogin();
    try {
      const loanDetails = await axios.get(
        import.meta.env.VITE_API_URL + "api/loan/" + this.$route.params.loanID
      );
      const detail = loanDetails.data.data[0];

      const loanId = detail.loanId;
      const staffName = detail.staffName;
      const staffEmail = detail.staffEmail;
      const visitDate = detail.visitDate;
      const attractionName = detail.attractionName;
      const passId = detail.passId;
      const prevBorrowerName = detail.prevBorrowerName;
      const prevBorrowerContact = detail.prevBorrowerContact;
      this.loans = [
        loanId,
        staffName,
        staffEmail,
        visitDate,
        attractionName,
        passId,
        prevBorrowerName,
        prevBorrowerContact,
      ];
    } catch (err: any) {
      if (err.response.status == 401) {
        this.$router.push({ name: "Login" });
      }
    }
  },
  methods: {
    showPreviousLoaner() {
      this.checkLoaner = this.checkLoaner ? false : true;
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
          role: this.role,
        };
      }
    },
  },
  props: ["loanID"],
});
</script>

<style>

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