package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.UserService;
import com.ft.backend.work.client.service.WorkerService;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.UserVo;
import com.ft.backend.work.client.vo.WorkerVo;
import com.ft.backend.work.common.CommonConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created by huanghongyi on 2016/11/19.
 */
@Controller
//@RequestMapping(value = "/l")
public class LoginAndOutController {

    private Logger logger = LogManager.getLogger(LoginAndOutController.class);
    @Resource
    private UserService userService;

    @Resource
    private WorkerService workerService;

    @RequestMapping(value = "/logincheck")
    @ResponseBody
    public ModelAndView doLogIn(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
    throws Exception{


        String url="login.html";
        String username=request.getParameter("username");//username="hhy";
        String password=request.getParameter("password");password="123456";
        UserVo userVo=(userService.getByUsernameAndPassword(username,password)).getResult();
        if(userVo !=null){
            try {

                WorkerVo workerVo = workerService.selectById(userVo.getWorkId()).getResult();
                if(workerVo!=null){
                    userVo.setWorkerName(workerVo.getName());
                }

                PrintWriter out = response.getWriter();
                httpSession.setAttribute("userVo",userVo);

                if(userVo.getRole().intValue()== CommonConstant.ROLE_SUPER_MANAGER){
                    //response.sendRedirect("main.html");
                    url="main.html";
                } else if (userVo.getRole().intValue()== CommonConstant.ROLE_NORMAL_USER) {
                    //response.sendRedirect("main_v1.html");
                    url="main_v1.html";
                }
                else  if (userVo.getRole().intValue()== CommonConstant.ROLE_CHEJIAN_MANAGER) {
                    //response.sendRedirect("main_cj.html");
                    url="main_cj.html";
                }
                else  if (userVo.getRole().intValue()== CommonConstant.ROLE_CHEDUI_MANAGER) {
                    //response.sendRedirect("main_cd.html");
                    url="main_cd.html";
                }
            }
            catch (Exception e){
               logger.error("doLogIn - error:{}", e);
            }
        }
        response.sendRedirect(url);
       return null;
    }


    @RequestMapping(value = "/getUser",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject getUser(HttpServletRequest request, HttpServletResponse response){

        String showName="";
        UserVo userVo =null;
        WorkerVo workerVo=null;
        try {
            userVo = (UserVo)request.getSession().getAttribute("userVo");
            logger.info("getUser - info, userVo:{}", userVo);
            if(userVo!=null){
                showName = userVo.getUserName();
                 workerVo = workerService.selectById(userVo.getWorkId()).getResult();
                if(workerVo!=null){
                    showName = workerVo.getName();
                }
            }
        }
        catch (Exception e){
           logger.error("getUser - error:{}", e);
        }

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userName",showName);

        return jsonObject;
    }
}
