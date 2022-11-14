<template>
  <div
    class="imageDiv p-5 mb-5 container-fluid"
    :style="{ backgroundImage: `url(${currentBackground})` }"
  >
    <div>
      <h1 class="text-center">{{ title }}</h1>
    </div>

    <div class="table-responsive tableblock col-6">
      <table class="table table-hover border shadow p-3 mb-5 bg-white rounded">
        <tbody>
          <thead class="table-active bookingfont">
            <tr>
              <th class="fw-bold">Loan Details</th>
              <th></th>
            </tr>
          </thead>
          <tr>
            <th class="bookingfont fw-bold">Loan</th>
            <td class="bookingfont align-right">Loan ID</td>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Loan Date</th>
            <td class="bookingfont align-right">Date</td>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Due Date</th>
            <td class="bookingfont align-right">Date</td>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Attraction</th>
            <td class="bookingfont align-right">AttractionName</td>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Number of Passes</th>
            <td class="bookingfont align-right">2</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="table-responsive tableblock col-6">
      <table
        class="table table-hover border shadow p-3 mb-5 bg-white rounded"
        v-if="checkLoaner"
      >
        <tbody>
          <tr class="table-warning bookingfont">
            <th class="fw-bold">Previous Loaner's Details</th>
            <th></th>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Name</th>
            <td class="bookingfont align-right">Bob Aw</td>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Email</th>
            <td class="bookingfont align-right">BobAw@ss.edu.sg</td>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Due Date</th>
            <td class="bookingfont align-right">Date</td>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Attraction</th>
            <td class="bookingfont align-right">AttractionName</td>
          </tr>
          <tr>
            <th class="bookingfont fw-bold">Number of Passes</th>
            <td class="bookingfont align-right">2</td>
          </tr>
        </tbody>
      </table>
    </div>


    <div class="text-center m-5 p-5">
      <button
        type="button"
        class="btn btn-color btn-lg btn-block"
        @click="showPreviousLoaner"
      >
        Check previous borrower
      </button>
    </div>
    <div class="py-5"></div>
  </div>
</template>

<script lang="ts">
//get loan by id
//pass the output from the api to the respective table rows
interface Data {
  title: string;
  checkLoaner: boolean;
  id: number;
  currentBackground: string;
}
export default {
  data(): Data {
    return {
      title: "Your booking is successful!",
      checkLoaner: false,
      id: 0,
      currentBackground:
        "https://img.freepik.com/free-vector/white-desktop-background-modern-minimal-design-vector_53876-140226.jpg?w=1800&t=st=1668366952~exp=1668367552~hmac=a23687ccfe071f6c28017a514a3380e222e62d36894545fc6ff4f9ad24033935",
    };
  },
  created() {
    //test and see what's the issue
    this.id = parseInt(this.$route.params.id);
  },
  methods: {
    showPreviousLoaner() {
      this.checkLoaner = this.checkLoaner ? false : true;
    },
  },
  props: {
    loanDetails: Object,
  },
};
</script>

<style>
imageDiv {
  background-size: 100% 100%;
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

.btn-color,
.btn-color:focus {
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
