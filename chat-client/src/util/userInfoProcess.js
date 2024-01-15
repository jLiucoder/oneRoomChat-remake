

export default function userInfoProcess(users){
    let processedUser = [];
    for(let i = 0; i < users.length; i++){
        processedUser.push(users[i].username);
    }

    return processedUser;
}