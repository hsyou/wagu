<template>
  <div class="timeline-left">
    <div class="user-profile">
      <div class="user-profile-top">
      </div>
      <div class="user-profile-bottom">
        <span></span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'timeline-left'
}
</script>

<style scoped>
  .timeline-left {
    display: inline-block;
    width:290px;
    vertical-align: top;
  }
  .user-profile {
    height:220px;
    background: #fff;
  }
  .user-profile-top {
    height:95px;
    background: #1DA1F2;
  }
</style>
