<template>
    <div :class="[`group-3-1`, className || ``]">
        <div class="place-1 lato-semi-bold-pickled-bluewood-20px">Date</div>
        <div class="address lato-medium-storm-gray-18px">{{ address }}</div>
  </div>

</template>

<script lang="ts">

import {defineComponent} from "vue";
export default defineComponent({
    props: ["address", "className"]
})
</script>

<style>
.group-3-1 {
  align-items: flex-start;
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-top: 0;
  min-height: 56px;
  width: 136px;
}

.place-1,
.address {
  letter-spacing: 0;
  line-height: 24px;
  white-space: nowrap;
}

.group-3-1.group-4 {
  left: 43px;
  margin-top: unset;
  position: absolute;
  top: 53px;
}
</style>