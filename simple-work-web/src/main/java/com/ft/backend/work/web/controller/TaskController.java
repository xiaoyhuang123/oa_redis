package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.client.common.ResultCode;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.*;
import com.ft.backend.work.client.vo.*;
import com.ft.backend.work.common.CommonConstant;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by huanghongyi on 2017/10/24.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    private Logger logger = LogManager.getLogger(TaskController.class);

    private String pathUrl="fileSet/";

    private Map<String,String> filesMap = new HashedMap();

    private Map<String,String> taskFilesMap = new HashedMap();

    @Resource
    private TaskService taskService;

    @Resource
    private TaskProtocalService taskProtocalService;

    @Resource
    private ChejianService chejianService;

    @Resource
    private CheduiService cheduiService;

    @Resource
    private FileService fileService;

    @Resource
    private WorkerService workerService;

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
            params.put("pageIndex",pageIndex);
            params.put("offset",pageSize*(pageIndex-1));
            params.put("pageSize",pageSize);

            List<TaskVo> result=new ArrayList<>();

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_SUPER_MANAGER){
                //超级管理员  忽略
            }
            else if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                params.put("chejianId",userVo.getChejianId());
            }
            else if(userVo.getRole()== CommonConstant.ROLE_CHEDUI_MANAGER){
                params.put("cheduiId",userVo.getCheduiId());
            }
            else if(userVo.getRole()== CommonConstant.ROLE_NORMAL_USER){
                params.put("workerId",userVo.getWorkId());
            }

            Long id = getLongParameterFromRequest(request,"id");
            if(id!=null){
                params.put("id",id);
            }

            String workType= getStringParameterFromRequest(request,"workType");
            if(StringUtils.isNotBlank(workType)){
                params.put("workType",workType);
            }

            Integer isCompleted=getIntegerParameterFromRequest(request,"isCompleted");
            if(isCompleted !=null){
                params.put("isCompleted",isCompleted);
            }

            Integer isReceived=getIntegerParameterFromRequest(request,"isReceived");
            if(isReceived !=null){
                params.put("isReceived",isReceived);
            }

            Integer ignoreParentIdNull=getIntegerParameterFromRequest(request,"ignoreParentIdNull");
            if(ignoreParentIdNull !=null){
                params.put("ignoreParentIdNull",ignoreParentIdNull);
            }

            Long parentId= getLongParameterFromRequest(request,"parentId");
            if(parentId!=null){
                params.put("parentId",parentId);
            }

            Long completeState= getLongParameterFromRequest(request,"completeState");
            if(completeState!=null){
                params.put("completeState",completeState);
            }

            logger.info("selectByParams - count, params:{}", params);
            Integer totals=taskService.countByParameters(params).getResult();
            logger.info("selectByParams - info,totals={}", totals);
            if(totals>0) {

                ReturnResult<List<TaskVo>> res = taskService.selectByParameters(params);
                if (res != null) {
                    result = res.getResult();
                }
                logger.info("selectByParams - info,result={}", result);
            }
            addStyle(result,userVo);

            jsonFromBean=new JSONObject();
            jsonFromBean.put("rows",result);
            jsonFromBean.put("results",totals);
        }catch (Exception e){
            logger.error("selectByParams - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectBySubParams")
    @ResponseBody
    public JSONObject selectBySubParams(HttpServletRequest request){

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
            params.put("pageIndex",pageIndex);
            params.put("offset",pageSize*(pageIndex-1));
            params.put("pageSize",pageSize);

            List<TaskVo> result=new ArrayList<>();

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_SUPER_MANAGER){
                //超级管理员  忽略
            }
            else if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                params.put("chejianId",userVo.getChejianId());
            }
            else if(userVo.getRole()== CommonConstant.ROLE_CHEDUI_MANAGER){
                params.put("cheduiId",userVo.getCheduiId());
            }
            else if(userVo.getRole()== CommonConstant.ROLE_NORMAL_USER){
                params.put("workerId",userVo.getWorkId());
            }

            String workType= getStringParameterFromRequest(request,"workType");
            if(StringUtils.isNotBlank(workType)){
                params.put("workType",workType);
            }

            Integer isCompleted=getIntegerParameterFromRequest(request,"isCompleted");
            if(isCompleted !=null){
                params.put("isCompleted",isCompleted);
            }

            Integer isReceived=getIntegerParameterFromRequest(request,"isReceived");
            if(isReceived !=null){
                params.put("isReceived",isReceived);
            }

            Integer ignoreParentIdNull=getIntegerParameterFromRequest(request,"ignoreParentIdNull");
            if(ignoreParentIdNull !=null){
                params.put("ignoreParentIdNull",ignoreParentIdNull);
            }

            Long parentId= getLongParameterFromRequest(request,"parentId");
            if(parentId!=null){
                params.put("parentId",parentId);
            }

            Long completeState= getLongParameterFromRequest(request,"completeState");
            if(completeState!=null){
                params.put("completeState",completeState);
            }

            logger.info("selectBySubParams - count, params:{}", params);
            Integer totals=taskService.countBySubParameters(params).getResult();
            logger.info("selectBySubParams - info,totals={}", totals);
            if(totals>0) {

                ReturnResult<List<TaskVo>> res = taskService.selectBySubParameters(params);
                if (res != null) {
                    result = res.getResult();
                }
                logger.info("selectBySubParams - info,result={}", result);
            }
            addStyle(result,userVo);

            jsonFromBean=new JSONObject();
            jsonFromBean.put("rows",result);
            jsonFromBean.put("results",totals);
        }catch (Exception e){
            logger.error("selectBySubParams - error:{}", e);
        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/selectMyTaskByParams")
    @ResponseBody
    public JSONObject selectMyTaskByParams(HttpServletRequest request){

        JSONObject jsonFromBean=new JSONObject();

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
            params.put("pageIndex",pageIndex);
            params.put("offset",pageSize*(pageIndex-1));
            params.put("pageSize",pageSize);

            List<TaskVo> result=new ArrayList<>();


            Long undoType= getLongParameterFromRequest(request,"undoType");
            if(undoType!=null){
                params.put("undoType",undoType);
            }


            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_SUPER_MANAGER){
                //超级管理员  忽略
            }
            else {
                if(undoType==3){
                    params.put("leaderId",userVo.getWorkId());
                }
                else{
                    params.put("workerId",userVo.getWorkId());
                }
            }


            String workType= getStringParameterFromRequest(request,"workType");
            if(StringUtils.isNotBlank(workType)){
                params.put("workType",workType);
            }

            Long completeState= getLongParameterFromRequest(request,"completeState");
            if(completeState!=null){
                params.put("completeState",completeState);
            }



            logger.info("selectMyTaskByParams - count, params:{}", params);
            Integer totals=taskService.countBySubParameters(params).getResult();
            logger.info("selectMyTaskByParams - info,totals={}", totals);
            if(totals>0) {

                ReturnResult<List<TaskVo>> res = taskService.selectBySubParameters(params);
                if (res != null) {
                    result = res.getResult();
                }
                logger.info("selectMyTaskByParams - info,result={}", result);
            }
            addStyle(result,userVo);

            jsonFromBean=new JSONObject();
            jsonFromBean.put("rows",result);
            jsonFromBean.put("results",totals);
        }catch (Exception e){
            logger.error("selectMyTaskByParams - error:{}", e);
        }
        return jsonFromBean;
    }


    @RequestMapping(value = "/detail",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<TaskVo> taskDetail(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<TaskVo> result=null;
        Long id=getLongParameterFromRequest(request,"id");
        logger.info("taskDetail - start, id:{}", id);
        result = taskService.selectById(id);

        UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
        addStyle(Arrays.asList(result.getResult()),userVo);


        ReturnResult<List<TaskVo>> res = taskService.selectByParentIdList(Arrays.asList(id));
        List<TaskVo> taskVos=res.getResult();

        List<TaskVo> fu=new ArrayList<>();
        List<TaskVo> luo=new ArrayList<>();

        StringBuilder fl=new StringBuilder();
        StringBuilder fhl=new StringBuilder();
        StringBuilder ll=new StringBuilder();
        StringBuilder lhl=new StringBuilder();
        for(TaskVo taskVo: taskVos){
            if(taskVo.getRoleType()==1){
                fu.add(taskVo);
                if(taskVo.getTargetType()==1){
                    fl.append(taskVo.getChejianName()).append("-").append(taskVo.getCheduiName()).append("-").append(taskVo.getWorkerName()).append(",");

                    fhl.append("w").append(taskVo.getWorkerId()).append(",");
                }
                else if(taskVo.getTargetType()==2){
                    fl.append(taskVo.getChejianName()).append("-").append(taskVo.getCheduiName()).append(",");
                    fhl.append("d").append(taskVo.getCheduiId()).append(",");
                }
                else if(taskVo.getTargetType()==3){
                    fl.append(taskVo.getChejianName()).append(",");
                    fhl.append("j").append(taskVo.getChejianId()).append(",");
                }
            }
            else if(taskVo.getRoleType()==2){
                luo.add(taskVo);
                ll.append(taskVo.getChejianName()).append("-").append(taskVo.getCheduiName()).append("-").append(taskVo.getWorkerName()).append(",");

                lhl.append("w").append(taskVo.getChejianId()).append(",");
            }
            else if(taskVo.getTargetType()==2){
                ll.append(taskVo.getChejianName()).append("-").append(taskVo.getCheduiName()).append(",");
                lhl.append("d").append(taskVo.getCheduiId()).append(",");
            }
            else if(taskVo.getTargetType()==3){
                ll.append(taskVo.getChejianName()).append(",");
                lhl.append("j").append(taskVo.getChejianId()).append(",");
            }
        }
        if(fu!=null && fu.size()>0){
            String fshow = fl.toString();fshow=fshow.substring(0,fshow.length()-1);
            String fhidden = fhl.toString();fhidden=fhidden.substring(0,fhidden.length()-1);
            result.getResult().setFuShow(fshow);
            result.getResult().setFuHidden(fhidden);
        }
        if(luo!=null && luo.size()>0){
            String lshow = ll.toString();lshow=lshow.substring(0,lshow.length()-1);
            String lhidden = lhl.toString();lhidden=lhidden.substring(0,lhidden.length()-1);
            result.getResult().setLuoShow(lshow);
            result.getResult().setLuoHidden(lhidden);
        }
        logger.info("cheduiDetail - end, result:{}", result);
        return result;
    }

    @RequestMapping(value = "/qianshou",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Boolean> taskQianshou(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<Boolean> result=null;
        String item=request.getParameter("id");
        logger.info("taskQianshou - start, id:{}", item);

        Long id=Long.parseLong(item);
        ReturnResult<TaskVo> res = taskService.selectById(id);
        TaskVo taskVo=null;
        if(res!=null && res.getResult()!=null){
            taskVo = res.getResult();
        }

        if(taskVo==null){
            logger.warn("taskQianshou - warn, taskVo is null.");
            result = ReturnResult.getNewFailedInstance(-1,"no record found.",null);
            return result;
        }

        if(taskVo.getIsReceived().intValue()==1){
            logger.warn("taskQianshou - warn, taskVo is already received.");
            result = ReturnResult.getNewFailedInstance(-1,"taskVo is already received.",null);
            return result;
        }


        try {
            TaskVo updateRecord = new TaskVo();
            updateRecord.setId(taskVo.getId());
            updateRecord.setIsReceived(1);
            updateRecord.setReceiveTime(new Date());
            updateRecord.setIsCompleted(0);
            taskService.update(updateRecord);


            updateRecord = new TaskVo();
            updateRecord.setId(taskVo.getParentId());
            updateRecord.setIsReceived(1);
            updateRecord.setReceiveTime(new Date());
            updateRecord.setIsCompleted(0);
            if(updateRecord.getId()!=null && updateRecord.getId()>0){
                taskService.update(updateRecord);
            }

            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.error("taskQianshou - error:{}", e);
            result = ReturnResult.getNewFailedInstance(-1,"unknown error.",null);
        }
        logger.info("taskQianshou - end, result:{}", result);
        return result;
    }

    @RequestMapping(value = "/setScore",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Boolean> setScore(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<Boolean> result=null;
        String item=request.getParameter("id");
        logger.info("setScore - start, id:{}", item);

        Long id=Long.parseLong(item);
        ReturnResult<TaskVo> res = taskService.selectById(id);
        TaskVo taskVo=null;
        if(res!=null && res.getResult()!=null){
            taskVo = res.getResult();
        }

        Integer iscompleted =getIntegerParameterFromRequest(request,"completeFlag");

        Integer score =getIntegerParameterFromRequest(request,"resScore");
        String scoreComment =getStringParameterFromRequest(request,"resComments");
        String operator="-";
        UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
        if(userVo!=null){
            operator=userVo.getWorkerName();
        }

        if(taskVo==null){
            logger.warn("setScore - warn, taskVo is null.");
            result = ReturnResult.getNewFailedInstance();
            return result;
        }

        /*if(taskVo.getIsCompleted()==0){
            logger.warn("setScore - warn, taskVo is not feedback.");
            result = ReturnResult.getNewFailedInstance(-1,"该任务未反馈，不允许打分",false);
            return result;
        }*/
        if(taskVo.getIsReceived()==0){
            logger.warn("setScore - warn, taskVo is not received.");
            result = ReturnResult.getNewFailedInstance(-1,"该任务未签收，不允许打分",false);
            return result;
        }

        try {
            TaskVo updateRecord = new TaskVo();
            updateRecord.setId(taskVo.getId());
            if(iscompleted!=null){
                updateRecord.setIsCompleted(iscompleted);
            }
            updateRecord.setScoreResult(scoreComment);
            updateRecord.setScore(score);
            taskService.update(updateRecord);


            ////////////////////////////////////////update task
            Long parentId=taskVo.getParentId();
            ReturnResult<List<TaskVo>> res1= taskService.selectByParentIdList(Arrays.asList(parentId));
            List<TaskVo> taskVos =  res1.getResult();
            Integer iscomplete=2;
            for(TaskVo itm: taskVos){
                if(itm.getIsCompleted()!=2){
                    iscomplete=1;
                    break;
                }
            }
            taskVo=new TaskVo();
            taskVo.setId(parentId);
            taskVo.setIsCompleted(iscomplete);
            taskVo.setCompleteTime(new Date());

            taskService.update(taskVo);

            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.error("setScore - error:{}", e);
        }
        logger.info("setScore - end, result:{}", result);
        return result;
    }

    @RequestMapping(value = "/batchQianshou",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Boolean> batchQianshou(HttpServletRequest request,HttpServletResponse response){
        ReturnResult<Boolean> result=ReturnResult.getNewFailedInstance();

        String[] idList=request.getParameterValues("ids");
        List<Long> ids=new ArrayList<>();
        for(String item:idList){
            Long id=Long.parseLong(item);


            ReturnResult<TaskVo> res = taskService.selectById(id);
            TaskVo taskVo=null;
            if(res!=null && res.getResult()!=null){
                taskVo = res.getResult();
            }

            if(taskVo==null){
                logger.warn("batchQianshou - warn, taskVo is null.");
                result = ReturnResult.getNewFailedInstance(-1,"记录不存在，请稍后重试！",null);
                return result;
            }

            if(taskVo.getIsReceived().intValue()==1){
                logger.warn("batchQianshou - warn, taskVo is already received.");
                result = ReturnResult.getNewFailedInstance(-1,"任务已签收，无法重复签收.",null);
                return result;
            }


            try {
                TaskVo updateRecord = new TaskVo();
                updateRecord.setId(taskVo.getId());
                updateRecord.setIsReceived(1);
                updateRecord.setReceiveTime(new Date());
                updateRecord.setIsCompleted(0);
                taskService.update(updateRecord);


                updateRecord = new TaskVo();
                updateRecord.setId(taskVo.getParentId());
                updateRecord.setIsReceived(1);
                updateRecord.setReceiveTime(new Date());
                updateRecord.setIsCompleted(0);
                if(updateRecord.getId()!=null && updateRecord.getId()>0){
                    taskService.update(updateRecord);
                }

                result = ReturnResult.getNewSuccessedInstance(true);
            }
            catch (Exception e){
                logger.error("batchQianshou - error:{}", e);
                result = ReturnResult.getNewFailedInstance(-1,"未知异常.",null);
            }

        }
        logger.info("batchQianshou - end, result:{}", result);
        return result;
    }

    @RequestMapping(value = "/edit",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Long> edit(HttpServletRequest request, HttpServletResponse response) {
        ReturnResult<Long> result=null;
        try {
            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");

            Long id = getLongParameterFromRequest(request,"id");

            String workType =getStringParameterFromRequest(request,"workType");
            String leaderId = getStringParameterFromRequest(request,"qiantourenId");
            if(StringUtils.isBlank(leaderId)){
                result = ReturnResult.getNewFailedInstance(ResultCode.TASK_QIANTOUREN_NULL.getCode(),
                        ResultCode.TASK_QIANTOUREN_NULL.getMsg(),null);
                return result;
            }
            WorkerVo leaderVo = workerService.selectById(Long.parseLong(leaderId)).getResult();

            String fuzerenList = getStringParameterFromRequest(request,"fuzeren");
            if(StringUtils.isBlank(fuzerenList)){
                result = ReturnResult.getNewFailedInstance(ResultCode.TASK_FUZEREN_NULL.getCode(),
                        ResultCode.TASK_FUZEREN_NULL.getMsg(),null);
                return result;
            }

            Date startTime = getDateParameterFromRequest(request,"startDate","yyyy-MM-dd");
            Date endTime = getDateParameterFromRequest(request,"endDate","yyyy-MM-dd");
            String work_summary = getStringParameterFromRequest(request,"work_summary");
            String work_detail = getStringParameterFromRequest(request,"work_detail");
            String needFeedBack = getStringParameterFromRequest(request,"NeedFeedBack");
            String feedBackType =  getStringParameterFromRequest(request,"FeedBackType");
            String luoshirenList = getStringParameterFromRequest(request,"luoshiren");

            TaskProtocalVo taskProtocalVo =new TaskProtocalVo();
            taskProtocalVo.setWorkType(workType);
            taskProtocalVo.setStartTime(startTime);
            taskProtocalVo.setEndTime(endTime);
            taskProtocalVo.setSummary(work_summary);
            taskProtocalVo.setDetails(work_detail);
            taskProtocalVo.setFeedback(Integer.parseInt(needFeedBack));
            taskProtocalVo.setFeedbackType(feedBackType);
            taskProtocalVo.setLeaderId(Long.parseLong(leaderId));
            taskProtocalVo.setLeaderName(leaderVo.getName());

            if(id!=null ){
                taskProtocalVo.setId(id);
            }
            else{
                taskProtocalVo.setPublisher(userVo.getWorkerName());
                taskProtocalVo.setCreateTime(new Date());

                taskProtocalVo.setIsDeleted(0);
                taskProtocalVo.setIsCompleted(0);
                taskProtocalVo.setIsReceived(0);
                taskProtocalVo.setCreateTime(new Date());
                taskProtocalVo.setPublisher(userVo.getWorkerName());
                taskProtocalVo.setRoleType(TaskVo.WORKER_TYPE_NONE);
                taskProtocalVo.setParentId(TaskVo.TASKVO_TYPE_PARENT_DEFAULT);
            }


            List<TaskVo> fList = generateSubTask(CommonConstant.WORKER_TYPE_FUZEREN,fuzerenList, taskProtocalVo);
            List<TaskVo> lList = generateSubTask(CommonConstant.WORKER_TYPE_LUOSHIREN,luoshirenList, taskProtocalVo);

            taskProtocalVo.setFuzerenList(fList);
            taskProtocalVo.setLuoshirenList(lList);

            ReturnResult<TaskProtocalVo> temp=null;
            if(id!=null){
                temp = taskProtocalService.update(taskProtocalVo);
            }
            else{
                temp=taskProtocalService.add(taskProtocalVo);
                if(temp!=null){
                    id = temp.getResult().getId();
                }
            }

            for(String fileName:taskFilesMap.keySet()){
                FileVo fileVo=new FileVo();
                fileVo.setTaskId(id);
                fileVo.setFileName(fileName);
                fileVo.setFilePath(taskFilesMap.get(fileName));
                fileVo.setCreaterName(userVo.getWorkerName());
                fileVo.setCreateTime(new Date());
                fileVo.setCreaterName(userVo.getWorkerName());
                fileVo.setFileType(CommonConstant.FILE_TYPE_TASK_FILE);
                fileService.add(fileVo);
            }
            taskFilesMap.clear();

            result = ReturnResult.getNewSuccessedInstance(temp.getResult().getId());
        }
        catch (Exception e) {
            result = ReturnResult.getNewFailedInstance();
            logger.error("edit - error:{}", e);
        }
        return result;
    }

    @RequestMapping(value = "/countAll")
    @ResponseBody
    public JSONObject countAll(HttpServletRequest request){

        JSONObject jsonFromBean=new JSONObject();

        try {

            String res="";
            {
                Map<String,Object> params=new HashMap<>();
                List<TaskStasticsVo> result=new ArrayList<>();

                UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
                if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                    params.put("chejianId",userVo.getChejianId());

                    Long cdid = getLongParameterFromRequest(request,"cheduiId");
                    Long wid = getLongParameterFromRequest(request,"workerId");
                    if(cdid!=null){
                        params.put("cheduiId",cdid);
                    }
                    if(wid!=null){
                        params.put("workerId",wid);
                    }
                }
                else if(userVo.getRole()== CommonConstant.ROLE_CHEDUI_MANAGER){
                    params.put("cheduiId",userVo.getCheduiId());
                    Long wid = getLongParameterFromRequest(request,"workerId");
                    if(wid!=null){
                        params.put("workerId",wid);
                    }
                }
                else if(userVo.getRole()== CommonConstant.ROLE_NORMAL_USER){
                    params.put("workerId",userVo.getWorkId());
                }
                else{
                    //從前端傳入
                    Long chejianId=getLongParameterFromRequest(request,"chejianId");
                    if(chejianId!=null){
                        params.put("chejianId",chejianId);
                    }

                    Long cheduiId=getLongParameterFromRequest(request,"cheduiId");
                    if(cheduiId!=null){
                        params.put("cheduiId",cheduiId);
                    }
                    Long workerId=getLongParameterFromRequest(request,"workerId");
                    if(workerId!=null){
                        params.put("workerId",workerId);
                    }
                }

                String workType=getStringParameterFromRequest(request,"workType");
                if(workType!=null){
                    params.put("workType",workType);
                }

                String[] dataFomat={"yyyy-MM-dd"};

                Date startTime=getDateParameterFromRequest(request,"startDate","yyyy-MM-dd");
                if(startTime!=null ) {
                    params.put("startTime",startTime);
                }
                Date endTime=getDateParameterFromRequest(request,"endDate","yyyy-MM-dd");
                if(startTime!=null ) {
                    params.put("endTime",endTime);
                }
                params.put("ignoreParentIdNull",1);
                params.put("isCompleted",2);
                params.put("score",1);
                Integer better=taskService.countByParameters(params).getResult();

                params.put("score",2);
                Integer good=taskService.countByParameters(params).getResult();

                params.put("score",3);
                Integer middle=taskService.countByParameters(params).getResult();

                params.put("score",4);
                Integer terriable=taskService.countByParameters(params).getResult();


                jsonFromBean.put("better",better);
                jsonFromBean.put("good",good);
                jsonFromBean.put("middle",middle);
                jsonFromBean.put("terriable",terriable);

                /*****************************************************************/
                params.remove("score");
                params.remove("isCompleted");

                Integer totals=taskService.countByParameters(params).getResult();
                params.put("isCompleted",0);
                Integer notComplete=taskService.countByParameters(params).getResult();
                params.put("isCompleted",1);
                Integer midComplete=taskService.countByParameters(params).getResult();
                params.put("isCompleted",2);
                Integer complete=taskService.countByParameters(params).getResult();

                jsonFromBean.put("totals",totals);
                jsonFromBean.put("complete",complete);
                jsonFromBean.put("notComplete",notComplete);
                jsonFromBean.put("midComplete",midComplete);
                jsonFromBean.put("successed",true);
           }

        }catch (Exception e){
            jsonFromBean.put("successed",false);
            logger.error("countAll - error:{}", e);
        }
        return jsonFromBean;
    }


      @RequestMapping(value = "/feedback",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Boolean> feedback(HttpServletRequest request, HttpServletResponse response) {
        ReturnResult<Boolean> result=ReturnResult.getNewFailedInstance();
        try {

            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");

            Long id = getLongParameterFromRequest(request,"id");

            Integer isCompleted =getIntegerParameterFromRequest(request,"completeFlag");

            String work_feedback = getStringParameterFromRequest(request,"work_feedback");

            String parentIdStr = getStringParameterFromRequest(request,"parentId");

            logger.info("id:{}, isColplete:{}, work_feedback:{},parentIdStr:{}",id,isCompleted,work_feedback,parentIdStr);
            List<FileVo> fileVos =new ArrayList<>();
            for(String fileName:filesMap.keySet()){
                FileVo fileVo=new FileVo();
                fileVo.setTaskId(id);
                fileVo.setFileName(fileName);
                fileVo.setFilePath(filesMap.get(fileName));
                fileVo.setCreaterName(userVo.getWorkerName());
                fileVo.setCreateTime(new Date());

                fileVo.setFileType(CommonConstant.FILE_TYPE_FEEDBACK_FILE);

                fileVos.add(fileVo);
            }

            TaskVo taskVo = null;
            ReturnResult<TaskVo> taskVoReturnResult = taskService.selectById(id);
            if(taskVoReturnResult!=null && taskVoReturnResult.isSuccessed()){
                taskVo = taskVoReturnResult.getResult();
            }

            if(taskVo==null){
                result = ReturnResult.getNewFailedInstance(-1,"记录不存在",null);
                return result;
            }

            if(work_feedback==null && fileVos.size()<1) {
                result = ReturnResult.getNewFailedInstance(-1,"反馈内容为空",null);
                return result;
            }

            //add feedback file
            for(FileVo fileVo: fileVos){
                fileService.add(fileVo);
            }
            filesMap.clear();


            Map<String,Object> params = new HashedMap();
            params.put("id",id);
            params.put("feedbackContent",work_feedback);
            params.put("completeTime",new Date());
            params.put("isCompleted",1);
            params.put("score",null);
            params.put("scoreResult",null);
            params.put("scoreUsername",userVo.getWorkerName());
            taskService.updateWithNullParams(params);

            //跟新大的task状态
            params.clear();
            params.put("id",taskVo.getParentId());
            params.put("feedbackContent",null);
            params.put("completeTime",new Date());
            params.put("isCompleted",1);
            params.put("score",null);
            params.put("scoreResult",null);
            params.put("scoreUsername",null);
            taskService.updateWithNullParams(params);

            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e) {
            result = ReturnResult.getNewFailedInstance();
            logger.info("feebback - error:{}", e);
        }
        return result;
    }

    @RequestMapping(value = "/upload",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ReturnResult<Boolean> upload(HttpServletRequest request, HttpServletResponse response) {
        ReturnResult<Boolean> result=ReturnResult.getNewFailedInstance();
        try {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置你想要的格式
            String dateStr = df.format(calendar.getTime());

            String fileUrl = pathUrl+dateStr+"/";

            Integer fileType = getIntegerParameterFromRequest(request,"filetype");

            String relativelyPath= request.getSession().getServletContext().getRealPath("/");

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();


            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {

                String fileName = null;
                MultipartFile myfile = entity.getValue();
                fileName =  new String(myfile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");

                File dir = new File(relativelyPath+fileUrl);
                if(!dir.exists()) {
                    dir.mkdirs();
                }

                //myfile.getOriginalFilename();
                byte[] bs = myfile.getBytes();
                File file = new File(relativelyPath+fileUrl + fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bs);
                fos.close();

                StringBuffer sb = new StringBuffer();
                sb.append(fileUrl);//.append(fileName);
                if(fileType!=null && fileType.intValue()==1){
                    taskFilesMap.put(fileName, sb.toString());
                }
                else {
                    filesMap.put(fileName, sb.toString());
                }
            }
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e) {
            result = ReturnResult.getNewFailedInstance();
            logger.info("feebback - error:{}", e);
        }
        return result;
    }


    @RequestMapping(value = "/stasticsTaskByParams")
    @ResponseBody
    public JSONObject stasticsTaskByParams(HttpServletRequest request){

        JSONObject jsonFromBean=new JSONObject();

        String currentPage=request.getParameter("pageIndex");
        String limit = request.getParameter("limit");

        int pageIndex=1;
        int pageSize=10;

        if(StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(limit)){
            pageIndex= Integer.parseInt(currentPage)+1;
            pageSize = Integer.parseInt(limit);
        }
        String groupByName=null;


        try {

            Map<String,Object> params=new HashMap<>();
            List<TaskStasticsVo> result=new ArrayList<>();

            Integer staticsType=getIntegerParameterFromRequest(request,"staticsType");
            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");
            if(userVo.getRole()== CommonConstant.ROLE_CHEJIAN_MANAGER){
                /*************************************************/
                params.put("chejianId",userVo.getChejianId());
                if(staticsType==1){
                }
                else  if(staticsType==2){
                    Long cheduiId=getLongParameterFromRequest(request,"cheduiId");
                    if(cheduiId!=null){
                        params.put("cheduiId",cheduiId);
                    }
                }
                else  if(staticsType==3){
                    Long cheduiId=getLongParameterFromRequest(request,"cheduiId");
                    if(cheduiId!=null){
                        params.put("cheduiId",cheduiId);
                    }
                    Long workerId=getLongParameterFromRequest(request,"workerId");
                    if(workerId!=null){
                        params.put("workerId",workerId);
                    }
                }
            }
            else if(userVo.getRole()== CommonConstant.ROLE_CHEDUI_MANAGER){
                /*************************************************/
                params.put("cheduiId",userVo.getCheduiId());
                if(staticsType==2){

                }
                else  if(staticsType==3){
                    Long workerId=getLongParameterFromRequest(request,"workerId");
                    if(workerId!=null){
                        params.put("workerId",workerId);
                    }
                }
            }
            else if(userVo.getRole()== CommonConstant.ROLE_NORMAL_USER){
                /*************************************************/
                params.put("workerId",userVo.getWorkId());
                staticsType=3;
            }
            else if(userVo.getRole()== CommonConstant.ROLE_SUPER_MANAGER){
                /*************************************************/
                if(staticsType==1){
                    Long chejianId=getLongParameterFromRequest(request,"chejianId");
                    if(chejianId!=null){
                        params.put("chejianId",chejianId);
                    }
                }
                else  if(staticsType==2){
                    Long chejianId=getLongParameterFromRequest(request,"chejianId");
                    if(chejianId!=null){
                        params.put("chejianId",chejianId);
                    }
                    Long cheduiId=getLongParameterFromRequest(request,"cheduiId");
                    if(cheduiId!=null){
                        params.put("cheduiId",cheduiId);
                    }
                }
                else  if(staticsType==3){
                    Long chejianId=getLongParameterFromRequest(request,"chejianId");
                    if(chejianId!=null){
                        params.put("chejianId",chejianId);
                    }
                    Long cheduiId=getLongParameterFromRequest(request,"cheduiId");
                    if(cheduiId!=null){
                        params.put("cheduiId",cheduiId);
                    }
                    Long workerId=getLongParameterFromRequest(request,"workerId");
                    if(workerId!=null){
                        params.put("workerId",workerId);
                    }
                }
                else if(staticsType==4){
                    Long chejianId=getLongParameterFromRequest(request,"chejianId");
                    if(chejianId!=null){
                        params.put("chejianId",chejianId);
                    }
                    Long cheduiId=getLongParameterFromRequest(request,"cheduiId");
                    if(cheduiId!=null){
                        params.put("cheduiId",cheduiId);
                    }
                    Long workerId=getLongParameterFromRequest(request,"workerId");
                    if(workerId!=null){
                        params.put("workerId",workerId);
                    }
                }
            }

            Date startTime=getDateParameterFromRequest(request,"startDate","yyyy-MM-dd");
            if(startTime!=null ) {
                params.put("startTime",startTime);
            }
            Date endTime=getDateParameterFromRequest(request,"endDate","yyyy-MM-dd");
            if(startTime!=null ) {
                params.put("endTime",endTime);
            }

            Integer ignoreParentIdNull=getIntegerParameterFromRequest(request,"ignoreParentIdNull");
            if(ignoreParentIdNull !=null){
                params.put("ignoreParentIdNull",ignoreParentIdNull);
            }

            Integer staticsTypeV=getIntegerParameterFromRequest(request,"staticsType");
            if(staticsTypeV!=null) {
                staticsType = staticsTypeV;
            }
            if(staticsType==1){
                groupByName="chejian_id";
            }
            else  if(staticsType==2){
                groupByName="chedui_id";
            }
            else  if(staticsType==3){
                groupByName="worker_id";
            }
            else if(staticsType==4){
                groupByName ="work_type";
            }
            if(groupByName!=null){
                params.put("groupByName",groupByName);
                params.put("staticsType", staticsType);
            }

            params.put("pageIndex",pageIndex);
            params.put("offset",pageSize*(pageIndex-1));
            params.put("pageSize",pageSize);

            logger.info("stasticsTaskByParams - params:{}", params);
            Integer totals=taskService.countTaskStastics(params).getResult();
            logger.info("stasticsTaskByParams - info,totals={}", totals);
            if(totals>0) {

                ReturnResult<List<TaskStasticsVo>> res = taskService.selectTaskStasticsList(params);
                if (res != null) {
                    result = res.getResult();
                    addStyle1(result, staticsType);
                }

                logger.info("stasticsTaskByParams - info,result={}", result);
            }


            jsonFromBean=new JSONObject();
            jsonFromBean.put("rows",result);
            jsonFromBean.put("results",totals);
        }catch (Exception e){
            logger.error("stasticsTaskByParams - error:{}", e);
        }
        return jsonFromBean;
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public ReturnResult<Boolean> delete(HttpServletRequest request){

        ReturnResult<Boolean> result = ReturnResult.getNewSuccessedInstance(true);

        String[] idList=request.getParameterValues("ids");
        List<Long> ids=new ArrayList<>();

        List<Long> deleteIds=new ArrayList<>();

        try {
            UserVo userVo = (UserVo)request.getSession().getAttribute("userVo");

            for(String item:idList){
                Long id=Long.parseLong(item);

                if(userVo.getRole().intValue() == CommonConstant.ROLE_SUPER_MANAGER){
                    deleteIds.add(id);
                    continue;
                }

                ReturnResult<TaskVo> taskVoReturnResult = taskService.selectById(id);
                TaskVo taskVo = taskVoReturnResult.getResult();
                if(taskVo!=null){
                    if(userVo.getWorkId().intValue() == taskVo.getLeaderId().intValue()){
                        deleteIds.add(id);
                    }
                    else{
                        ids.add(id);
                    }
                }
            }

            if(ids!=null && ids.size()>0){
                result = ReturnResult.getNewFailedInstance(ResultCode.TASK_NOT_LEADER_TO_DELETE.getCode(),ResultCode.TASK_NOT_LEADER_TO_DELETE.getMsg(),false);
                return result;
            }

            if(deleteIds!=null && deleteIds.size()>0){
                taskService.deleteByIdList(deleteIds);

                fileService.deleteByTaskIds(deleteIds);

                result = ReturnResult.getNewSuccessedInstance(true);
            }

        }
        catch (Exception e){
            logger.error("delete - error:{}", e);
            result = ReturnResult.getNewFailedInstance(ResultCode.UN_KNOW.getCode(),
                    ResultCode.UN_KNOW.getMsg(),null);
        }
        logger.info("delete - end, result:{}", result);
        return result;
    }


    private List<TaskVo> getTaskVoByParameterStr(String parameters, TaskProtocalVo taskProtocalVo){

        logger.info("getTaskVoByParameterStr - start, parameters:{}, taskProtocalVo:{}", parameters, taskProtocalVo);
        List<TaskVo> taskVos=new ArrayList<>();

        if(StringUtils.isBlank(parameters)){
            return taskVos;
        }

        List<Long> chejianIds=new ArrayList<>();
        List<Long> cheduiIds=new ArrayList<>();
        List<Long> workerIds=new ArrayList<>();
        String[] temp = parameters.split(",");
        for(String  item: temp){
            String index =item.substring(0,1);
            String  val = item.substring(1,item.length());
            if(index.equals("j")){
                chejianIds.add(Long.parseLong(val));
            }
            else if(index.equals("d")){
                cheduiIds.add(Long.parseLong(val));
            }
            else if(index.equals("w")){
                workerIds.add(Long.parseLong(val));
            }
        }

        List<ChejianVo> chejianVos=new ArrayList<>();
        if(chejianIds!=null && chejianIds.size()>0) {
            chejianVos = chejianService.selectByIdList(chejianIds).getResult();
        }
        List<CheduiVo> cheduiVos=new ArrayList<>();
        if(cheduiIds!=null && cheduiIds.size()>0) {
            cheduiVos = cheduiService.selectByIdList(cheduiIds).getResult();
        }
        List<WorkerVo> workerVos=new ArrayList<>();
        if(workerIds!=null && workerIds.size()>0){
            workerVos = workerService.selectByIdList(workerIds).getResult();
        }

        for(ChejianVo cj:chejianVos){
            TaskVo taskVo =new TaskVo();
            BeanUtils.copyProperties(taskProtocalVo,taskVo);
            taskVo.setChejianId(cj.getId());
            taskVo.setChejianName(cj.getChejianName());
            taskVo.setTargetType(3);
            taskVos.add(taskVo);
        }

        for(CheduiVo cd:cheduiVos){
            TaskVo taskVo =new TaskVo();
            BeanUtils.copyProperties(taskProtocalVo,taskVo);
            taskVo.setChejianId(cd.getChejianId());
            taskVo.setChejianName(cd.getChejianName());
            taskVo.setCheduiId(cd.getId());
            taskVo.setCheduiName(cd.getCheduiName());
            taskVo.setTargetType(2);
            taskVos.add(taskVo);
        }

        for(WorkerVo wo:workerVos){
            TaskVo taskVo =new TaskVo();
            BeanUtils.copyProperties(taskProtocalVo,taskVo);
            taskVo.setChejianId(wo.getChejianId());
            taskVo.setChejianName(wo.getChejianName());
            taskVo.setCheduiId(wo.getCheduiId());
            taskVo.setCheduiName(wo.getCheduiName());
            taskVo.setTargetType(1);
            taskVo.setWorkerId(wo.getId());
            taskVo.setWorkerName(wo.getName());

            taskVos.add(taskVo);
        }
        return taskVos;
    }


    private List<TaskVo> generateSubTask(String workerType,String parameters, TaskProtocalVo taskProtocalVo){

        logger.info("getTaskVoByParameterStr - start, workerType:{}, parameters:{}, taskProtocalVo:{}", workerType, parameters, taskProtocalVo);
        List<TaskVo> taskVos=new ArrayList<>();

        if(StringUtils.isBlank(parameters)){
            return taskVos;
        }

        List<Long> chejianIds=new ArrayList<>();
        List<Long> cheduiIds=new ArrayList<>();
        List<Long> workerIds=new ArrayList<>();
        String[] temp = parameters.split(",");
        for(String  item: temp){
            String index =item.substring(0,1);
            String  val = item.substring(1,item.length());
            if(index.equals("j")){
                chejianIds.add(Long.parseLong(val));
            }
            else if(index.equals("d")){
                cheduiIds.add(Long.parseLong(val));
            }
            else if(index.equals("w")){
                workerIds.add(Long.parseLong(val));
            }
        }

        List<ChejianVo> chejianVos=new ArrayList<>();
        if(chejianIds!=null && chejianIds.size()>0) {
            chejianVos = chejianService.selectByIdList(chejianIds).getResult();
        }
        List<CheduiVo> cheduiVos=new ArrayList<>();
        if(cheduiIds!=null && cheduiIds.size()>0) {
            cheduiVos = cheduiService.selectByIdList(cheduiIds).getResult();
        }
        List<WorkerVo> workerVos=new ArrayList<>();
        if(workerIds!=null && workerIds.size()>0){
            workerVos = workerService.selectByIdList(workerIds).getResult();
        }

        for(ChejianVo cj:chejianVos){
            TaskVo taskVo =new TaskVo();
            BeanUtils.copyProperties(taskProtocalVo,taskVo);
            taskVo.setChejianId(cj.getId());
            taskVo.setChejianName(cj.getChejianName());
            taskVo.setTargetType(3);
            taskVos.add(taskVo);
        }

        for(CheduiVo cd:cheduiVos){
            TaskVo taskVo =new TaskVo();
            BeanUtils.copyProperties(taskProtocalVo,taskVo);
            taskVo.setChejianId(cd.getChejianId());
            taskVo.setChejianName(cd.getChejianName());
            taskVo.setCheduiId(cd.getId());
            taskVo.setCheduiName(cd.getCheduiName());
            taskVo.setTargetType(2);
            taskVos.add(taskVo);
        }

        for(WorkerVo wo:workerVos){
            TaskVo taskVo =new TaskVo();
            BeanUtils.copyProperties(taskProtocalVo,taskVo);
            taskVo.setChejianId(wo.getChejianId());
            taskVo.setChejianName(wo.getChejianName());
            taskVo.setCheduiId(wo.getCheduiId());
            taskVo.setCheduiName(wo.getCheduiName());
            taskVo.setTargetType(1);
            taskVo.setWorkerId(wo.getId());
            taskVo.setWorkerName(wo.getName());

            taskVos.add(taskVo);
        }

        for(TaskVo taskVo: taskVos){
            if(CommonConstant.WORKER_TYPE_FUZEREN.equals(workerType)){
                taskVo.setRoleType(TaskVo.WORKER_TYPE_FUZEREN);
            }
            else if(CommonConstant.WORKER_TYPE_LUOSHIREN.equals(workerType)){
                taskVo.setRoleType(TaskVo.WORKER_TYPE_LUOSHIREN);
            }
        }
        logger.info("getTaskVoByParameterStr - end, taskVos:{}", taskVos);
        return taskVos;
    }

    private void addStyle(List<TaskVo> records,UserVo userVo){
        for(TaskVo item: records){

            if(userVo.getRole()!= CommonConstant.ROLE_SUPER_MANAGER  &&item.getParentId()!=null
             &&item.getParentId()>0){

                    if(userVo.getWorkId() !=null &&
                            (item.getWorkerId().intValue()==userVo.getWorkId().intValue() ||
                                    item.getLeaderId().intValue()==userVo.getWorkId().intValue())
                            ){
                        item.setCanFeedBack(1);
                    }
                    else{
                        item.setCanFeedBack(0);
                    }

                    if(userVo.getWorkId()!=null && item.getLeaderId().intValue() == userVo.getWorkId().intValue()){
                        item.setCanScore(1);
                    }
                    else{
                        item.setCanScore(0);
                    }
            }
            else{
                item.setCanFeedBack(1);
                item.setCanScore(0);
            }


            if(item.getIsCompleted() == 2 ){
                item.setCompleteFlag(true);
                item.setNotCompleteFlag(false);
            }
            else{
                item.setCompleteFlag(false);
                item.setNotCompleteFlag(true);
            }


            if(item.getIsReceived()==0){
                item.setNoReceiveNoCompleteFlag(true);
            }
            else if(item.getIsReceived()==1){
               if(item.getIsCompleted()==0){
                   item.setReceiveNoCompleteFlag(true);
               }
               else{
                   item.setReceiveCompleteFlag(true);
               }
            }

            if(userVo.getWorkId()!=null && userVo.getWorkId().intValue() == item.getLeaderId().intValue()){
                item.setCanDelete(1);
            }
            else{
                item.setCanDelete(0);
            }

            Map<String,Object> params = new HashedMap();
            params.put("taskId",item.getId());
            params.put("fileType",CommonConstant.FILE_TYPE_TASK_FILE);
            ReturnResult<Integer> count =fileService.countByParameters(params);
            if(count!=null && count.getResult()!=null && count.getResult().intValue()>0){
                item.setContainTaskFile(1);
            }

        }
    }

    private void addStyle1(List<TaskStasticsVo> records, int staticsType){
        List<Long> ids=new ArrayList<>();
        Map<Long, String> map = new HashedMap();
        for(TaskStasticsVo item: records){
            if(item.getTargetId()!=null){
                ids.add(item.getTargetId());
            }
        }

        String title="";
        if(staticsType == 1){
            List<ChejianVo> list = chejianService.selectByIdList(ids).getResult();
            for(ChejianVo chejianVo: list){
                map.put(chejianVo.getId(),chejianVo.getChejianName());
            }
        }
        else if(staticsType == 2){
            List<CheduiVo> list = cheduiService.selectByIdList(ids).getResult();
            for(CheduiVo cheduiVo: list){

                if(StringUtils.isNotBlank(cheduiVo.getChejianName())){
                    title=cheduiVo.getChejianName()+"-";
                }
                title=title+cheduiVo.getCheduiName();

                map.put(cheduiVo.getId(),title);
            }
        }
        else if(staticsType == 3){
            List<WorkerVo> list = workerService.selectByIdList(ids).getResult();
            for(WorkerVo workerVo: list){
                if(StringUtils.isNotBlank(workerVo.getChejianName())){
                    title=workerVo.getChejianName()+"-";
                }

                if(StringUtils.isNotBlank(workerVo.getCheduiName())){
                    title=title+workerVo.getCheduiName()+"-";
                }

                title=title+workerVo.getName();
                map.put(workerVo.getId(),title);
            }
        }
        else if(staticsType == 4){
            for(TaskStasticsVo item: records){
                item.setTitle(item.getTargetName());
            }
        }

        for(TaskStasticsVo item: records){
           if(item.getTargetId()!=null && map.get(item.getTargetId())!=null){
               item.setTitle(map.get(item.getTargetId()));
           }
           String s="%";
           if(item.getTaskTotal() !=0 && item.getTaskTotal()!=null && item.getCompleteTotal()!=null){
               int percentage = (item.getCompleteTotal()*100)/item.getTaskTotal();
               s=percentage + s;
               item.setCompletePercentage(s);
           }

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
                if(paramName.equals("chejianId") || paramName.equals("cheduiId") || paramName.equals("workerId")){
                     //t= t.substring(1,t.length());
                }
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

