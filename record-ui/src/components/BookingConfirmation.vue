<template>

  <div>
    <h1 class="text-center">{{ title }}</h1>
  </div>

  <div class="table-responsive tableblock">
    <table class="table-borderless">
      <tbody>
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

  <div class="text-center m-5">
    <button
      type="button"
      class="btn btn-color btn-lg btn-block"
      @click="showPreviousLoaner"
    >
      Check previous borrower
    </button>
  </div>
  <div v-if="checkLoaner">
    <table class="table-borderless tableblock">
      <h2 class="text-center">Previous Loaner Details</h2>
      <tbody>
        <tr>
          <th class="bookingfont fw-bold">Previous Loaner</th>
          <td class="bookingfont align-right">Name</td>
        </tr>
        <tr>
          <th class="bookingfont fw-bold">Due Date</th>
          <td class="bookingfont align-right">Date</td>
        </tr>
        <tr>
          <th class="bookingfont fw-bold">Email</th>
          <td class="bookingfont align-right">test@test.com</td>
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
</template>

<script lang="ts">
//get loan by id
//pass the output from the api to the respective table rows
interface Data {
  title: string;
  checkLoaner: boolean;
  id: number;
}
export default {
  data(): Data {
    return {
      title: "Booking Confirmation",
      checkLoaner: false,
      id: 0,
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
h1 {
  font-size: 75px;
  padding: 40px;
  margin: 20px;
  font-family: "Trebuchet MS", sans-serif;
}

h2 {
  font-size: 50px;
  padding-bottom: 40px;
}

.bookingfont {
  font-size: 24px;
  font-family: "Trebuchet MS", sans-serif;
  padding: 15px;
}

.tableblock {
  margin-right: 100px;
  margin-left: 450px;
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
