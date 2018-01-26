package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.UserService;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.ChejianVo;
import com.ft.backend.work.client.vo.UserVo;
import com.ft.backend.work.client.vo.WorkerVo;
import com.ft.backend.work.model.User;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LogManager.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @RequestMapping(value = "/edit")
    @ResponseBody
    public ReturnResult<Boolean> edit(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){
        ReturnResult<Boolean> result=null;
        try {
            String id_str=request.getParameter("id");

            String userName = new String(request.getParameter("userName").getBytes("iso-8859-1"),"utf-8");

            String password = "123456";
            if(request.getParameter("password")!=null) {
                password = new String(request.getParameter("password").getBytes("iso-8859-1"), "utf-8");
            }

            String chejianIdStr = null;
            if(request.getParameter("chejianId")!=null){
                chejianIdStr =  new String(request.getParameter("chejianId").getBytes("iso-8859-1"),"utf-8");
            }
            String cheduiIdStr = null;
            if(request.getParameter("cheduiId")!=null){
                cheduiIdStr=new String(request.getParameter("cheduiId").getBytes("iso-8859-1"),"utf-8");
            }
            String workerIdStr = null;
            if(request.getParameter("workerId")!=null){
                workerIdStr=new String(request.getParameter("workerId").getBytes("iso-8859-1"),"utf-8");
            }

            String roleType = null;
            if(request.getParameter("roleType")!=null){
                roleType=new String(request.getParameter("roleType").getBytes("iso-8859-1"),"utf-8");
            }

            if(StringUtils.isBlank(workerIdStr) ||StringUtils.isBlank(roleType)  ){
                result = ReturnResult.getNewFailedInstance(-1,"所属信息或者角色类型不合法",false);
                return result;
            }



            UserVo record = new UserVo();
            record.setUserName(userName);
            record.setPassword(password);
            if(StringUtils.isNotBlank(chejianIdStr)){
                record.setChejianId(Long.parseLong(chejianIdStr));
            }
            if(StringUtils.isNotBlank(cheduiIdStr)){
                record.setCheduiId(Long.parseLong(cheduiIdStr));
            }
            if(StringUtils.isNotBlank(workerIdStr)){
                record.setWorkId(Long.parseLong(workerIdStr));
            }
            record.setRole(Integer.parseInt(roleType));


            if(StringUtils.isNotBlank(id_str)){
                Long id=Long.parseLong(id_str);
                record.setId(id);
                userService.update(record);
            }
            else{
                Map<String,Object> params =new HashedMap();
                params.put("userName", userName);
                Integer count = userService.countByParameters(params).getResult();
                if(count>0){
                    result =  ReturnResult.getNewFailedInstance(-1,"该用户名已经存在",false);
                    return result;
                }
                userService.add(record);
            }
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e) {
            logger.info("edit - error:{}", e);
            result = ReturnResult.getNewFailedInstance();
        }
        return result;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){

        JSONObject outData = new JSONObject();
        String[] idList=request.getParameterValues("ids");

        try {
            List<Long> ids=new ArrayList<>();
            for(String item:idList){
                Long id=Long.parseLong(item);
                ids.add(id);
                userService.deleteById(id);
            }
            outData.put("successed","true");
        }
        catch (Exception e){
            logger.info("delete - error:{}", e);
            outData.put("successed","false");
        }
        logger.info("delete - end, result:{}", outData);
        return  outData;
    }

    @RequestMapping(value = "/selectByParams")
    @ResponseBody
    public JSONObject selectByParams(HttpServletRequest request){

        JSONObject jsonFromBean=null;

        String currentPage=request.getParameter("pageIndex");
        String limit = request.getParameter("limit");

        int pageIndex=1;
        int pageSize=10;

        if(StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(limit)){
            pageIndex= Integer.parseInt(currentPage)+1;
            pageSize = Integer.parseInt(limit);
        }

        try {

            Map<String,Object> params=new HashMap<>();
            List<UserVo> result=new ArrayList<>();

            String userName = request.getParameter("userName");
            String userType = request.getParameter("userType");

            if(!StringUtils.isBlank(userName)){
                params.put("userName",userName);
            }

            if(!StringUtils.isBlank(userType)){
                params.put("role",Integer.parseInt(userType));
            }

            params.put("pageIndex",pageIndex);
            params.put("offset",pageSize*(pageIndex-1));
            params.put("pageSize",pageSize);

            Integer totals=userService.countByParameters(params).getResult();
            logger.info("selectByParams - info,totals={}", totals);
            if(totals>0) {

                ReturnResult<List<UserVo>> res = userService.selectByParameters(params);
                result = res.getResult();
                logger.info("selectByParams - info,result={}", result);
            }

            jsonFromBean=new JSONObject();
            jsonFromBean.put("rows",result);
            jsonFromBean.put("results",totals);
        }catch (Exception e){
            logger.info("selectByParams - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/detail",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<UserVo> userDetail(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<UserVo> result=null;

        String item=request.getParameter("id");

        Long id=Long.parseLong(item);

        result = userService.getById(id);
        return result;
    }

    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public ReturnResult<Boolean> updatePassword(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){
        ReturnResult<Boolean> result=ReturnResult.getNewFailedInstance();
        try {

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo==null){
                result= ReturnResult.getNewFailedInstance(-1,"未知异常",false);
                return result;
            }
            String password = null;
            if(request.getParameter("password")!=null) {
                password = new String(request.getParameter("password").getBytes("iso-8859-1"), "utf-8");
            }

            if(StringUtils.isNotBlank(password) && userVo.getPassword().equals(password)){
                UserVo updateRecord=new UserVo();
                updateRecord.setId(userVo.getId());
                updateRecord.setPassword(password);
                userService.update(updateRecord);
            }
            else{
                result= ReturnResult.getNewFailedInstance(-1,"原始密码不正确",false);
                return result;
            }

            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e) {
            logger.info("updatePassword - error:{}", e);
            result = ReturnResult.getNewFailedInstance(-1,"系统异常",false);
        }
        return result;
    }

    @RequestMapping(value = "/resetPwd",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Boolean> resetPwd(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<Boolean> result=ReturnResult.getNewFailedInstance();

        try {
            String[] idList=request.getParameterValues("ids");
            for(String item:idList) {
                Long id = Long.parseLong(item);
                Map<String,Object> params = new HashedMap();
                params.put("id",id);
                ReturnResult<List<UserVo>> res = userService.selectByParameters(params);
                if(res.isSuccessed()){
                    List<UserVo> userVos = res.getResult();
                    for(UserVo userVo:userVos){
                        UserVo updateRecord=new UserVo();
                        updateRecord.setId(userVo.getId());
                        updateRecord.setPassword("123456");
                        userService.update(updateRecord);
                    }
                }
            }
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            result = ReturnResult.getNewFailedInstance(-1,"未知异常",false);
            logger.info("resetPwd -user -error:{}", e);
        }
        return result;
    }
}
