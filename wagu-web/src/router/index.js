import Vue from 'vue'
import Router from 'vue-router'
import Timeline from '@/components/Timeline/Timeline'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'TimeLine',
      component: Timeline
    }
  ]
})
