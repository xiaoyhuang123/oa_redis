package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.baseVo.ListItemVo;
import com.ft.backend.work.client.QueryCondition.ChejianQueryCondition;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.ChejianService;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.ChejianVo;

import com.ft.backend.work.client.vo.UserVo;
import com.ft.backend.work.common.CommonConstant;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2016/11/8.
 */
@Controller
@RequestMapping("/chejian")
public class ChejianController {

    private Logger logger = LogManager.getLogger(ChejianController.class);

    @Autowired
    private ChejianService chejianService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public JSONObject listChejian(HttpServletRequest request){

        List<ChejianVo> result=null;
        int pageIndex=1;
        int pageSize=10;

        String currentPage=request.getParameter("pageIndex");
        String limit = request.getParameter("limit");
        String pageSizeStr = request.getParameter("pageSize");

        if(StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(limit)){
            pageIndex= Integer.parseInt(currentPage)+1;
            pageSize = Integer.parseInt(limit);
        }

        JSONObject jsonFromBean=null;

        try {
            String chejianName=null;
            if(request.getParameter("chejianName")!=null) {
                chejianName = new String(
                        request.getParameter("chejianName").trim().getBytes("iso-8859-1"),
                        "utf-8");
            }

            ChejianQueryCondition condition=new ChejianQueryCondition();
            condition.setPageIndex(pageIndex);
            condition.setPageSize(pageSize);
            condition.setChejianName(chejianName);

            result=chejianService.selectByCondition1(condition);
            logger.info("listWorkshop - info,result={}", result);
            int totals=chejianService.countByCondition(condition);
            logger.info("listWorkshop - info,totals={}", totals);

            jsonFromBean=new JSONObject();
            jsonFromBean.put("rows",result);
            jsonFromBean.put("results",totals);

        }catch (Exception e){

        }
        logger.info("listWorkshop -end, res:{}", jsonFromBean.toString());
        return jsonFromBean;
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
            chejianService.deleteById(id);
        }
        JSONObject outData = new JSONObject();
        outData.put("successed","true");

        return outData;
    }


    @RequestMapping(value = "/detail",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<ChejianVo> chejianDetail(HttpServletRequest request, HttpServletResponse response){
        ReturnResult<ChejianVo> result=null;

        String item=request.getParameter("id");

        Long id=Long.parseLong(item);

       result = chejianService.selectById(id);
        return result;
    }


    @RequestMapping(value = "/update",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Boolean> chejianUpdate(HttpServletRequest request,HttpServletResponse response) {
        ReturnResult<Boolean> result=null;
        try {
            String id_str=request.getParameter("id");

            String wname = new String(
                    request.getParameter("workshopName").getBytes("iso-8859-1"),
                    "utf-8");
            String winterduction=new String(
                    request.getParameter("bfIntroduction").getBytes("iso-8859-1"),
                    "utf-8");
            String wcomment=new String(
                    request.getParameter("remarks").getBytes("iso-8859-1"),
                    "utf-8");

            ChejianVo chejianVo = new ChejianVo();

            chejianVo.setChejianName(wname);
            chejianVo.setChejianIntroduction(winterduction);
            chejianVo.setChejianInfo(wcomment);
            if(StringUtils.isNotBlank(id_str)){
                Long id=Long.parseLong(id_str);
                chejianVo.setId(id);
                chejianService.update(chejianVo);
            }
            else{
                chejianService.add(chejianVo);
            }
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e) {
            result = ReturnResult.getNewFailedInstance();
        }
        return result;
    }

    @RequestMapping(value = "/select")
    @ResponseBody
    public JSONArray select(HttpServletRequest request){

        JSONArray jsonFromBean=null;
        try {

            ChejianQueryCondition condition = new ChejianQueryCondition();
            condition.setPageSize(null);
            condition.setPageIndex(null);

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo!=null && userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                if(userVo.getChejianId()!=null){
                    condition.setId(userVo.getChejianId());
                }
            }


            ReturnResult<List<ChejianVo>> resp=chejianService.selectByCondition(condition);

            List<ChejianVo> chejianVos =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                chejianVos=resp.getResult();
            }

            List<ListItemVo> itemVos =new ArrayList<>();
            for(ChejianVo chejianVo: chejianVos){
                ListItemVo itemVo=new ListItemVo();
                itemVo.setText(chejianVo.getChejianName());
                itemVo.setValue(chejianVo.getId().toString());
                itemVos.add(itemVo);
            }

            String jsonstr= JSON.toJSONString(itemVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){

        }
        return jsonFromBean;
    }

}
