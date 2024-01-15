import { createSlice } from '@reduxjs/toolkit';
import {getUserAPI, loginAPI} from "../../apis/user";
import {getTokenKey, setTokenKey} from "../../util/token";

const userSlice = createSlice({
    name: 'user',
    initialState: {
        token: getTokenKey() ? getTokenKey() : "",
        userId: localStorage.getItem("userId") ? localStorage.getItem("userId") : "",
        userInfo: {},
    },

    reducers: {
        setUserInfo(state, action) {
            state.userInfo = action.payload;

        },
        setToken(state, action) {
            state.token = action.payload;
            setTokenKey(action.payload);
        },
        setId(state, action) {
            state.userId = action.payload;
            localStorage.setItem("userId", action.payload);
        },
        clearUserInfo(state) {

            state.token = "";
            state.userInfo = {};
            state.userId = "";

        }

    },
});

const { setToken, setUserInfo, setId ,clearUserInfo} = userSlice.actions;

// encapuslate the APIs to api folder for easier management
const fetchLogin = (loginForm) => {
    return async (dispatch) => {
        const res = await loginAPI(loginForm);
        console.log(res);
        dispatch(setToken(res.token));
        dispatch(setId(res.userId));
    };
};


const fetchUserInfo = () => {
    return async (dispatch) => {
        const id = localStorage.getItem("userId")
        const res = await getUserAPI(id);
        dispatch(setUserInfo(res));
    };
}



const userReducer = userSlice.reducer;
export { fetchLogin, fetchUserInfo, clearUserInfo };

export default userReducer;
