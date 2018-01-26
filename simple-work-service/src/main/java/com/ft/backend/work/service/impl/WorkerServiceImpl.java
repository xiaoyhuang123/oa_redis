package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.WorkerService;
import com.ft.backend.work.client.vo.WorkerVo;
import com.ft.backend.work.dao.WorkerMapper;
import com.ft.backend.work.model.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/4.
 */
@Service("workerService")
public class WorkerServiceImpl implements WorkerService{

    private Logger logger= LogManager.getLogger(WorkerServiceImpl.class);

    @Resource
    private WorkerMapper workerMapper;

    @Override
    public ReturnResult<WorkerVo> add(WorkerVo record) {

        ReturnResult<WorkerVo> returnResult=ReturnResult.getNewSuccessedInstance();
        Worker worker=new Worker();
        BeanUtils.copyProperties(record,worker);
        workerMapper.insertSelective(worker);
        record.setId(worker.getId());
        returnResult = ReturnResult.getNewSuccessedInstance(record);
        return returnResult;
    }

    @Override
    public ReturnResult<Boolean> deleteById(Long id) {
        logger.info("deleteById - info,  id:{}",id);
        ReturnResult<Boolean> result=null;
        try {
            workerMapper.deleteById(id);
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
    public ReturnResult<Boolean> update(WorkerVo record) {
        logger.info("update worker begin, record={}",record);
        ReturnResult<Boolean> result=null;
        try {
            Worker worker=new Worker();
            BeanUtils.copyProperties(record,worker);
            workerMapper.updateByPrimaryKeySelective(worker);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.info("update worker by id error ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("update worker end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<WorkerVo> selectById(Long id) {
        logger.info("selectById - start, id={}",id);
        ReturnResult<WorkerVo> result=ReturnResult.getNewFailedInstance();
        WorkerVo workerVo=null;

        if(id==null){
            return result;
        }

        try {
            Worker worker=workerMapper.selectByPrimaryKey(id);
            if(worker!=null){
                workerVo=new WorkerVo();
                BeanUtils.copyProperties(worker,workerVo);

                result = ReturnResult.getNewSuccessedInstance(workerVo);
            }
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
        Integer count= workerMapper.countByCondition(params);
        returnResult = ReturnResult.getNewSuccessedInstance(count);
        return returnResult;
    }

    @Override
    public ReturnResult<List<WorkerVo>> selectByParameters(Map<String, Object> params) {
        ReturnResult<List<WorkerVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<Worker> workers= workerMapper.selectByCondition(params);

        List<WorkerVo> workerVos =new ArrayList<>();
        for(Worker item: workers){
            WorkerVo workerVo=new WorkerVo();
            BeanUtils.copyProperties(item,workerVo);
            workerVos.add(workerVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(workerVos);
        return returnResult;
    }

    @Override
    public ReturnResult<List<WorkerVo>> selectByIdList(List<Long> ids) {
        ReturnResult<List<WorkerVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<Worker> workers =new ArrayList<>();
        if(ids!=null && ids.size()>0){
            workers = workerMapper.selectByIdList(ids);
        }

        List<WorkerVo> workerVos =new ArrayList<>();
        for(Worker item: workers){
            WorkerVo workerVo=new WorkerVo();
            BeanUtils.copyProperties(item,workerVo);
            workerVos.add(workerVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(workerVos);
        return returnResult;
    }
}
