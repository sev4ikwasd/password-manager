import axios from "axios";

const BASE_URL = "http://localhost:8080/api";

const loginInstance = axios.create({
  baseURL: BASE_URL,
  method: "POST",
});

const signupInstance = axios.create({
  baseURL: BASE_URL,
  method: "POST",
})

export const API = {
  login(user) {
    return loginInstance.post("/auth/login", user)
  },

  signup(user) {
    return signupInstance.post("/auth/signup", user)
  },
}