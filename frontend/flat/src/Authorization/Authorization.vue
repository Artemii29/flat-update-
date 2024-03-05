<template>
  <div>
  <h1>Вход</h1>
  <label for="username">Username:</label>
  <input type="text" id="username" v-model="username" />
  <label for="password">Password:</label>
  <input type="password" id="password" v-model="password" />
  <button @click="login">Вход</button>
  </div>
</template>
<script>

import { ref } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const username = ref('');
    const password = ref('');


    const login = async () => {
      try {
        const authRequest = { username: username.value, password: password.value};
        const response = await axios.post('http://localhost:8080/api/v1/auth', authRequest);

        if (response.data.token) {
          localStorage.setItem('token', response.data.token);
          console.log('Авторизация успешна!');
          console.log(response.data.token);
        } else {
          throw new Error('Ошибка авторизации');
        }
      } catch (error) {
        console.error('Ошибка авторизации:', error.message);
      }
    };

    return { username, password, login };
  }
};
</script>


<style scoped>
div{
  background-color: #2c3e50;
  border-radius: 10px;
}
button{
  margin-left: 15%;
  width: 10%;
  border-radius: 20px;
  background-color: aqua;
  height: 40px;
}
input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 5px;
  border: none;
}
</style>