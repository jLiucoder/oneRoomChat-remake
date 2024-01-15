import axios from "axios"
import {getTokenKey, removeTokenKey} from "./token";
import router from "../router";


const BASE_URL = "http://localhost:8080"
const TIME_OUT = 5000

const request = axios.create({
    baseURL: BASE_URL,
    timeout: TIME_OUT,
    headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
    },
    validateStatus: (_) => {
        return true;
    },
})

// we can use this to add data before send this out, else return error
request.interceptors.request.use(
    (config) => {
        const token = getTokenKey();
        if (token) {
            config.headers.Authorization = `${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    },
);

// before response to the client,intercept it and process the response
request.interceptors.response.use(
    (response) => {
        if(response.data.msg === "INVALID_TOKEN" || response.data.msg==="NOT_LOGGED_IN"){
            removeTokenKey();
            localStorage.removeItem("userId")
            router.navigate("/login").then(() => window.location.reload());
        }
        return response.data;
    },
    (error) => {
        console.dir(error);
        if (error.response.status === 401) {
            removeTokenKey();
            router.navigate("/login").then(() => window.location.reload());
        }
        return Promise.reject(error);
    },
);



export {request}
