package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Result class, used to return the result of the request
 * cleaner code, more readable
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;//response code，1 success; 0 failure
    private String msg;  //description
    private Object data; //returned data


    public static Result success(){
        return new Result(1,"success",null);
    }
    //successful response
    public static Result success(Object data){
        return new Result(1,"success",data);
    }
    //Failure response
    public static Result error(String msg){
        return new Result(0,msg,null);
    }
}
