package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.TaskService;
import com.ft.backend.work.client.vo.*;
import com.ft.backend.work.dao.CheduiMapper;
import com.ft.backend.work.dao.TaskMapper;
import com.ft.backend.work.model.Chedui;
import com.ft.backend.work.model.Task;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/3.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService{

    private Logger logger= LogManager.getLogger(TaskServiceImpl.class);

    @Resource
    private TaskMapper taskMapper;

    @Override
    public ReturnResult<TaskVo> add(TaskVo record) {

        ReturnResult<TaskVo> taskVoReturnResult=ReturnResult.getNewSuccessedInstance();
        Task task=new Task();
        BeanUtils.copyProperties(record,task);

        taskMapper.insertSelective(task);
        record.setId(task.getId());

        taskVoReturnResult = ReturnResult.getNewSuccessedInstance(record);
        return taskVoReturnResult;
    }

    @Override
    public ReturnResult<Boolean> deleteById(Long id) {
        logger.info("deleteById - info,  id:{}",id);
        ReturnResult<Boolean> result=null;
        try {
            taskMapper.deleteByPrimaryKey(id);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.info("deleteById - error:{} ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("deleteById - end, result:{}",result);
        return result;
    }

    @Override
    public ReturnResult<Boolean> updateWithNullParams(Map<String,Object> params) {
        logger.info("updateWithNullParams chedui begin, params={}",params);
        ReturnResult<Boolean> result=null;
        try {
            taskMapper.updateByPrimaryKeySelectiveWithNull(params);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.error("updateWithNullParams - error:{} ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("updateWithNullParams - end, result:{}",result);
        return result;
    }


    @Override
    public ReturnResult<Boolean> update(TaskVo record) {
        logger.info("update - begin, record={}",record);
        ReturnResult<Boolean> result=null;
        try {
            Task task=new Task();
            BeanUtils.copyProperties(record,task);
            taskMapper.updateByPrimaryKeySelective(task);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.error("update by id error:{} ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("update chedui end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<TaskVo> selectById(Long id) {
        logger.info("selectById - start, id={}",id);
        ReturnResult<TaskVo> result=null;
        try {
            Task task=taskMapper.selectByPrimaryKey(id);
            TaskVo taskVo=null;
            if(task!=null){
                taskVo=new TaskVo();
                BeanUtils.copyProperties(task,taskVo);
            }
            result = ReturnResult.getNewSuccessedInstance(taskVo);
        }
        catch (Exception e){
            logger.info("selectById - error:{}",e);
            result = ReturnResult.getNewFailedInstance();
        }
        logger.info("selectById -  end, result:{}",result);
        return result;
    }

    @Override
    public ReturnResult<Integer> countByParameters(Map<String, Object> params) {
        ReturnResult<Integer> returnResult=ReturnResult.getNewSuccessedInstance(0);
        Integer count=0;
        count = taskMapper.countByCondition(params);
        if(count==null){
            count=0;
        }
        returnResult = ReturnResult.getNewSuccessedInstance(count);
        return returnResult;
    }

    @Override
    public ReturnResult<List<TaskVo>> selectByParameters(Map<String, Object> params) {
        ReturnResult<List<TaskVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<Task> tasks= taskMapper.selectByCondition(params);

        List<TaskVo> taskVos =new ArrayList<>();
        for(Task item: tasks){
            TaskVo taskVo=new TaskVo();
            BeanUtils.copyProperties(item,taskVo);
            taskVos.add(taskVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(taskVos);
        return returnResult;
    }

    @Override
    public ReturnResult<Boolean> batchInsert(List<TaskVo> taskVos) {
        logger.info("batchInsert - start, taskVos:{}", taskVos);

        ReturnResult<Boolean> result=ReturnResult.getNewSuccessedInstance(false);
        try {
            if(taskVos!=null && taskVos.size()>0){
                List<Task> tasks=new ArrayList<>();
                for(TaskVo item: taskVos){
                    Task task = new Task();
                    BeanUtils.copyProperties(item,task);
                    tasks.add(task);
                }
                taskMapper.batchInsert(tasks);
            }
            result=ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.error("batchInsert - error:{}", e);
        }
        logger.info("batchInsert - end, result:{}", result);
        return result;
    }

    @Override
    public ReturnResult<List<TaskVo>> selectByParentIdList(List<Long> ids) {
        logger.info("selectByParentIdList - start, ids:{}", ids);
        ReturnResult<List<TaskVo>> result=ReturnResult.getNewSuccessedInstance();
        List<TaskVo> taskVoList =new ArrayList<>();
        try {
            if(ids!=null && ids.size()>0){
                List<Task> tasks=taskMapper.selectByParentIdList(ids);
                for(Task item: tasks){
                    TaskVo taskVo = new TaskVo();
                    BeanUtils.copyProperties(item,taskVo);
                    taskVoList.add(taskVo);
                }
            }
        }
        catch (Exception e){
            logger.error("selectByParentIdList - error:{}", e);
        }
        result=ReturnResult.getNewSuccessedInstance(taskVoList);
        logger.info("selectByParentIdList - end, result:{}", result);
        return result;
    }

    @Override
    public ReturnResult<Boolean> deleteByIdList(List<Long> ids) {
        logger.info("deleteByIdList - start, ids:{}", ids);
        ReturnResult<Boolean> result=ReturnResult.getNewSuccessedInstance();
        List<Task> taskList =new ArrayList<>();

        List<Long> deleteIdList=new ArrayList<>();

        List<Long> idList=new ArrayList<>();
        idList.addAll(ids);
        deleteIdList.addAll(ids);
        try {
            while (true) {
                if (ids != null && ids.size() > 0) {
                    List<Task> tasks = taskMapper.selectByParentIdList(ids);
                    ids.clear();
                    for (Task item : tasks) {
                        deleteIdList.add(item.getId());
                        ids.add(item.getId());
                    }
                }
                else{
                    break;
                }
            }
            if(deleteIdList!=null && deleteIdList.size()>0){
                taskMapper.deleteByIdList(deleteIdList);
            }
            result=ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.error("deleteByIdList - error:{}", e);
        }
        logger.info("deleteByIdList - end, result:{}", result);
        return result;
    }

    @Override
    public ReturnResult<Integer> countTaskStastics(Map<String, Object> params) {
        logger.info("countTaskStastics - start, params:{}", params);
        ReturnResult<Integer> result = ReturnResult.getNewFailedInstance();
        Integer count=0;
        try {
            count = taskMapper.countTaskStastics(params);
            result = ReturnResult.getNewSuccessedInstance(count);
        }
        catch (Exception e){
            logger.error("countTaskStastics - error:{}", e);
        }
        logger.info("countTaskStastics - result:{}", result);
        return result;
    }

    @Override
    public ReturnResult<List<TaskStasticsVo>> selectTaskStasticsList(Map<String, Object> params) {
        logger.info("selectTaskStasticsList - start, params:{}", params);
        ReturnResult<List<TaskStasticsVo>> result = ReturnResult.getNewFailedInstance();
        List<TaskStasticsVo> taskStasticsVos= new ArrayList<>();
        try {
            //全部数据
            taskStasticsVos = taskMapper.selectTaskStasticsList(params);

            //已完成
            List<TaskStasticsVo> completeVos=new ArrayList<>();
            params.put("isCompleted",2);
            completeVos = taskMapper.selectTaskStasticsList(params);

            Map<Long,TaskStasticsVo> taskStasticsVoMapper=new HashedMap();
            Map<String,TaskStasticsVo> taskTypeStasticsVoMapper=new HashedMap();

            List<Long> idList = new ArrayList<>();
            List<String> typeList = new ArrayList<>();
            for(TaskStasticsVo item: taskStasticsVos){
                if(item.getTargetId()!=null){
                    idList.add(item.getTargetId());
                    taskStasticsVoMapper.put(item.getTargetId(),item);
                }
                else{
                    typeList.add(item.getTargetName());
                    taskTypeStasticsVoMapper.put(item.getTargetName(),item);
                }

            }

            for(TaskStasticsVo taskStasticsVo: completeVos){
                if(taskStasticsVoMapper.get(taskStasticsVo.getTargetId())!=null){
                    taskStasticsVoMapper.get(taskStasticsVo.getTargetId()).setCompleteTotal(taskStasticsVo.getTaskTotal());
                }
                else{
                    if(taskTypeStasticsVoMapper.get(taskStasticsVo.getTargetName())!=null){
                        taskTypeStasticsVoMapper.get(taskStasticsVo.getTargetName()).setCompleteTotal(taskStasticsVo.getTaskTotal());
                    }
                }
            }

            if(idList.size()>0 || typeList.size()>0) {
                Map<String, Object> objParam = new HashedMap();
                objParam.put("staticsType", params.get("staticsType"));
                if(idList.size()>0){
                    objParam.put("idList", idList);
                }
                else if(typeList.size()>0){
                    objParam.put("typeList", typeList);
                }
                objParam.put("groupByName",params.get("groupByName")+",score");

                logger.info("selectTaskStasticsList - info:objParam:{} ", objParam);
                List<TaskScoreVo> taskScoreVos = taskMapper.selectScoreStasticsList(objParam);
                for (TaskScoreVo taskScoreVo : taskScoreVos) {
                    TaskStasticsVo taskStasticsVo = taskStasticsVoMapper.get(taskScoreVo.getTargetId());
                    if(taskStasticsVo==null){
                        taskStasticsVo = taskTypeStasticsVoMapper.get(taskScoreVo.getTargetName());
                    }

                    if (taskScoreVo.getScore() == TaskScoreVo.SCORE_LEVEL_BETTER) {
                        taskStasticsVo.setBetter(taskScoreVo.getTaskTotal());
                    }
                    if (taskScoreVo.getScore() == TaskScoreVo.SCORE_LEVEL_GOOD) {
                        taskStasticsVo.setGood(taskScoreVo.getTaskTotal());
                    }
                    if (taskScoreVo.getScore() == TaskScoreVo.SCORE_LEVEL_MIDDLE) {
                        taskStasticsVo.setMiddle(taskScoreVo.getTaskTotal());
                    }
                    if (taskScoreVo.getScore() == TaskScoreVo.SCORE_LEVEL_WORSE) {
                        taskStasticsVo.setTerriable(taskScoreVo.getTaskTotal());
                    }
                }
            }
        }
        catch (Exception e){
            logger.error("selectTaskStasticsList - error:{}", e);
        }
        result= ReturnResult.getNewSuccessedInstance(taskStasticsVos);
        logger.info("selectTaskStasticsList - result:{}", result);
        return result;
    }

    @Override
    public ReturnResult<Integer> countBySubParameters(Map<String, Object> params) {
        ReturnResult<Integer> returnResult=ReturnResult.getNewSuccessedInstance(0);
        Integer count=0;
        count = taskMapper.countBySubCondition(params);
        if(count==null){
            count=0;
        }
        returnResult = ReturnResult.getNewSuccessedInstance(count);
        return returnResult;
    }

    @Override
    public ReturnResult<List<TaskVo>> selectBySubParameters(Map<String, Object> params) {
        ReturnResult<List<TaskVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<Task> tasks= taskMapper.selectBySubCondition(params);

        List<TaskVo> taskVos =new ArrayList<>();
        for(Task item: tasks){
            TaskVo taskVo=new TaskVo();
            BeanUtils.copyProperties(item,taskVo);
            taskVos.add(taskVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(taskVos);
        return returnResult;
    }
}
