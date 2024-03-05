<template>
  <div>
    <h2>Create Announcement</h2>
    <form @submit="handleSubmit">
      <label>
        Title:
        <input type="text" v-model="title"/>
      </label>
      <br/>
      <label>
        Description:
        <textarea v-model="description"></textarea>
      </label>
      <br/>
      <input type="file" ref="file"/>
      <button type="submit" class="send_form">Submit</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import axios from 'axios';
const files = ref([]);
const file = ref('');
const title = ref('');
const description = ref('');
const token = ref(localStorage.getItem('token') || ''); // Проверка наличия значения в localStorage
//как во vue добавить файл в формдата
const handleSubmit = async (e: Event) => {
  e.preventDefault();
  // console.log(file.value.files)

  const FlatAnnouncement = {
    title: title.value,
    description: description.value
  }
  const announcementForm = new FormData();
  announcementForm.append('announcementData', JSON.stringify(FlatAnnouncement));
  for (const fil of file.value.files) {
    announcementForm.append('announcementFiles', fil, fil.name);
  }
  
  try {
    console.log(token.value);
    // Отправка POST запроса на сервер
    const response = await axios.post('http://localhost:8080/api/v1/announcements', announcementForm, {
      headers: {
         "Content-Type": "multipart/form-data",
        "Authorization": "Bearer " + token.value
      },
    },);
    console.log(response.data); // Результат запроса
    // Очистка полей формы
    title.value = '';
    description.value = '';
  } catch (error) {
    console.error(error);
  }

};


</script>
<style scoped>
.send_form {
  border-radius: 20px;
  width: 25%;
  height: 40px;
  background-color: aqua;
  color: #f2f2f2;
  font-weight: bold;
  cursor: pointer;
}

.send_form:hover {
  background-color: #00bfff;
}
</style>