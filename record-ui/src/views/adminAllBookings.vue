<template>
  <Navbar></Navbar>
  <div class="container-fluid">
    <div
      class="imageDiv p-5 mb-5 row"
      :style="{ backgroundImage: `url(${currentBackground})` }"
    >
      <div>
        <h1 class="text-center">{{ title }}</h1>
      </div>

      <div class="pb-2 align-right">
        Start Date:
        <input class="me-2 date" id="start" type="date" v-model="startDate" />
        End Date:
        <input class="me-2 date" type="date" v-model="endDate" />
        <button
          class="btn btn-secondary me-2"
          @click="download()"
          id="download"
        >
          Download CSV
        </button>
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
        <tbody v-for="loan in loans">
          <tr>
            <th scope="col">{{ loan[0] }}</th>
            <td scope="col">{{ loan[1] }}</td>
            <td scope="col">{{ loan[2] }}</td>
            <td scope="col">{{ loan[3] }}</td>
            <td scope="col">{{ loan[4] }}</td>
            <td scope="col">{{ loan[5] }}</td>
            <td scope="col">{{ loan[6] }}</td>
            <td scope="col">{{ loan[7] }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import axios from "axios";
import Navbar from "../components/Navbar.vue";

interface Data {
  title: string;
  currentBackground: string;
  loans: any[];
  staffId: number;
  startDate: any;
  endDate: any;
}

interface LoginData {
  staffId: number;
  role: string;
}
export default defineComponent({
  name: "AdminAllBookings",
  components: {
    Navbar,
  },
  data(): Data {
    return {
      title: "All Bookings",
      loans: [],
      staffId: 0,
      startDate: "",
      endDate: "",
      currentBackground:
        "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935",
    };
  },
  async created() {
    this.checkLogin();
    try {
      const loanDetails = await axios.get(
        import.meta.env.VITE_API_URL + "api/loan",
        {
          headers: {'authorization': `${localStorage.getItem("token")}`},
        }
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
        this.loans.push([
          loanId,
          staffName,
          staffEmail,
          visitDate,
          attractionName,
          passId,
          prevBorrowerName,
          prevBorrowerContact,
        ]);
      }
      console.log(this.loans);
    } catch (err: any) {
      if (err.response) {
        if (err.response.status == 401) {
          this.$router.push({ name: "login" }).then(() => this.$router.go(0));
        } else if (err.response.status == 403) {
          this.$router
            .push({ name: "home" })
            .then(() => this.$router.go(0));

          return;
        } else {
          console.error(err.response.data.message);
        }
      } else {
        console.error(err.message);
      }
    }
  },
  methods: {
    async download() {
      const startDateArr = this.startDate.split("-");
      const endDateArr = this.endDate.split("-");
      try {
        const res = await axios.post(
          import.meta.env.VITE_API_URL + "api/analytics/loan/csv",
          {
            fromDay: startDateArr[2],
            fromMonth: startDateArr[1],
            fromYear: startDateArr[0],
            toDay: endDateArr[2],
            toMonth: endDateArr[1],
            toYear: endDateArr[0],
          },
          {
            responseType: "blob",
            headers: {'authorization': `${localStorage.getItem("token")}`},
          }
        );
        console.log(res);
        // const blob = new Blob([res.data], { type: "text/csv" });
        const blob = await res.data;

        // Creating an object for downloading url
        const url = window.URL.createObjectURL(blob);

        // Creating an anchor(a) tag of HTML
        const dl = document.createElement("a");

        // Passing the blob downloading url
        dl.setAttribute("href", url);

        // Setting the anchor tag attribute for downloading
        // and passing the download file name
        dl.setAttribute("download", "download.csv");
        dl.click();
        // Performing a download with click
      } catch (err: any) {
        if (err.response) {
          if (err.response.status == 401) {
            this.$router.push({ name: "login" }).then(() => this.$router.go(0));
          } else if (err.response.status == 403) {
            this.$router
              .push({ name: "home" })
              .then(() => this.$router.go(0));

            return;
          } else {
            console.error(err.response.data.message);
          }
        } else {
          console.error(err.message);
        }
      }
    },
    checkLogin(): LoginData | undefined {
      const staffIdStr = localStorage.getItem("staffId");
      const role = localStorage.getItem("role");
      console.log(role);

      if (staffIdStr === null || role === null) {
        this.$router.push({ name: "login" });
      } else {
        this.staffId = parseInt(staffIdStr);
        if (role !== "ADMINISTRATOR") {
          this.$router.push({ name: "home" });
        }

        return {
          staffId: this.staffId,
          role: role,
        };
      }
    },
  },
});
</script>

<style scoped>
.imageDiv {
  width: 100%;
  background-size: cover;
  padding-top: 300px;
  padding-bottom: 300px;
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
