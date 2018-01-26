package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.*;
import com.ft.backend.work.client.vo.TaskProtocalVo;
import com.ft.backend.work.client.vo.TaskVo;
import com.ft.backend.work.dao.CheduiMapper;
import com.ft.backend.work.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by huanghongyi on 2017/11/9.
 */
@Service("TaskProtocalService")
public class TaskProtocalServiceImpl implements TaskProtocalService{

    private Logger logger= LogManager.getLogger(TaskProtocalServiceImpl.class);

    @Resource
    private TaskService taskService;

    @Resource
    private ChejianService chejianService;

    @Resource
    private CheduiService cheduiService;

    @Resource
    private WorkerService workerService;

    @Override
    public ReturnResult<TaskProtocalVo> add(TaskProtocalVo taskProtocalVo) {

        logger.info("add - start, taskProtocalVo:{}", taskProtocalVo);
        TaskVo task=new TaskVo();

        BeanUtils.copyProperties(taskProtocalVo,task);
        ReturnResult<TaskVo> res = taskService.add(task);
        if(res!=null){
            task.setId(res.getResult().getId());
        }

        List<TaskVo> insertList=new ArrayList<>();

        List<TaskVo> fList=taskProtocalVo.getFuzerenList();
        insertList.addAll(fList);
        List<TaskVo> lList=taskProtocalVo.getLuoshirenList();
        insertList.addAll(lList);

        for(TaskVo item: insertList){
           item.setParentId(task.getId());
        }

        try {
            taskService.batchInsert(insertList);
        }
        catch (Exception e){
            logger.error("add - error:{}", e);
        }
        return selectDetailById(task.getId());
    }

    @Override
    public ReturnResult<Boolean> deleteById(Long id) {
        logger.info("deleteById - start, id:{}", id);

        ReturnResult<Boolean> reult=ReturnResult.getNewSuccessedInstance();
        List<Long> deleteIds=new ArrayList<>();
        deleteIds.add(id);

        List<Long> ids=new ArrayList<>();
        ids.add(id);
        while(true){

            List<TaskVo>  taskVos = new ArrayList<>();
            ReturnResult<List<TaskVo>> res=taskService.selectByParentIdList(ids);
            if(res!=null && res.getResult()!=null && res.getResult().size()>0){
                taskVos=res.getResult();
            }
            if( taskVos==null || taskVos.size()<1){
                break;
            }
            ids.clear();
            for(TaskVo item: taskVos){
                ids.add(item.getId());
                deleteIds.add(item.getId());
            }
        }
        try {
            if(deleteIds!=null && deleteIds.size()>0){
                taskService.deleteByIdList(deleteIds);
            }
            reult= ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.error("deleteById -error:{}", e);
            reult= ReturnResult.getNewSuccessedInstance(false);
        }
        logger.info("deleteById - end, result:{}", reult);
        return reult;
    }

    @Override
    public ReturnResult<TaskProtocalVo> update(TaskProtocalVo taskProtocalVo) {
        logger.info("update - start, taskProtocalVo:{}", taskProtocalVo);
        List<TaskVo> insertList=new ArrayList<>();
        List<TaskVo> deleteList=new ArrayList<>();

        TaskVo task=new TaskVo();
        BeanUtils.copyProperties(taskProtocalVo,task);
        ReturnResult<Boolean> res = taskService.update(task);

        ReturnResult<List<TaskVo>> taskRes = taskService.selectByParentIdList(Arrays.asList(task.getId()));
        List<TaskVo> taskVos = taskRes.getResult();
        Map<String,TaskVo> oriFSet=new HashMap<>();
        Map<String,TaskVo> oriLSet=new HashMap<>();
        for(TaskVo taskVo: taskVos){
            if(taskVo.getRoleType()==1){
                if(taskVo.getTargetType()==1){
                    oriFSet.put("w"+taskVo.getWorkerId(),taskVo);
                }
                else  if(taskVo.getTargetType()==2){
                    oriFSet.put("d"+taskVo.getCheduiId(),taskVo);
                }
                else  if(taskVo.getTargetType()==3){
                    oriFSet.put("j"+taskVo.getChejianId(),taskVo);
                }
            }
            else  if(taskVo.getRoleType()==2){
                if(taskVo.getTargetType()==1){
                    oriLSet.put("w"+taskVo.getWorkerId(),taskVo);
                }
                else  if(taskVo.getTargetType()==2){
                    oriLSet.put("d"+taskVo.getCheduiId(),taskVo);
                }
                else  if(taskVo.getTargetType()==3){
                    oriLSet.put("j"+taskVo.getChejianId(),taskVo);
                }
            }
        }

        List<TaskVo> fList=taskProtocalVo.getFuzerenList();
        List<TaskVo> lList=taskProtocalVo.getLuoshirenList();
        fList.addAll(lList);
        Map<String,TaskVo> fSet=new HashMap<>();
        Map<String,TaskVo> lSet=new HashMap<>();
        for(TaskVo taskVo: fList){
            String pId=null;
            if(taskVo.getRoleType()==1){
                if(taskVo.getTargetType()==1){
                    pId="w"+taskVo.getWorkerId();
                }
                else  if(taskVo.getTargetType()==2){
                    pId="d"+taskVo.getCheduiId();
                }
                else  if(taskVo.getTargetType()==3){
                    pId="j"+taskVo.getChejianId();
                }
                fSet.put(pId,taskVo);
                taskVo.setParentId(taskProtocalVo.getId());
                if(oriFSet.get(pId)==null){
                    insertList.add(taskVo);
                }
            }
            else  if(taskVo.getRoleType()==2){
                if(taskVo.getTargetType()==1){
                    pId="w"+taskVo.getWorkerId();
                }
                else  if(taskVo.getTargetType()==2){
                    pId="d"+taskVo.getCheduiId();
                }
                else  if(taskVo.getTargetType()==3){
                    pId="j"+taskVo.getChejianId();
                }
                lSet.put(pId, taskVo);
                if(oriLSet.get(pId)==null){
                    insertList.add(taskVo);
                }
            }
        }

        try {
            if(insertList!=null && insertList.size()>0){
                logger.info("update - info, insert records:{}", insertList);
                for(TaskVo taskVo: insertList){
                    taskVo.setParentId(task.getId());
                    taskVo.setIsDeleted(0);
                    taskVo.setIsCompleted(0);
                    taskVo.setIsReceived(0);
                    taskVo.setCreateTime(new Date());
                }
                taskService.batchInsert(insertList);
            }
            if(deleteList!=null && deleteList.size()>0){
                List<Long> ids =new ArrayList<>();
                for(TaskVo taskVo: deleteList){
                    ids.add(taskVo.getId());
                }
                logger.info("update - info, delete ids:{}", ids);
                taskService.deleteByIdList(ids);
            }
        }
        catch (Exception e){
            logger.error("update - error:{}", e);
        }
        return selectDetailById(task.getId());
    }

    @Override
    public ReturnResult<TaskProtocalVo> selectDetailById(Long id) {
        logger.info("selectDetailById -start, id:{}", id);
        ReturnResult<TaskProtocalVo> result=ReturnResult.getNewSuccessedInstance();
        try{
            TaskProtocalVo taskProtocalVo=new TaskProtocalVo();
            taskProtocalVo.setFuzerenList(new ArrayList<TaskVo>());
            taskProtocalVo.setLuoshirenList(new ArrayList<TaskVo>());

            ReturnResult<TaskVo> taskVoReturnResult = taskService.selectById(id);
            BeanUtils.copyProperties(taskVoReturnResult.getResult(),taskProtocalVo);

            ReturnResult<List<TaskVo>> listReturnResult = taskService.selectByParentIdList(Arrays.asList(id));

            List<TaskVo> taskVos = listReturnResult.getResult();
            for(TaskVo taskVo: taskVos){
                if(taskVo.getRoleType()== TaskVo.WORKER_TYPE_FUZEREN){
                    taskProtocalVo.getFuzerenList().add(taskVo);
                }
                else if(taskVo.getRoleType()== TaskVo.WORKER_TYPE_LUOSHIREN){
                    taskProtocalVo.getLuoshirenList().add(taskVo);
                }
            }
            result = ReturnResult.getNewSuccessedInstance(taskProtocalVo);
        }
        catch (Exception e){
            logger.error("selectDetailById -error:{}", e);
            result = ReturnResult.getNewFailedInstance();
        }
      logger.info("selectDetailById - end, result:{}", result);
        return result;
    }
}
