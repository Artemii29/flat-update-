<template>
<div class="all__announcements">
  <div v-for="announcement in announcements" >
    <AnnouncementCard :announcement="announcement"></AnnouncementCard>
  </div>
  </div>

</template>
<script setup lang="ts">
import { onMounted, ref} from "vue";
import axios from "axios";
const announcements = ref<{ title: string, description: string, flatPhotos:{data:string} }[]>([]);
const token = ref(localStorage.getItem('token') || ''); // Проверка наличия значения в localStorage
import AnnouncementCard from "@/components/AnnouncementCard.vue";
onMounted(async () => {
  console.log(token.value)
  try {
    const response = await axios.get('http://localhost:8080/api/v1/announcements', {
          headers: {
            Authorization: 'Bearer ' + token.value
          }
        }
    )
    announcements.value = response.data.map((announcement: any) => ({
      ...announcement,
      // Convert each flat photo's data (if not already in Base64) to a viewable format
      flatPhotos: announcement.flatPhotos.map((photo: any) => ({
        ...photo,
        data: `data:image/jpeg;base64,${photo.data}`
      }))
    }));
  }
catch (error){
  console.log(error)
}
  }
)
</script>
<style scoped>
.all__announcements{
  display: flex;
  flex-wrap: wrap;
}
</style>