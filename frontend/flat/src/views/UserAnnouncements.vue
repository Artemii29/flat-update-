<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
const favorites = ref([]);
const token =   localStorage.getItem('token')
onMounted(() => {

  const axiosGet = axios.get("/api/v1/announcements/favorites/${userId}")
})

</script>

<template>
<h1>Мой объявления</h1>
</template>

<style scoped>

</style>