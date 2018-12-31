<template>
  <div class="timeline-index">
    <TimelineLeft></TimelineLeft>
    <TimelineCenter></TimelineCenter>
    <TimelineRight></TimelineRight>
  </div>
</template>

<script>
import TimelineLeft from './TimelineLeft'
import TimelineCenter from './TimelineCenter'
import TimelineRight from './TimelineRight'
export default {
  name: 'timeline-index',
  components: {
    TimelineLeft,
    TimelineCenter,
    TimelineRight
  }
}
</script>

<style scoped>
  .timeline-index {
    width:1200px;
    overflow: hidden;
    text-align: center;
    display: inline-block;
  }
</style>
