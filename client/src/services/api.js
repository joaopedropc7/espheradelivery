import axios from "axios";

const api = axios.create({
    baseURL: "http://34.95.213.107:9999/"
})

export default api

export const urlApi = "http://34.95.213.107:9999/"

