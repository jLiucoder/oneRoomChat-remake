const TOKEN_KEY = 'token';

const setTokenKey = (token) => {
    localStorage.setItem(TOKEN_KEY, token);
}

const getTokenKey = () => {
    return localStorage.getItem(TOKEN_KEY);
}

const removeTokenKey = () => {
    localStorage.removeItem(TOKEN_KEY);
}

export { setTokenKey, getTokenKey, removeTokenKey };
