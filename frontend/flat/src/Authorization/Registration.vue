<template>
  <div class="container">
    <h1>Registration</h1>
    <label for="username">Username:</label>
    <input type="text" id="username" v-model="name"/>
    <label for="password">Password:</label>
    <input type="password" id="password" v-model="password"/>
    <label for="email">Email:</label>
    <input type="text" id="password" v-model="email" />
    <label for="phone_number">Phone number:</label>
    <input type="text" id="phone_number" v-model="phone_number" />
    <button @click="register">Register</button>
    <div class="have_accounted">
      <h2>Уже есть аккаунт?</h2>
      <RouterLink to="/authorization">Авторизуйтесь</RouterLink>
    </div>
  </div>
</template>
<script>
import {ref} from 'vue';
import axios from 'axios';

export default {
  setup() {
    const name = ref('');
    const username = ref('');
    const password = ref('');
    const email = ref('');
    const phone_number = ref('');
    const register = async () => {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/register', {
          name: name.value,
          password: password.value,
          confirmPassword:password.value,
          email:email.value,
          phone_number:phone_number.value
        });

// Обработка успешной регистрации
      } catch (error) {
// Обработка ошибки регистрации
      }
    };


    return {
      name,
      username,
      password,
      phone_number,
      email,
      register
    };
  }
};
</script>
<style scoped>

.container {
  background-color: #2c3e50;
  padding: 20px;
  border-radius: 10px;
  color: white;
}

h1 {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 10px;
}

input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 5px;
  border: none;
}

button {
  width: 100%;
  background-color: aqua;
  height: 40px;
  border-radius: 20px;
  border: none;
  color: white;
  font-weight: bold;
  cursor: pointer;
}

button:hover {
  background-color: #00bfff;
}
.have_accounted{
  display: flex;
}
a{
  padding-left: 400px;

}
</style>