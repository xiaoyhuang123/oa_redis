package com.ft.backend.work.web.controller;

import com.ft.backend.work.client.vo.UserVo;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by huanghongyi on 2016/12/1.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    //无需登录，允许访问的地址
    private static final String[] IGNORE_URI = {"/login.html", "/assets/","/logincheck","logincheck"};

    private static String loginURL="/login.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean flag = false;

        //创建session
        HttpSession session =request.getSession();

        //获取请求地址
        String url = request.getRequestURL().toString();

        //获得session中的用户
        UserVo userVo =(UserVo) session.getAttribute("userVo");

        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            if (userVo != null ) {
                flag = true;
            }
            else{
                response.sendRedirect(loginURL);
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
