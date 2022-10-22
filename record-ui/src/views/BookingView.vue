
<template>

  <div class="container">
    <div class="row" id="sectionheader">
      <h1>Singapore Zoo</h1>
      <h3>Each physical pass equates to 2 entries to the attraction, you are entitled to condition</h3>
    </div>
    <div class="bookingdetails">
      <div class="location">
        <h4>Location</h4>
        <div>{{locationSelected}}</div>
        <i class="fas fa-angle-down"></i>
      </div>

      <div class="Date">
        <h4>Date</h4>
        <div data-placeholder="data-placeholder">

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default{
  data(){
    return{
      locationSelected: "",
    }
  },
  methods:{

  },

}
</script>

<style>
#sectionheader{
  position: absolute;
}

div:empty::before{
  color:red;
}

div[data-placeholder]:not([data-placeholder=""]):empty::before{
  content: attr(data-placeholder);
}

</style>