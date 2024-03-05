<template>
  <div class="announcements">
    <div class="announcement-card">
      <h3>{{ announcement.title }}</h3>
      <p>{{ announcement.description }}</p>
      <p>{{ announcement.rooms }}</p>

      <div v-for="(photo, index) in announcement.flatPhotos" :key="index">
        <img :src="(photo.data)" alt="" class="flat__photo">
      </div>

      <button class="look__announcements">Посмотреть</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps({
  announcement: {
    type: Object as () => { title: string, description: string, rooms: string, flatPhotos: { fileName: string }[] },
    required: true
  }
});
</script>
<style>
.flat__photo{
  width: 50%;
  height: 40%;

}
.announcement-card {
  border: 1px solid #ccc;
  width: 400px;
  height: 300px;
  margin-bottom: 10px;
  background-color: #f2f2f2;
  border-radius: 20px;
  text-align: center;
  position: relative;
}

.look__announcements {
  border-radius: 20px;
  background-color: aqua;
  color: white;
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
  text-align: center;
}

h3 {
  font-size: 24px;
}

p {
  font-size: 16px;
  margin-top: 50px;
}
</style>