<template>
  <div id="app">
    <navigation-header></navigation-header>
    <div style="padding-top:10px;">
      <router-view/>
    </div>
  </div>
</template>

<script>
import NavigationHeader from '@/components/Common/NavigationHeader'
export default {
  name: 'App',
  components: {
    NavigationHeader
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background: #e6ecf0;
  height: 100%;
  display: block;
}
* { margin: 0 !important;}

ul {
    list-style: none;
    padding:0 0 10px 0;
  }
  li {
    padding:20px;
    border-bottom: 1px solid #ddd;
    cursor: pointer;
  }
  li:hover{background: #f5f8fa}
  input { border: 1px solid #1DA1F2; border-radius: 5px;padding:10px;}
  input:focus {outline:none;}
  button { border:none; background: #1DA1F2; border-radius:5px; padding:8px 20px 8px 20px;color:#fff}

</style>
