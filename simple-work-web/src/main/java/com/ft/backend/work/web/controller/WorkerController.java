package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.baseVo.ListItemVo;
import com.ft.backend.work.baseVo.SelectItemListVo;
import com.ft.backend.work.client.common.ResultCode;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.*;
import com.ft.backend.work.client.vo.*;
import com.ft.backend.work.common.CommonConstant;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import redis.clients.jedis.Response;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.util.*;

/**
 * Created by huanghongyi on 2017/11/4.
 */
@Controller
@RequestMapping("/worker")
public class WorkerController {
    private Logger logger = LogManager.getLogger(WorkerController.class);

    @Resource
    private WorkerService workerService;

    @Resource
    private UserService userService;

    @Resource
    private ChejianService chejianService;

    @Resource
    private CheduiService cheduiService;

    @Resource
    private ZhiwuService zhiwuService;

    @Resource
    private PositionTitleService positionTitleService;

    @Resource
    private PoliticService politicService;

    @Resource
    private DegreeService degreeService;

    private String pathUrl="fileSet/";
    private Map<String,String> filesMap = new HashedMap();


    private  String[] dataFomat={"yyyy-MM-dd"};

    @RequestMapping(value = "/delete",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<Boolean> result=null;

        String[] idList=request.getParameterValues("ids");
        List<Long> ids=new ArrayList<>();
        for(String item:idList){
            Long id=Long.parseLong(item);
            ids.add(id);
            workerService.deleteById(id);

            try {
                Map<String,Object> params =new HashedMap();
                params.put("workId", id);
                ReturnResult<List<UserVo>> res = userService.selectByParameters(params);
                if(res!=null && res.getResult()!=null){
                    List<UserVo> userVos = res.getResult();
                    if(userVos!=null && userVos.size()>0){
                        for(UserVo userVo: userVos){
                            userService.deleteById(userVo.getId());
                        }
                    }

                }
            }
            catch (Exception e){
                logger.info("delete -user -error:{}", e);
            }
        }
        JSONObject outData = new JSONObject();
        outData.put("successed","true");

        return outData;
    }

    @RequestMapping(value = "/edit",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<WorkerVo> workerEdit(HttpServletRequest request, HttpServletResponse response) {
        ReturnResult<WorkerVo> result=ReturnResult.getNewFailedInstance();

        try {

            String src="";
            Integer editType=CommonConstant.OP_TYPE_ADD;
            WorkerVo workerVo = new WorkerVo();
            String workNumber=null;

            editType = getIntegerParameterFromRequest(request,"editType");
            if(editType==null){
                editType=CommonConstant.OP_TYPE_ADD;
            }


            if(editType!=null && editType.intValue()==1) {
                workNumber = new String(request.getParameter("Wnumber").getBytes("iso-8859-1"),"utf-8");
                Map<String, Object> params = new HashedMap();
                params.put("workNumber", workNumber);
                ReturnResult<List<WorkerVo>> res = workerService.selectByParameters(params);
                if (res.getResult() != null && res.getResult().size() > 0) {
                    result = ReturnResult.getNewFailedInstance(ResultCode.WORKER_NO_ALREADY_EXISTS.getCode(),ResultCode.WORKER_NO_ALREADY_EXISTS.getMsg(),null
                            );
                    return result;
                }
            }

            Integer roleType = CommonConstant.ROLE_NORMAL_USER;
            if(request.getParameter("roleType")!=null) {
                String roleTypeStr = new String(request.getParameter("roleType").getBytes("iso-8859-1"), "utf-8");
                roleType = Integer.parseInt(roleTypeStr);
            }

            if(roleType.intValue() == CommonConstant.ROLE_CHEJIAN_MANAGER){
                String cjid = new String(request.getParameter("chejianId").getBytes("iso-8859-1"),"utf-8");
                if(cjid==null || StringUtils.isBlank(cjid)){
                    result = ReturnResult.getNewFailedInstance(ResultCode.WORKER_CHEJIAN_IS_NULL.getCode(),ResultCode.WORKER_CHEJIAN_IS_NULL.getMsg(),null
                    );
                    return result;
                }
            }
            else  if(roleType.intValue() == CommonConstant.ROLE_CHEDUI_MANAGER){
                String cjid = new String(request.getParameter("chejianId").getBytes("iso-8859-1"),"utf-8");
                if(cjid==null || StringUtils.isBlank(cjid)){
                    result = ReturnResult.getNewFailedInstance(ResultCode.WORKER_CHEDUI_IS_NULL.getCode(),ResultCode.WORKER_CHEDUI_IS_NULL.getMsg(),null
                    );
                    return result;
                }
                String cdid = new String(request.getParameter("cheduiId").getBytes("iso-8859-1"),"utf-8");
                if(cdid==null || StringUtils.isBlank(cdid)){
                    result = ReturnResult.getNewFailedInstance(ResultCode.WORKER_CHEDUI_IS_NULL.getCode(),ResultCode.WORKER_CHEDUI_IS_NULL.getMsg(),null
                    );
                    return result;
                }
            }
            else if(roleType.intValue() == CommonConstant.ROLE_NORMAL_USER){

            }

            try {

                String imgUrl = pathUrl+workNumber+"/";

                String fullPath="";
                String relativelyPath= request.getSession().getServletContext().getRealPath("/");

                File dir = new File(relativelyPath+imgUrl);
                if(!dir.exists()) {
                    dir.mkdirs();
                }

                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
                String fileName = null;
                StringBuffer sb = new StringBuffer();
                for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                    MultipartFile myfile = entity.getValue();
                    fileName =  new String(myfile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
                    //myfile.getOriginalFilename();
                    src = imgUrl+fileName;

                    byte[] bs = myfile.getBytes();
                    fullPath = relativelyPath + src;
                    File file = new File(fullPath);
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bs);
                    fos.close();

                    sb.append(relativelyPath).append(pathUrl).append(fileName);
                    filesMap.put(fileName,sb.toString());
                }
            }
            catch (Exception e){
                logger.error("upload file error:{}", e);
            }

            if(StringUtils.isNotBlank(src)){
                workerVo.setImgurl("../"+src);
            }

            String id_str=request.getParameter("id");


            String name = new String(request.getParameter("Wname").getBytes("iso-8859-1"),"utf-8");
            workerVo.setName(name);
            workerVo.setWorkNumber(workNumber);


            String sex = getStringParameterFromRequest(request,"Wsex");
            workerVo.setSex(sex);

            String nation = getStringParameterFromRequest(request,"Wnation");
            workerVo.setNation(nation);

            String cardid = getStringParameterFromRequest(request,"WcardId");
            workerVo.setCardid(cardid);

            String province = getStringParameterFromRequest(request,"province");
            workerVo.setProvince(province);

            String feature = getStringParameterFromRequest(request,"feature");
            workerVo.setFeature(feature);

            Date joinTime =getDateParameterFromRequest(request,"Wjointime","yyyy-MM-dd");
            if(joinTime!=null){
                    workerVo.setJoinTime(joinTime);
            }

            Date birthTime = getDateParameterFromRequest(request,"Wjointime","yyyy-MM-dd");
            if(birthTime!=null){
                    workerVo.setBirthday(birthTime);
            }

            String workCardNo = getStringParameterFromRequest(request,"WworkCardNo");
            workerVo.setWorkCardNo(workCardNo);


            Long zhiwuId = getLongParameterFromRequest(request,"zhiwuId");
            if(zhiwuId!=null){

                ZhiwuVo zhiwuVo = zhiwuService.selectById(zhiwuId).getResult();
                workerVo.setZhiwuId(zhiwuVo.getId());
                workerVo.setZhiwuJc(zhiwuVo.getJc());
                workerVo.setZhiwuName(zhiwuVo.getName());
            }


            String telno =getStringParameterFromRequest(request,"Wtel");
            workerVo.setTelno(telno);

            Long chejianId=getLongParameterFromRequest(request,"chejianId");
            Long cheduiId=getLongParameterFromRequest(request,"cheduiId");
            if(chejianId!=null){

                ReturnResult<ChejianVo> chejianVoReturnResult = chejianService.selectById(chejianId);
                ChejianVo chejianVo = chejianVoReturnResult.getResult();
                workerVo.setChejianName(chejianVo.getChejianName());
                workerVo.setChejianId(chejianId);
            }

            if(cheduiId!=null && cheduiId>0){
                ReturnResult<CheduiVo> cheduiVoReturnResult = cheduiService.selectById(cheduiId);
                CheduiVo cheduiVo = cheduiVoReturnResult.getResult();
                workerVo.setCheduiId(cheduiId);
                workerVo.setCheduiName(cheduiVo.getCheduiName());
            }



            Long politicId=getLongParameterFromRequest(request,"politicId");
            if(politicId!=null){
                ReturnResult<PoliticVo> politicVoReturnResult = politicService.selectById(politicId);
                PoliticVo politicVo= politicVoReturnResult.getResult();

                workerVo.setPoliticalId(politicId);
                workerVo.setPolitical(politicVo.getPoliticname());
            }

            Long positionTitleId = getLongParameterFromRequest(request,"positionTitleId");
            if(positionTitleId!=null){
                ReturnResult<PositionTitleVo> positionTitleVoReturnResult =
                        positionTitleService.selectById(positionTitleId);
                PositionTitleVo record= positionTitleVoReturnResult.getResult();

                workerVo.setPositionTitleId(positionTitleId);
                workerVo.setPositionTitle(record.getPositionTitleName());
            }

            Long degreeId=getLongParameterFromRequest(request,"degreeId");
            if(degreeId!=null){
                ReturnResult<DegreeVo> degreeVoReturnResult = degreeService.selectById(degreeId);
                DegreeVo degreeVo= degreeVoReturnResult.getResult();

                workerVo.setDegreeId(degreeId);
                workerVo.setDegreeName(degreeVo.getDegreename());
            }

            String address = getStringParameterFromRequest(request,"address");
            workerVo.setAddress(address);


            Long groupId = getLongParameterFromRequest(request,"groupId");
            if(groupId!=null){
                workerVo.setGroupId(groupId);
            }

            String allowTrainTypeId = getStringParameterFromRequest(request,"allowTrainTypeId");
            if(allowTrainTypeId!=null){
                workerVo.setAllowTrainTypeName(allowTrainTypeId);
            }

            Date firstGetTime =getDateParameterFromRequest(request,"firstGetTime","yyyy-MM-dd");
            if(firstGetTime!=null){
                workerVo.setFirstGetTime(firstGetTime);
            }


            Date startTime =getDateParameterFromRequest(request,"startTime","yyyy-MM-dd");
            if(startTime!=null){
                workerVo.setStartTime(startTime);
            }

            Date endTime =getDateParameterFromRequest(request,"endTime","yyyy-MM-dd");
            if(endTime!=null){
                workerVo.setEndTime(endTime);
            }


            String password = "123456";
            if(request.getParameter("Wpasswd")!=null) {
                password = new String(request.getParameter("Wpasswd").getBytes("iso-8859-1"), "utf-8");
            }


            if(null!=id_str && StringUtils.isNotBlank(id_str)){
                Long id=Long.parseLong(id_str);
                workerVo.setId(id);
                workerService.update(workerVo);

                UserVo userVo =null;
                ReturnResult<UserVo> userVoRes = userService.getByWorkerId(id);
                if(userVoRes!=null){
                    userVo = userVoRes.getResult();

                    UserVo updateUserVo=new UserVo();
                    updateUserVo.setId(userVo.getId());
                    userVo.setPassword(password);
                    userVo.setRole(roleType);

                    if(workerVo.getChejianId()!=null){
                        userVo.setChejianId(workerVo.getChejianId());
                    }
                    if(workerVo.getCheduiId()!=null){
                        userVo.setCheduiId(workerVo.getCheduiId());
                    }
                    if(workerVo.getId()!=null){
                        userVo.setWorkId(workerVo.getId());
                    }
                    userService.update(userVo);
                }

            }
            else{
                workerVo.setIsDeleted(0);
                workerService.add(workerVo);

                UserVo userVo = new UserVo();
                userVo.setUserName(workerVo.getWorkNumber());
                userVo.setPassword(password);
                userVo.setChejianId(workerVo.getChejianId());
                userVo.setCheduiId(workerVo.getCheduiId());
                userVo.setWorkId(workerVo.getId());
                userVo.setRole(roleType);
                if(workerVo.getChejianId()!=null){
                    userVo.setChejianId(workerVo.getChejianId());
                }
                if(workerVo.getCheduiId()!=null){
                    userVo.setCheduiId(workerVo.getCheduiId());
                }
                if(workerVo.getId()!=null){
                    userVo.setWorkId(workerVo.getId());
                }

                userService.add(userVo);
            }
            result = ReturnResult.getNewSuccessedInstance();
        }
        catch (Exception e) {
            logger.error("edit - error:{}", e);
            result = ReturnResult.getNewFailedInstance(-1,"未知异常，请稍后重试!",null);
        }
        logger.info("edit - end, result:{}", result);

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

        if(StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(limit)){
            pageIndex= Integer.parseInt(currentPage)+1;
            pageSize = Integer.parseInt(limit);
        }

        try {

            Map<String,Object> params=new HashMap<>();
            List<WorkerVo> result=new ArrayList<>();

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                params.put("chejianId",userVo.getChejianId());
            }
            else  if(userVo.getRole()== CommonConstant.ROLE_CHEDUI_MANAGER){
                params.put("cheduiId",userVo.getCheduiId());
            }

            Long workerId=getLongParameterFromRequest(request,"workerId");
            if(workerId!=null){
                params.put("workId",workerId);
            }


            String chejianId = request.getParameter("chejianId");
            String cheduiId = request.getParameter("cheduiId");

            if(!StringUtils.isBlank(chejianId)){
                params.put("chejianId",Integer.parseInt(chejianId));
            }

            if(!StringUtils.isBlank(cheduiId)){
                params.put("cheduiId",Integer.parseInt(cheduiId));
            }

            String province=getStringParameterFromRequest(request,"province");
            if(StringUtils.isNotBlank(province)){
                params.put("province",province);
            }

            String feature=getStringParameterFromRequest(request,"feature");
            if(StringUtils.isNotBlank(feature)){
                params.put("feature",feature);
            }

            String workerName=getStringParameterFromRequest(request,"workerName");
            if(StringUtils.isNotBlank(workerName)){
                params.put("name",workerName);
            }

            Date joinTimeStart = getDateParameterFromRequest(request,"joinTimeStart","yyyy-MM-dd");
            Date joinTimeEnd = getDateParameterFromRequest(request,"joinTimeEnd","yyyy-MM-dd");

            if(joinTimeStart!=null){
                params.put("joinTimeStart",joinTimeStart);
            }
            if(joinTimeEnd!=null){
                params.put("joinTimeEnd",joinTimeEnd);
            }

            Long politicId = getLongParameterFromRequest(request,"politicId");
            Long degreeId = getLongParameterFromRequest(request,"degreeId");
            Long positionTitleId = getLongParameterFromRequest(request,"positionTitleId");
            Long zhiwuId = getLongParameterFromRequest(request,"zhiwuId");

            if(politicId!=null){
                params.put("politicId",politicId);
            }
            if(degreeId!=null){
                params.put("degreeId",degreeId);
            }
            if(positionTitleId!=null){
                params.put("positionTitleId",positionTitleId);
            }
            if(zhiwuId!=null){
                params.put("zhiwuId",zhiwuId);
            }

            params.put("pageIndex",pageIndex);
            params.put("offset",pageSize*(pageIndex-1));
            params.put("pageSize",pageSize);

            Integer totals=workerService.countByParameters(params).getResult();
            logger.info("selectByParams - info,totals={}", totals);
            if(totals>0) {

                ReturnResult<List<WorkerVo>> res = workerService.selectByParameters(params);
                List<WorkerVo> records = new ArrayList<>();
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
    public ReturnResult<WorkerVo> workerDetail(HttpServletRequest request,HttpServletResponse response){
        logger.info("workerDetail - start.");
        ReturnResult<WorkerVo> result=null;

        String item=request.getParameter("id");

        Long id=Long.parseLong(item);
        try {
            result = workerService.selectById(id);

            Map<String,Object> params=new HashedMap();
            params.put("workId",result.getResult().getId());
            ReturnResult<List<UserVo>> res = userService.selectByParameters(params);
            if(res!=null && res.getResult()!=null && res.getResult().size()>0){
                UserVo userVo= res.getResult().get(0);
                result.getResult().setRole(userVo.getRole());
            }

        }
        catch (Exception e){
            logger.error("workerDetail - error:{}", e);
            result=ReturnResult.getNewFailedInstance();
        }
        logger.info("workerDetail - end.");
        return result;
    }


    @RequestMapping(value = "/select")
    @ResponseBody
    public JSONArray select(HttpServletRequest request){

        JSONArray jsonFromBean=null;
        try {

            Map<String,Object> params=new HashedMap();

            Long chejianId = getLongParameterFromRequest(request,"chejianId");
            Long cheduiId = getLongParameterFromRequest(request,"cheduiId");


            if(chejianId!=null){
                params.put("chejianId",chejianId);
            }
            if(cheduiId!=null){
                params.put("cheduiId",cheduiId);
            }

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole() == CommonConstant.ROLE_CHEDUI_MANAGER){
                params.put("cheduiId",userVo.getCheduiId());
            }

            ReturnResult<List<WorkerVo>> resp=workerService.selectByParameters(params);

            List<WorkerVo> workerVos =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                workerVos=resp.getResult();
            }

            List<ListItemVo> itemVos =new ArrayList<>();
            ListItemVo defaultVo = new ListItemVo("请选择","");
            itemVos.add(defaultVo);
            for(WorkerVo workerVo: workerVos){
                ListItemVo itemVo=new ListItemVo();
                itemVo.setText(workerVo.getName());
                itemVo.setValue(workerVo.getId().toString());
                itemVos.add(itemVo);
            }

            String jsonstr= JSON.toJSONString(itemVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.info("list - error:{}", e);
        }
        return jsonFromBean;
    }


    @RequestMapping(value = "/upload",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject upload(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject=new JSONObject();

        String fullPath="";
        try {
            String relativelyPath= request.getSession().getServletContext().getRealPath("/");

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            String fileName = null;
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile myfile = entity.getValue();
                fileName =  new String(myfile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
                //myfile.getOriginalFilename();
                byte[] bs = myfile.getBytes();
                fullPath = relativelyPath + pathUrl + fileName;
                File file = new File(fullPath);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bs);
                fos.close();

                sb.append(relativelyPath).append(pathUrl).append(fileName);
                filesMap.put(fileName,sb.toString());
            }
            jsonObject.put("success",true);
            jsonObject.put("url",pathUrl + fileName);
        }
        catch (Exception e) {
            jsonObject.put("success",false);
            logger.info("upload - error:{}", e);
        }
        return jsonObject;
    }


    @RequestMapping(value = "/upLoadImg", method = RequestMethod.POST)
    public String upLoadImg(HttpServletRequest request) {
        String src="";
        try {
            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");

            String imgUrl = pathUrl+userVo.getUserName()+"/";

            String fullPath="";
            String relativelyPath= request.getSession().getServletContext().getRealPath("/");

            File dir = new File(relativelyPath+imgUrl);
            dir.mkdirs();

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            String fileName = null;
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile myfile = entity.getValue();
                fileName =  new String(myfile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
                //myfile.getOriginalFilename();
                src = imgUrl+fileName;

                byte[] bs = myfile.getBytes();
                fullPath = relativelyPath + src;
                File file = new File(fullPath);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bs);
                fos.close();

                sb.append(relativelyPath).append(pathUrl).append(fileName);
                filesMap.put(fileName,sb.toString());
            }
        }
        catch (Exception e){
            logger.error("upload file error:{}", e);
        }
        return src;
    }


    /**
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectKindWorker",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONArray selectKindWorker(HttpServletRequest request, HttpServletResponse response){

        JSONArray jsonFromBean=null;
        try {

            Map<String,Object> params=new HashedMap();
            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");

            //查询不同角色
            String workerType = getStringParameterFromRequest(request,"workerType");
            if(StringUtils.isNotBlank(workerType)){
                params.put("zhiwuJc",workerType);
            }

            if(CommonConstant.WORKER_TYPE_QIANTOUREN.equals(workerType)){
                if(userVo.getRole() == CommonConstant.ROLE_SUPER_MANAGER){
                    //超级管理员可以查看所有的
                    ReturnResult<List<WorkerVo>> workervoResp=workerService.selectByParameters(params);
                    List<WorkerVo> workerVos= workervoResp.getResult();
                }
                else if(userVo.getRole() == CommonConstant.ROLE_CHEJIAN_MANAGER
                        || userVo.getRole() == CommonConstant.ROLE_CHEDUI_MANAGER){
                    //车间管理员查看本车间
                }
            }
            else if(CommonConstant.WORKER_TYPE_FUZEREN.equals(workerType)){

            }
            else if(CommonConstant.WORKER_TYPE_LUOSHIREN.equals(workerType)){

            }









            String duty="-";

            List<SelectItemListVo> selectItemListVos = new ArrayList<>();



            String qiantourenId = null;
            if(request.getParameter("qiantourenId")!=null){
                qiantourenId = new String(
                        request.getParameter("qiantourenId").getBytes("iso-8859-1"),"utf-8");
            }

            if(userVo.getRole() == CommonConstant.ROLE_SUPER_MANAGER){
                //超级管理员
                if(workerType.equals(CommonConstant.WORKER_TYPE_QIANTOUREN)){
                    //牵头人
                    params.put("zhiwuJc",workerType);
                    ReturnResult<List<WorkerVo>> workervoResp=workerService.selectByParameters(params);
                    List<WorkerVo> workerVos= workervoResp.getResult();

                    for(WorkerVo w: workerVos){
                        SelectItemListVo selectItemListVo = new SelectItemListVo();
                        selectItemListVo.setId(w.getId()+"");
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }
                        selectItemListVo.setText(w.getChejianName()+"--"+w.getName() + "("+duty+")");
                        selectItemListVo.setLeaf(true);
                        selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                        selectItemListVos.add(selectItemListVo);

                    }
                }
                else if(workerType.equals(CommonConstant.WORKER_TYPE_FUZEREN)){

                    WorkerVo workerVo = workerService.selectById(Long.parseLong(qiantourenId)).getResult();

                    Map<String,Object> params1=new HashedMap();
                    params1.put("chejianId", workerVo.getChejianId());
                    params1.put("zhiwuJc","100");

                    List<WorkerVo> cheduiManage= workerService.selectByParameters(params1).getResult();
                    Map<String,SelectItemListVo> s=new HashedMap();
                    for(WorkerVo w: cheduiManage){
                        SelectItemListVo wVo = new SelectItemListVo();
                        wVo.setId("w"+w.getId());
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }
                        wVo.setText(w.getName()+"("+duty+")");
                        wVo.setChildren(new ArrayList<SelectItemListVo>());
                        wVo.setLeaf(true);
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }

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
                            s.get(w.getCheduiName()).getChildren().add(wVo);
                        }

                    }
                    selectItemListVos.addAll(new ArrayList<SelectItemListVo>(s.values()));

                }
                else if(workerType.equals(CommonConstant.WORKER_TYPE_LUOSHIREN)){
                    //落实人
                    WorkerVo workerVo = workerService.selectById(Long.parseLong(qiantourenId)).getResult();

                    Map<String,Object> params1=new HashedMap();
                    params1.put("chejianId", workerVo.getChejianId());
                    params1.put("zhiwuJc","110");
                    workerService.selectByParameters(params1);
                    List<WorkerVo> cheduiManage= workerService.selectByParameters(params1).getResult();
                    Map<String,SelectItemListVo> s=new HashedMap();
                    for(WorkerVo w: cheduiManage){
                        SelectItemListVo wVo = new SelectItemListVo();
                        wVo.setId("w"+w.getId());
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }
                        wVo.setText(w.getName()+"("+duty+")");
                        wVo.setChildren(new ArrayList<SelectItemListVo>());
                        wVo.setLeaf(true);


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
                            s.get(w.getCheduiName()).getChildren().add(wVo);
                        }
                    }
                    selectItemListVos.addAll(new ArrayList<SelectItemListVo>(s.values()));
                }
            }
            else if(userVo.getRole() == CommonConstant.ROLE_CHEJIAN_MANAGER){
                //车间管理员
                if(workerType.equals(CommonConstant.WORKER_TYPE_QIANTOUREN)){
                    //牵头人
                    params.put("zhiwuJc",workerType);
                    params.put("chejianId",userVo.getChejianId());
                    ReturnResult<List<WorkerVo>> workervoResp=workerService.selectByParameters(params);
                    List<WorkerVo> workerVos= workervoResp.getResult();

                    for(WorkerVo w: workerVos){
                        SelectItemListVo selectItemListVo = new SelectItemListVo();
                        selectItemListVo.setId(w.getId()+"");
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }
                        selectItemListVo.setText(w.getChejianName()+"--"+w.getName() + "("+duty+")");
                        selectItemListVo.setLeaf(true);
                        selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                        selectItemListVos.add(selectItemListVo);
                    }
                }
                else if(workerType.equals(CommonConstant.WORKER_TYPE_FUZEREN)){
                    //fuzeren人
                    WorkerVo workerVo = workerService.selectById(Long.parseLong(qiantourenId)).getResult();

                    Map<String,Object> params1=new HashedMap();
                    params1.put("chejianId", workerVo.getChejianId());
                    List<CheduiVo> cheduiVos = cheduiService.selectByParameters(params1).getResult();

                    for(CheduiVo cheduiVo: cheduiVos){
                        SelectItemListVo cheduiItem = new SelectItemListVo();
                        cheduiItem.setId("d"+cheduiVo.getId());
                        cheduiItem.setText(cheduiVo.getChejianName()+"--"+cheduiVo.getCheduiName());
                        cheduiItem.setChildren(new ArrayList<SelectItemListVo>());
                        cheduiItem.setLeaf(true);
                        selectItemListVos.add(cheduiItem);
                    }

                    params1.put("zhiwuJc","100");
                    workerService.selectByParameters(params1);
                    List<WorkerVo> cheduiManage= workerService.selectByParameters(params1).getResult();
                    Map<String,SelectItemListVo> s=new HashedMap();
                    for(WorkerVo w: cheduiManage){
                        SelectItemListVo wVo = new SelectItemListVo();
                        wVo.setId("w"+w.getId());
                        wVo.setText(w.getName()+"("+duty+")");
                        wVo.setChildren(new ArrayList<SelectItemListVo>());
                        wVo.setLeaf(true);
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }

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
                            s.get(w.getCheduiName()).getChildren().add(wVo);
                        }

                    }
                    selectItemListVos.addAll(new ArrayList<SelectItemListVo>(s.values()));
                }
                else if(workerType.equals(CommonConstant.WORKER_TYPE_LUOSHIREN)){
                    //luoshi人

                    WorkerVo workerVo = workerService.selectById(Long.parseLong(qiantourenId)).getResult();

                    Map<String,Object> params1=new HashedMap();
                    params1.put("chejianId", workerVo.getChejianId());
                    params1.put("zhiwuJc","110");
                    workerService.selectByParameters(params1);
                    List<WorkerVo> cheduiManage= workerService.selectByParameters(params1).getResult();
                    Map<String,SelectItemListVo> s=new HashedMap();
                    for(WorkerVo w: cheduiManage){
                        SelectItemListVo wVo = new SelectItemListVo();
                        wVo.setId("w"+w.getId());
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }
                        wVo.setText(w.getName()+"("+duty+")");
                        wVo.setChildren(new ArrayList<SelectItemListVo>());
                        wVo.setLeaf(true);


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
                            s.get(w.getCheduiName()).getChildren().add(wVo);
                        }
                    }
                    selectItemListVos.addAll(new ArrayList<SelectItemListVo>(s.values()));
                }
            }
            else if(userVo.getRole() == CommonConstant.ROLE_CHEDUI_MANAGER){
                //车队管理员
                if(workerType.equals(CommonConstant.WORKER_TYPE_QIANTOUREN)){
                    //牵头人
                    params.put("zhiwuJc",workerType);
                    params.put("cheduiId",userVo.getCheduiId());
                    ReturnResult<List<WorkerVo>> workervoResp=workerService.selectByParameters(params);
                    List<WorkerVo> workerVos= workervoResp.getResult();

                    for(WorkerVo w: workerVos){
                        SelectItemListVo selectItemListVo = new SelectItemListVo();
                        selectItemListVo.setId(w.getId()+"");
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }
                        selectItemListVo.setText(w.getName() + "("+duty+")");
                        selectItemListVo.setLeaf(true);
                        selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                        selectItemListVos.add(selectItemListVo);
                    }
                }
                else if(workerType.equals(CommonConstant.WORKER_TYPE_FUZEREN)){
                    //fuze人
                    workerType=CommonConstant.WORKER_TYPE_QIANTOUREN;
                    params.put("zhiwuJc",workerType);
                    params.put("cheduiId",userVo.getCheduiId());
                    ReturnResult<List<WorkerVo>> workervoResp=workerService.selectByParameters(params);
                    List<WorkerVo> workerVos= workervoResp.getResult();

                    for(WorkerVo w: workerVos){
                        SelectItemListVo selectItemListVo = new SelectItemListVo();
                        selectItemListVo.setId(w.getId()+"");
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }
                        selectItemListVo.setText(w.getName() + "("+duty+")");
                        selectItemListVo.setLeaf(true);
                        selectItemListVo.setChildren(new ArrayList<SelectItemListVo>());
                        selectItemListVos.add(selectItemListVo);
                    }
                }
                else if(workerType.equals(CommonConstant.WORKER_TYPE_LUOSHIREN)){
                    //luoshiren

                    params.clear();
                    params.put("cheduiId", userVo.getCheduiId());
                    params.put("zhiwuJc","110");
                    List<WorkerVo> cheduiManage= workerService.selectByParameters(params).getResult();
                    Map<String,SelectItemListVo> s=new HashedMap();
                    for(WorkerVo w: cheduiManage){
                        SelectItemListVo wVo = new SelectItemListVo();
                        wVo.setId("w"+w.getId());
                        if(w.getZhiwuName()!=null){
                            duty = w.getZhiwuName();
                        }
                        wVo.setText(w.getName()+"("+duty+")");
                        wVo.setChildren(new ArrayList<SelectItemListVo>());
                        wVo.setLeaf(true);


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
                            s.get(w.getCheduiName()).getChildren().add(wVo);
                        }
                    }
                    selectItemListVos.addAll(new ArrayList<SelectItemListVo>(s.values()));
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


    private  List<SelectItemListVo> getWorker(String workerType,Map<String,Object> params){
        logger.info("getWorker - start, workerType:{}, params:{}", workerType, params);
        List<SelectItemListVo> result = new ArrayList<>();
        try {

            /**
             * param 参数类别
             * 1.车间ID
             * 2.车队ID
             * 3.zhiwu_jc
             */

            List<WorkerVo> workerVos = new ArrayList<>();
            for(WorkerVo item: workerVos){
                SelectItemListVo selectItemListVo = new SelectItemListVo();
                selectItemListVo.setId(item.getId()+"");
                selectItemListVo.setLeaf(true);
                selectItemListVo.setText(getWorkerShowText(item));
                result.add(selectItemListVo);
            }
        }
        catch (Exception e){
            logger.info("getWorker - error:{}", e);
        }
        logger.info("getWorker - end, result:{}", result);
        return result;
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
    private Date getDateParameterFromRequest(HttpServletRequest request,String paramName,String dateFormat){
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
