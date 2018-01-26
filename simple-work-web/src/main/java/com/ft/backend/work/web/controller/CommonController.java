package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.baseVo.ListItemVo;
import com.ft.backend.work.baseVo.SelectItemListVo;
import com.ft.backend.work.client.QueryCondition.ChejianQueryCondition;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.CheduiService;
import com.ft.backend.work.client.service.ChejianService;
import com.ft.backend.work.client.service.WorkerService;
import com.ft.backend.work.client.service.ZhiwuService;
import com.ft.backend.work.client.vo.*;
import com.ft.backend.work.common.CommonConstant;
import com.ft.backend.work.model.Worker;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.krb5.internal.PAData;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by huanghongyi on 2017/11/5.
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    private Logger logger = LogManager.getLogger(CommonController.class);

    @Resource
    private ChejianService chejianService;

    @Resource
    private CheduiService cheduiService;

    @Resource
    private WorkerService workerService;

    @Resource
    private ZhiwuService zhiwuService;

    @RequestMapping(value = "/selectZhiwu",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectZhiwu(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;
        try {
            Map<String,Object> params = new HashMap<>();

            ReturnResult<List<ZhiwuVo>> resp=  zhiwuService.selectByCondition(params);
            List<ZhiwuVo> zhiwuVos =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                zhiwuVos=resp.getResult();
            }

            List<ListItemVo> itemVos =new ArrayList<>();
            ListItemVo itemVo=new ListItemVo();

            for(ZhiwuVo zhiwuVo: zhiwuVos){
                itemVo=new ListItemVo();
                itemVo.setText(zhiwuVo.getName());
                itemVo.setValue(zhiwuVo.getId().toString());
                itemVos.add(itemVo);
            }

            String jsonstr= JSON.toJSONString(itemVos);
            logger.info("selectZhiwu -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectZhiwu - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectChejian",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectChejian(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;

        try {
            ChejianQueryCondition condition = new ChejianQueryCondition();
            condition.setPageSize(null);
            condition.setPageIndex(null);

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo!=null && userVo.getRole()!= CommonConstant.ROLE_SUPER_MANAGER){
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
            processDefaultItem(request,itemVos);
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
            logger.error("selectChejian - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectChedui",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectChedui(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;
        try {
            Map<String,Object> params=new HashedMap();

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                params.put("chejianId",userVo.getChejianId());
            }
            else if(userVo.getRole()== CommonConstant.ROLE_CHEDUI_MANAGER){
                params.put("chejianId",userVo.getChejianId());
                params.put("id",userVo.getCheduiId());
            }
            else if(userVo.getRole()== CommonConstant.ROLE_NORMAL_USER){
                params.put("chejianId",userVo.getChejianId());
                params.put("id",userVo.getCheduiId());
            }


            Long chejianId=getLongParameterFromRequest(request,"chejianId");
            if(chejianId!=null){
                params.put("chejianId",chejianId);
            }


            ReturnResult<List<CheduiVo>> resp=cheduiService.selectByParameters(params);

            List<CheduiVo> cheduiVoList =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                cheduiVoList=resp.getResult();
            }

            List<ListItemVo> itemVos =new ArrayList<>();
            processDefaultItem(request,itemVos);
            for(CheduiVo workshopVo: cheduiVoList){
                ListItemVo itemVo=new ListItemVo();
                itemVo.setText(workshopVo.getCheduiName());
                itemVo.setValue(workshopVo.getId().toString());
                itemVos.add(itemVo);
            }

            String jsonstr= JSON.toJSONString(itemVos);
            logger.info("selectChedui -info, jsonstr:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectChedui - error:{}", e);
        }
        logger.info("selectChedui - end, jsonFromBean:{}",jsonFromBean);
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectGroup",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectGroup(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;
        try {
            Map<String,Object> params=new HashedMap();

            Long cheduiId=getLongParameterFromRequest(request,"cheduiId");
            if(cheduiId!=null){
                params.put("chejianId",cheduiId);
            }

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                params.put("chejianId",userVo.getChejianId());
            }
            else if(userVo.getRole() == CommonConstant.ROLE_CHEDUI_MANAGER){
                params.put("chejianId",userVo.getChejianId());
                params.put("cheduiId",userVo.getCheduiId());
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
            logger.info("selectGroup -info, jsonstr:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectGroup - error:{}", e);
        }
        logger.info("selectGroup - end, jsonFromBean:{}",jsonFromBean);
        return jsonFromBean;
    }


    @RequestMapping(value = "/selectWorker",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectWorker(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;

        try {
            Map<String,SelectItemListVo> chejianVoMap = new HashedMap();

            Map<String,Object> params=new HashedMap();
            ChejianQueryCondition condition=new ChejianQueryCondition();
            condition.setPageIndex(null);
            condition.setPageSize(null);
            ReturnResult<List<ChejianVo>> resp=chejianService.selectByCondition(condition);

            List<ChejianVo> chejianVos =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                chejianVos=resp.getResult();
            }

            List<SelectItemListVo> itemVos =new ArrayList<>();
            for(ChejianVo chejianVo: chejianVos){
                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("j"+chejianVo.getId());
                selectItemListVo.setText(chejianVo.getChejianName());
                selectItemListVo.setLeaf(true);
                selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                chejianVoMap.put("j"+chejianVo.getId(), selectItemListVo);
            }

            /**************************************************************************/

            ReturnResult<List<CheduiVo>> cheduiResp=cheduiService.selectByParameters(params);
            Map<String,SelectItemListVo> cheduiVoMap = new HashedMap();

            List<CheduiVo> cheduiVoList =new ArrayList<>();
            if(cheduiResp!=null && cheduiResp.getResult()!=null){
                cheduiVoList=cheduiResp.getResult();
            }
            List<SelectItemListVo> selectItemListVos =new ArrayList<>();
            for(CheduiVo cheduiVo: cheduiVoList){

                Long chejianId=cheduiVo.getChejianId();

                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("d"+cheduiVo.getId());
                selectItemListVo.setText(cheduiVo.getChejianName()+"-"+cheduiVo.getCheduiName());
                selectItemListVo.setLeaf(true);
                selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());

                List<SelectItemListVo> tt=chejianVoMap.get("j"+chejianId).getChildren();
                if(tt==null){
                    tt=new ArrayList<>();
                }
                tt.add(selectItemListVo);
                chejianVoMap.get("j"+chejianId).setChildren(tt);

                cheduiVoMap.put("d"+cheduiVo.getId(), selectItemListVo);
            }

            /**************************************************************************/
            List<WorkerVo> workerVos=new ArrayList<>();
            List<SelectItemListVo> extraWorkerVos=new ArrayList<>();
            ReturnResult<List<WorkerVo>> workerResp=workerService.selectByParameters(params);
            if(workerResp!=null){
                workerVos = workerResp.getResult();
            }

            for(WorkerVo workerVo: workerVos){
                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("w"+workerVo.getId());

                String tex ="";
                if(StringUtils.isNotBlank(workerVo.getZhiwuName())){
                    tex = tex+workerVo.getZhiwuName()+"-";
                }
                tex = tex + workerVo.getName();
                selectItemListVo.setText(tex);
                selectItemListVo.setLeaf(true);

                if(chejianVoMap.get("j"+workerVo.getChejianId())!=null){

                    if(cheduiVoMap.get("d"+workerVo.getCheduiId())==null){
                        SelectItemListVo cheduiVo = new SelectItemListVo();
                        cheduiVo.setId("");
                        cheduiVo.setText("其他");
                        cheduiVo.setLeaf(true);
                        cheduiVo.setChildren(new ArrayList<SelectItemListVo>());

                        cheduiVo.getChildren().add(selectItemListVo);

                        chejianVoMap.get("j"+workerVo.getChejianId()).getChildren().add(cheduiVo);
                    }
                    else{
                        cheduiVoMap.get("d"+workerVo.getCheduiId()).getChildren().add(selectItemListVo);
                    }
                }
            }

            selectItemListVos = new ArrayList<SelectItemListVo>(chejianVoMap.values());
            selectItemListVos.addAll(extraWorkerVos);

            String jsonstr= JSON.toJSONString(selectItemListVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectWorker - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectWorkerList",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectWorkerList(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;

        try {
            Map<String,SelectItemListVo> chejianVoMap = new HashedMap();

            Map<String,Object> params=new HashedMap();
            ChejianQueryCondition condition=new ChejianQueryCondition();
            condition.setPageIndex(null);
            condition.setPageSize(null);
            ReturnResult<List<ChejianVo>> resp=chejianService.selectByCondition(condition);

            List<ChejianVo> chejianVos =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                chejianVos=resp.getResult();
            }

            List<SelectItemListVo> itemVos =new ArrayList<>();
            for(ChejianVo chejianVo: chejianVos){
                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("j"+chejianVo.getId());
                selectItemListVo.setText(chejianVo.getChejianName());
                selectItemListVo.setLeaf(true);
                selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                chejianVoMap.put("j"+chejianVo.getId(), selectItemListVo);
            }

            /**************************************************************************/

            ReturnResult<List<CheduiVo>> cheduiResp=cheduiService.selectByParameters(params);

            Map<String,SelectItemListVo> cheduiVoMap = new HashedMap();

            List<CheduiVo> cheduiVoList =new ArrayList<>();
            if(cheduiResp!=null && cheduiResp.getResult()!=null){
                cheduiVoList=cheduiResp.getResult();
            }

            List<SelectItemListVo> selectItemListVos =new ArrayList<>();

            for(CheduiVo cheduiVo: cheduiVoList){

                Long chejianId=cheduiVo.getChejianId();
                chejianId = chejianId==null?-1L:chejianId;

                if(chejianVoMap.get("j"+chejianId)==null){
                    SelectItemListVo selectItemListVo = new SelectItemListVo();
                    selectItemListVo.setId("j"+chejianId);
                    selectItemListVo.setText(cheduiVo.getChejianName());
                    selectItemListVo.setLeaf(true);
                    selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                    chejianVoMap.put("j"+chejianId, selectItemListVo);
                }

                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("d"+cheduiVo.getId());
                selectItemListVo.setText(cheduiVo.getCheduiName());
                selectItemListVo.setLeaf(true);
                selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());

                List<SelectItemListVo> tt=chejianVoMap.get("j"+chejianId).getChildren();
                if(tt==null){
                    tt=new ArrayList<>();
                }
                tt.add(selectItemListVo);
                chejianVoMap.get("j"+chejianId).setChildren(tt);

                cheduiVoMap.put("d"+cheduiVo.getId(), selectItemListVo);

            }

            /**************************************************************************/
            List<WorkerVo> workerVos=new ArrayList<>();
            List<SelectItemListVo> extraWorkerVos=new ArrayList<>();
            ReturnResult<List<WorkerVo>> workerResp=workerService.selectByParameters(params);
            if(workerResp!=null){
                workerVos = workerResp.getResult();
            }

            for(WorkerVo workerVo: workerVos){
                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("w"+workerVo.getId());
                selectItemListVo.setText(workerVo.getZhiwuName()+"-"+workerVo.getName());
                selectItemListVo.setLeaf(true);

                if(workerVo.getChejianId()==null) {
                    workerVo.setChejianId(-1L);
                }
                if(workerVo.getCheduiId()==null) {
                    workerVo.setCheduiId(-1L);
                }

                if(chejianVoMap.get("j"+workerVo.getChejianId())==null){
                    if(cheduiVoMap.get("d"+workerVo.getCheduiId())==null){
                        //chejianVoMap.put("w"+workerVo.getId(),selectItemListVo);
                        extraWorkerVos.add(selectItemListVo);
                    }
                    else{
                        cheduiVoMap.get(workerVo.getCheduiId()).getChildren().add(selectItemListVo);
                    }
                }
                else{
                    if(cheduiVoMap.get("d"+workerVo.getCheduiId())==null){
                        chejianVoMap.get("j"+workerVo.getChejianId()).getChildren().add(selectItemListVo);
                    }
                    else{
                        cheduiVoMap.get("d"+workerVo.getCheduiId()).getChildren().add(selectItemListVo);
                    }
                }
            }

            selectItemListVos = new ArrayList<SelectItemListVo>(chejianVoMap.values());
            //selectItemListVos.addAll(extraWorkerVos);

            String jsonstr= JSON.toJSONString(selectItemListVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectWorker - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectWorkers",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectWorkers(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;

        try {

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");

            ////////////////worker/////////////////
            Map<String,Object> params=new HashedMap();

            if(userVo!=null && userVo.getChejianId()!=null && userVo.getChejianId()>0){
                params.put("chejianId",userVo.getChejianId());
            }
            if(userVo!=null && userVo.getCheduiId()!=null && userVo.getCheduiId()>0){
                params.put("cheduiId",userVo.getCheduiId());
            }

            List<SelectItemListVo> selectItemListVos=new ArrayList<>();
            ReturnResult<List<WorkerVo>> workerResp=workerService.selectByParameters(params);
            if(workerResp!=null){
                List<WorkerVo> workerVos = workerResp.getResult();

                for(WorkerVo workerVo: workerVos){
                    SelectItemListVo selectItemListVo=new SelectItemListVo();

                    selectItemListVo.setId(workerVo.getId()+"");

                    String text="";
                    if(StringUtils.isNotBlank(workerVo.getChejianName())){
                        text=text+workerVo.getChejianName()+"-";
                    }
                    if(StringUtils.isNotBlank(workerVo.getCheduiName())){
                        text=text+workerVo.getCheduiName()+"-";
                    }
                    if(StringUtils.isNotBlank(workerVo.getZhiwuName())){
                        text=text+workerVo.getZhiwuName()+"-";
                    }
                    text = text + workerVo.getName();

                    selectItemListVo.setText(text);
                    selectItemListVos.add(selectItemListVo);
                }
            }
            String jsonstr= JSON.toJSONString(selectItemListVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectWorker - error:{}", e);
        }
        return jsonFromBean;
    }


    @RequestMapping(value = "/selectKindWorker",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectKindWorker(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;
        try {
            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");

            List<SelectItemListVo> selectItemListVos = new ArrayList<>();
            String workerType = getStringParameterFromRequest(request,"workerType");
            Long qiantourenId = getLongParameterFromRequest(request,"qiantourenId");
            Map<String,Object> params=new HashedMap();
            params.put("zhiwuJc",workerType);

            if(CommonConstant.WORKER_TYPE_QIANTOUREN.equals(workerType)){
                if(userVo.getRole() == CommonConstant.ROLE_SUPER_MANAGER) {
                    qiantourenId=null;
                }

                if(qiantourenId!=null){
                    WorkerVo workerVo = workerService.selectById(qiantourenId).getResult();
                    params.put("chejianId", workerVo.getChejianId());
                }
                ReturnResult<List<WorkerVo>> workervoResp=workerService.selectByParameters(params);
                List<WorkerVo> workerVos= workervoResp.getResult();

                for(WorkerVo w: workerVos){
                    SelectItemListVo itemListVo = new SelectItemListVo();
                    itemListVo.setId("" + w.getId());
                    itemListVo.setText(getWorkerShowText(w));
                    itemListVo.setLeaf(true);
                    itemListVo.setChildren(new ArrayList<SelectItemListVo>());
                    selectItemListVos.add(itemListVo);
                }

            }
            else  if(CommonConstant.WORKER_TYPE_FUZEREN.equals(workerType)){
                if(qiantourenId!=null){
                    WorkerVo workerVo = workerService.selectById(qiantourenId).getResult();
                    params.put("chejianId", workerVo.getChejianId());
                }
                List<WorkerVo> workerVos= workerService.selectByParameters(params).getResult();

                Map<String,SelectItemListVo> s=new HashedMap();
                for(WorkerVo w: workerVos){
                    SelectItemListVo itemListVo = new SelectItemListVo();
                    itemListVo.setId("w" + w.getId());
                    itemListVo.setText(getWorkerShowText(w));
                    itemListVo.setChildren(new ArrayList<SelectItemListVo>());
                    itemListVo.setLeaf(true);

                    if(StringUtils.isNotBlank(w.getCheduiName()) && s.get(w.getCheduiName())==null){
                        SelectItemListVo cheduiItemVo = new SelectItemListVo();
                        cheduiItemVo.setId("d"+w.getCheduiId());
                        cheduiItemVo.setText(w.getChejianName()+"-"+w.getCheduiName());
                        cheduiItemVo.setChildren(new ArrayList<SelectItemListVo>());
                        cheduiItemVo.setLeaf(true);
                        cheduiItemVo.setExpanded(true);
                        s.put(w.getCheduiName(),cheduiItemVo);
                    }
                    if(w.getCheduiName()!=null) {
                        s.get(w.getCheduiName()).getChildren().add(itemListVo);
                    }

                }
                selectItemListVos.addAll(new ArrayList<SelectItemListVo>(s.values()));
            }
            else  if(CommonConstant.WORKER_TYPE_LUOSHIREN.equals(workerType)){
                if(qiantourenId!=null){
                    WorkerVo workerVo = workerService.selectById(qiantourenId).getResult();
                    params.put("chejianId", workerVo.getChejianId());
                }

                List<WorkerVo> workerVos= workerService.selectByParameters(params).getResult();
                Map<String,SelectItemListVo> s=new HashedMap();
                for(WorkerVo w: workerVos){
                    SelectItemListVo itemListVo = new SelectItemListVo();
                    itemListVo.setId("w" + w.getId());
                    itemListVo.setText(getWorkerShowText(w));
                    itemListVo.setChildren(new ArrayList<SelectItemListVo>());
                    itemListVo.setLeaf(true);


                    if(StringUtils.isNotBlank(w.getCheduiName()) && s.get(w.getCheduiName())==null){
                        SelectItemListVo cheduiItemVo = new SelectItemListVo();
                        cheduiItemVo.setId("d"+w.getCheduiId());
                        cheduiItemVo.setText(w.getChejianName()+"--"+w.getCheduiName());
                        cheduiItemVo.setChildren(new ArrayList<SelectItemListVo>());
                        cheduiItemVo.setLeaf(true);
                        cheduiItemVo.setExpanded(true);
                        s.put(w.getCheduiName(),cheduiItemVo);
                    }
                    if(w.getCheduiName()!=null) {
                        s.get(w.getCheduiName()).getChildren().add(itemListVo);
                    }
                }
                selectItemListVos.addAll(new ArrayList<SelectItemListVo>(s.values()));
            }

            String jsonstr= JSON.toJSONString(selectItemListVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectWorker - error:{}", e);
        }
        return jsonFromBean;
    }

    private String getWorkerShowText(WorkerVo workerVo){
        String text="-";
        StrBuilder sb =new StrBuilder();

        String zhiwuName="(-)";
        if(StringUtils.isNotBlank(workerVo.getZhiwuName())){
            zhiwuName="("+workerVo.getZhiwuName()+")";
        }

        sb.append(workerVo.getName()).append(zhiwuName);
        text = sb.toString();
        return  text;
    }

    private String getCheduiShowText(CheduiVo cheduiVo){
        return  cheduiVo.getChejianName()+"--"+cheduiVo.getCheduiName();
    }


    @RequestMapping(value = "/selectItemList",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectItemList(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;

        try {
            Integer type=null;

            String item=request.getParameter("type");
            if(StringUtils.isNotBlank(item)){
                type = Integer.parseInt(item);
            }

            Long cjId=null;
            Long cdId=null;
            Long wId=null;
            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){

            }
            else if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                cjId =userVo.getChejianId();
            }
            else if(userVo.getRole()== CommonConstant.ROLE_CHEDUI_MANAGER){
                cjId =userVo.getChejianId();
                cdId =userVo.getCheduiId();
            }
            else if(userVo.getRole()== CommonConstant.ROLE_NORMAL_USER){
                cjId =userVo.getChejianId();
                cdId =userVo.getCheduiId();
                wId=userVo.getWorkId();
            }


            Map<String,SelectItemListVo> chejianVoMap = new HashedMap();

            Map<String,Object> params=new HashedMap();
            ChejianQueryCondition condition=new ChejianQueryCondition();
            condition.setPageIndex(null);
            condition.setPageSize(null);
            if( cjId!=null){
                condition.setId(cjId);
            }
            ReturnResult<List<ChejianVo>> resp=chejianService.selectByCondition(condition);
            List<ChejianVo> chejianVos =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                chejianVos=resp.getResult();
            }

            List<SelectItemListVo> itemVos =new ArrayList<>();
            for(ChejianVo chejianVo: chejianVos){
                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("j"+chejianVo.getId());
                selectItemListVo.setText(chejianVo.getChejianName());
                selectItemListVo.setLeaf(true);
                selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                chejianVoMap.put("j"+chejianVo.getId(), selectItemListVo);
            }

            /**************************************************************************/

            if(cdId!=null){
                params.put("cheduiId",cdId);
            }
            ReturnResult<List<CheduiVo>> cheduiResp=cheduiService.selectByParameters(params);

            Map<String,SelectItemListVo> cheduiVoMap = new HashedMap();
            List<CheduiVo> cheduiVoList =new ArrayList<>();
            if(cheduiResp!=null && cheduiResp.getResult()!=null){
                cheduiVoList=cheduiResp.getResult();
            }

            List<SelectItemListVo> selectItemListVos =new ArrayList<>();
            for(CheduiVo cheduiVo: cheduiVoList){

                Long chejianId=cheduiVo.getChejianId();
                chejianId = chejianId==null?-1L:chejianId;

                if(chejianVoMap.get("j"+chejianId)==null){
                    SelectItemListVo selectItemListVo = new SelectItemListVo();
                    selectItemListVo.setId("j"+chejianId);
                    selectItemListVo.setText(cheduiVo.getChejianName());
                    selectItemListVo.setLeaf(true);
                    selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                    chejianVoMap.put("j"+chejianId, selectItemListVo);
                }

                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("d"+cheduiVo.getId());
                selectItemListVo.setText(cheduiVo.getCheduiName());
                selectItemListVo.setLeaf(true);
                selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());

                List<SelectItemListVo> tt=chejianVoMap.get("j"+chejianId).getChildren();
                if(tt==null){
                    tt=new ArrayList<>();
                }
                tt.add(selectItemListVo);
                chejianVoMap.get("j"+chejianId).setChildren(tt);

                cheduiVoMap.put("d"+cheduiVo.getId(), selectItemListVo);

            }

            /**************************************************************************/
            List<WorkerVo> workerVos=new ArrayList<>();
            List<SelectItemListVo> extraWorkerVos=new ArrayList<>();

            if( wId!=null){
                params.put("workerId",wId);
            }
            ReturnResult<List<WorkerVo>> workerResp=workerService.selectByParameters(params);
            if(workerResp!=null){
                workerVos = workerResp.getResult();
            }

            for(WorkerVo workerVo: workerVos){
                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId("w"+workerVo.getId());
                selectItemListVo.setText(workerVo.getZhiwuName()+"-"+workerVo.getName());
                selectItemListVo.setLeaf(true);

                if(workerVo.getChejianId()==null) {
                    workerVo.setChejianId(-1L);
                }
                if(workerVo.getCheduiId()==null) {
                    workerVo.setCheduiId(-1L);
                }

                if(chejianVoMap.get("j"+workerVo.getChejianId())==null){
                   /* if(cheduiVoMap.get("d"+workerVo.getCheduiId())==null){
                        //chejianVoMap.put("w"+workerVo.getId(),selectItemListVo);
                        extraWorkerVos.add(selectItemListVo);
                    }
                    else{
                        cheduiVoMap.get(workerVo.getCheduiId()).getChildren().add(selectItemListVo);
                    }*/
                }
                else{
                    if(cheduiVoMap.get("d"+workerVo.getCheduiId())==null){
                        //chejianVoMap.get("j"+workerVo.getChejianId()).getChildren().add(selectItemListVo);
                    }
                    else{
                        cheduiVoMap.get("d"+workerVo.getCheduiId()).getChildren().add(selectItemListVo);
                    }
                }
            }

            selectItemListVos = new ArrayList<SelectItemListVo>(chejianVoMap.values());
            //selectItemListVos.addAll(extraWorkerVos);

            String jsonstr= JSON.toJSONString(selectItemListVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectWorker - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectProvince",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectProvince(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;

        try {

            List<ListItemVo> selectItemListVos = new ArrayList();
            selectItemListVos.add(new ListItemVo("请选择",""));
            selectItemListVos.add(new ListItemVo("北京市","北京市"));
            selectItemListVos.add(new ListItemVo("天津市","天津市"));
            selectItemListVos.add(new ListItemVo("天津市","天津市"));
            selectItemListVos.add(new ListItemVo("重庆市","重庆市"));
            selectItemListVos.add(new ListItemVo("河北省","河北省"));
            selectItemListVos.add(new ListItemVo("河南省","河南省"));
            selectItemListVos.add(new ListItemVo("云南省","云南省"));
            selectItemListVos.add(new ListItemVo("辽宁省","辽宁省"));
            selectItemListVos.add(new ListItemVo("湖南省","湖南省"));
            selectItemListVos.add(new ListItemVo("安徽省","安徽省"));
            selectItemListVos.add(new ListItemVo("山东省","山东省"));
            selectItemListVos.add(new ListItemVo("江苏省","江苏省"));
            selectItemListVos.add(new ListItemVo("浙江省","浙江省"));
            selectItemListVos.add(new ListItemVo("江西省","江西省"));
            selectItemListVos.add(new ListItemVo("湖北省","湖北省"));
            selectItemListVos.add(new ListItemVo("甘肃省","甘肃省"));
            selectItemListVos.add(new ListItemVo("山西省","山西省"));
            selectItemListVos.add(new ListItemVo("陕西省","陕西省"));
            selectItemListVos.add(new ListItemVo("吉林省","吉林省"));
            selectItemListVos.add(new ListItemVo("福建省","福建省"));
            selectItemListVos.add(new ListItemVo("贵州省","贵州省"));
            selectItemListVos.add(new ListItemVo("广东省","广东省"));
            selectItemListVos.add(new ListItemVo("青海省","青海省"));
            selectItemListVos.add(new ListItemVo("四川省","四川省"));
            selectItemListVos.add(new ListItemVo("海南省","海南省"));
            selectItemListVos.add(new ListItemVo("台湾省","台湾省"));
            selectItemListVos.add(new ListItemVo("黑龙江省","黑龙江省"));
            selectItemListVos.add(new ListItemVo("西藏自治区","西藏自治区"));
            selectItemListVos.add(new ListItemVo("内蒙古自治区","内蒙古自治区"));
            selectItemListVos.add(new ListItemVo("广西壮族自治区","广西壮族自治区"));
            selectItemListVos.add(new ListItemVo("宁夏回族自治区","宁夏回族自治区"));
            selectItemListVos.add(new ListItemVo("新疆维吾尔族自治区","新疆维吾尔族自治区"));
            selectItemListVos.add(new ListItemVo("香港特别行政区","香港特别行政区"));
            selectItemListVos.add(new ListItemVo("澳门特别行政区","澳门特别行政区"));

            String jsonstr= JSON.toJSONString(selectItemListVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectWorker - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectRole",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectRole(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;

        try {

            List<ListItemVo> listItemVos = new ArrayList();
            processDefaultItem(request,listItemVos);
            listItemVos.add(new ListItemVo("普通用户","4"));
            listItemVos.add(new ListItemVo("车队管理员","3"));
            listItemVos.add(new ListItemVo("车间管理员","2"));
            listItemVos.add(new ListItemVo("超级管理员","1"));

            String jsonstr= JSON.toJSONString(listItemVos);
            logger.info("selectRole -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectRole - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectFeature",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectFeature(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;

        try {

            List<ListItemVo> selectItemListVos = new ArrayList();
            selectItemListVos.add(new ListItemVo("请选择",""));
            selectItemListVos.add(new ListItemVo("拿本未单独","拿本未单独"));
            selectItemListVos.add(new ListItemVo("技术比武","技术比武"));


            String jsonstr= JSON.toJSONString(selectItemListVos);
            logger.info("selectFeature -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.error("selectFeature - error:{}", e);
        }
        return jsonFromBean;
    }


    /**
     * 处理选择框内容
     * @param request
     * @param itemVos
     */
    private void processDefaultItem(HttpServletRequest request,List<ListItemVo> itemVos){

        boolean result = true;
        String obj =  request.getParameter("addDefault");
        if(obj!=null && StringUtils.isNotBlank(obj.toString()) ){
            try {
                Integer v = Integer.parseInt(obj.toString());
                if(v.intValue() == 0){
                    result=false;
                }
            }
            catch (Exception e){
                logger.error("needDefault - error:{}", e);
            }
        }

        if(result){
            ListItemVo itemVo = new ListItemVo("请选择","");
            itemVos.add(itemVo);
        }
    }

    private String getStringParameterFromRequest(HttpServletRequest request,String paramName){
        String result= null;

        try {
            if(request.getParameter(paramName)!=null &&
                    StringUtils.isNotBlank(request.getParameter(paramName))) {
                result = new String(request.getParameter(paramName).getBytes("iso-8859-1"), "utf-8");
            }
        }
        catch (Exception e){
            logger.info("getStringParameterFromRequest - error:{}", e);
        }
        return result;
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

    private Integer getIntegerParameterFromRequest(HttpServletRequest request,String paramName){
        Integer result= null;

        try {
            if(request.getParameter(paramName)!=null &&
                    StringUtils.isNotBlank(request.getParameter(paramName))) {
                String t = new String(request.getParameter(paramName).getBytes("iso-8859-1"), "utf-8");
                result =Integer.parseInt(t);
            }
        }
        catch (Exception e){
            logger.info("getIntegerParameterFromRequest - error:{}", e);
        }
        return result;
    }
    private Date getDateParameterFromRequest(HttpServletRequest request, String paramName, String dateFormat){
        Date result= null;

        try {
            if(request.getParameter(paramName)!=null &&
                    StringUtils.isNotBlank(request.getParameter(paramName))) {
                String date = new String(request.getParameter(paramName).getBytes("iso-8859-1"), "utf-8");
                if(dateFormat.equals("yyyy-MM-dd")){
                    String[] dataFomat={"yyyy-MM-dd"};
                    result = DateUtils.parseDate(date, dataFomat);
                }
                else {
                    String[] dataFomat={"yyyy-MM-dd HH:mm:ss"};
                    result = DateUtils.parseDate(date, dataFomat);
                }
            }
        }
        catch (Exception e){
            logger.info("getDateParameterFromRequest - error:{}", e);
        }
        return result;
    }
}
