import {request} from "../util/request"

export function loginAPI(data) {
    return request({
        url: "/auth/login",
        method: "POST",
        data: data,
    });
}

export function getUserAPI(id) {
    return request({
        url: `/users/${id}`,
        method: "GET",
    });
}

export function signUpAPI(data) {
    return request({
        url: "/auth/signup",
        method: "POST",
        data: data,
    });
}

export function getTopUsersAPI() {
    return request({
        url: "/users/top",
        method: "GET",
    });
}

export function getChatsAPI(data) {
    return request({
        url: "/chats",
        method: "GET",
        data: data,
    });
}

export function searchChatAPI(data){
    console.log(data);
    return request({
        url: `/chats/search?word=${encodeURIComponent(data)}`,
        method: "GET",
    });
}

export function updateProfileAPI(data) {
    return request({
        url: "/users",
        method: "POST",
        data: data,
    });
}

export function getPreSignedUrlAPI(file) {

    return request({
        url: `/storage/generate-presigned-url?filename=${file.name}&contentType=${file.type}&userId=${localStorage.getItem("userId")}`,
        method: "GET"
    });
}

export function saveProfilePictureAPI(data){
    return request({
        url: "/users/profile-picture",
        method: "POST",
        data: data,
    });
}
