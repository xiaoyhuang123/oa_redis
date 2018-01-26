package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.baseVo.ListItemVo;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.CheduiService;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.UserVo;
import com.ft.backend.work.common.CommonConstant;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.net.server.InputStreamLogEventBridge;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by huanghongyi on 2017/10/24.
 */
@Controller
@RequestMapping("/chedui")
public class CheduiController {

    private Logger logger = LogManager.getLogger(CheduiController.class);

    @Resource
    private CheduiService cheduiService;

    @RequestMapping(value = "/add",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject add(HttpServletRequest request,HttpServletResponse response){

        return null;
    }


    @RequestMapping(value = "/delete",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<Boolean> result=null;

        String[] idList=request.getParameterValues("ids");
        List<Long> ids=new ArrayList<>();
        for(String item:idList){
            Long id=Long.parseLong(item);
            ids.add(id);
            cheduiService.deleteById(id);
        }
        JSONObject outData = new JSONObject();
        outData.put("successed","true");

        return outData;
    }

    @RequestMapping(value = "/update",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Boolean> workshopUpdate(HttpServletRequest request,HttpServletResponse response) {
        ReturnResult<Boolean> result=null;
        try {
            String id_str=request.getParameter("id");

            String cheduiName = new String(
                    request.getParameter("cheduiName").getBytes("iso-8859-1"),
                    "utf-8");
            String chejianId=new String(
                    request.getParameter("chejianId").getBytes("iso-8859-1"),
                    "utf-8");


            CheduiVo cheduiVo = new CheduiVo();

            cheduiVo.setCheduiName(cheduiName);
            cheduiVo.setChejianId(Long.parseLong(chejianId));

            if( StringUtils.isNotBlank(id_str)){
                Long id=Long.parseLong(id_str);
                cheduiVo.setId(id);
                cheduiService.update(cheduiVo);
            }
            else{
                cheduiVo.setIsDeleted(0);
                cheduiVo.setCreateTime(new Date());
                cheduiService.add(cheduiVo);
            }
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e) {
            result = ReturnResult.getNewFailedInstance();
        }
        return result;
    }

/**********************************************************************/

    @RequestMapping(value = "/selectByParams")
    @ResponseBody
    public JSONObject selectByParams(HttpServletRequest request){

        JSONObject jsonFromBean=null;

        String currentPage=request.getParameter("pageIndex");
        String limit = request.getParameter("limit");

        int pageIndex=1;
        int pageSize=10;

        if( StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(limit)){
            pageIndex= Integer.parseInt(currentPage)+1;
            pageSize = Integer.parseInt(limit);
        }

        try {

            Map<String,Object> params=new HashMap<>();
            List<CheduiVo> result=new ArrayList<>();

            String chejianId = request.getParameter("chejianId");
            String cheduiName = request.getParameter("cheduiName");

            if(!StringUtils.isBlank(chejianId)){
                params.put("chejianId",Integer.parseInt(chejianId));
            }

            if(!StringUtils.isBlank(cheduiName)){
                params.put("cheduiName",cheduiName);
            }

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                params.put("chejianId",userVo.getChejianId());
            }

            params.put("pageIndex",pageIndex);
            params.put("offset",pageSize*(pageIndex-1));
            params.put("pageSize",pageSize);

            Integer totals=cheduiService.countByParameters(params).getResult();
            logger.info("listChedui - info,totals={}", totals);
            if(totals>0) {

                ReturnResult<List<CheduiVo>> res = cheduiService.selectByParameters(params);
                List<CheduiVo> records = new ArrayList<>();
                if (res != null) {
                    records = res.getResult();
                }
                result = res.getResult();
                logger.info("listChedui - info,result={}", result);
            }

            jsonFromBean=new JSONObject();
            jsonFromBean.put("rows",result);
            jsonFromBean.put("results",totals);
        }catch (Exception e){

        }
        return jsonFromBean;
    }


    @RequestMapping(value = "/detail",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<CheduiVo> cheduiDetail(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<CheduiVo> result=null;

        String item=request.getParameter("id");

        Long id=Long.parseLong(item);

        result = cheduiService.selectById(id);
        return result;
    }


    @RequestMapping(value = "/select")
    @ResponseBody
    public JSONArray select(HttpServletRequest request){

        JSONArray jsonFromBean=null;

        try {
            Map<String,Object> params=new HashedMap();

            Long chejianId=getLongParameterFromRequest(request,"chejianId");
            if(chejianId!=null){
                params.put("chejianId",chejianId);
            }

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                params.put("chejianId",userVo.getChejianId());
            }


            ReturnResult<List<CheduiVo>> resp=cheduiService.selectByParameters(params);

            List<CheduiVo> cheduiVoList =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                cheduiVoList=resp.getResult();
            }

            List<ListItemVo> itemVos =new ArrayList<>();
            for(CheduiVo workshopVo: cheduiVoList){
                ListItemVo itemVo=new ListItemVo();
                itemVo.setText(workshopVo.getCheduiName());
                itemVo.setValue(workshopVo.getId().toString());
                itemVos.add(itemVo);
            }

            String jsonstr= JSON.toJSONString(itemVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){

        }
        return jsonFromBean;
    }

    private Long getLongParameterFromRequest(HttpServletRequest request,String paramName){
        Long result= null;

        try {
            if(request.getParameter(paramName)!=null &&
                    StringUtils.isNotBlank(request.getParameter(paramName))) {
                String t = new String(request.getParameter(paramName).getBytes("iso-8859-1"), "utf-8");
                result = Long.parseLong(t);
            }
        }
        catch (Exception e){
            logger.info("getLongParameterFromRequest - error:{}", e);
        }
        return result;
    }


}
