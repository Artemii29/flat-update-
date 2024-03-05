import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/registration',
      name: ' Registration',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('@/Authorization/Registration.vue')
    },
    {
      path:'/authorization',
      name:'Authorization',
      component: () => import('@/Authorization/Authorization.vue')

    },
    {
      path: '/createAnnouncemnt',
      name: 'createAnnouncemnt',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('@/views/Announcement.vue')
    },
    {
      path:'/allAnnouncement',
      name:'allAnnouncement',
      component: () => import('@/views/allAnnouncement.vue')
    }

  ]
})

export default router
