package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.interceptor;


import com.google.gson.Gson;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.JwtUtils;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component // register this class to spring container
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private Gson gson;

    @Override //before the request is handled
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI().toString();
        log.info("request url: {}", url);

        if(url.contains("/auth")){
            return true;
        }

        String jwt = request.getHeader("Authorization");

        if(!StringUtils.hasLength(jwt)){
            log.info("jwt is empty");
//            Result error = Result.error();
            

            String notLogin = gson.toJson(ResponseEntity.badRequest().body(Result.error("NOT_LOGGED_IN")));
            response.getWriter().write(notLogin);
            return false;
        }

        try {
            JwtUtils.parseToken(jwt);
        } catch (Exception e) {
            log.info("jwt is invalid");
            Result error = Result.error("INVALID_TOKEN");
            String notLogin = gson.toJson(error);
            response.getWriter().write(notLogin);
            return false;
        }
        log.info("works fine, jwt: {}", jwt);

        return true;
    }

    @Override // after the request is handled
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override // after the response is handled
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

