package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.TaskStasticsVo;
import com.ft.backend.work.client.vo.TaskVo;
import com.ft.backend.work.client.vo.WorkerVo;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/10/23.
 */
public interface TaskService {

    ReturnResult<TaskVo> add(TaskVo record);

    ReturnResult<Boolean> deleteById(Long id);

    ReturnResult<Boolean> update(TaskVo record);

    ReturnResult<Boolean> updateWithNullParams(Map<String,Object> params);

    ReturnResult<TaskVo> selectById(Long id);

    ReturnResult<Integer> countByParameters(Map<String,Object> params);
    ReturnResult<List<TaskVo>> selectByParameters(Map<String,Object> params);

    ReturnResult<Boolean> batchInsert(List<TaskVo> taskVos);
    ReturnResult<List<TaskVo>> selectByParentIdList(List<Long> ids);
    ReturnResult<Boolean> deleteByIdList(List<Long> ids);


    ReturnResult<Integer> countTaskStastics(Map<String,Object> params);
    ReturnResult<List<TaskStasticsVo>> selectTaskStasticsList(Map<String,Object> params);

    ReturnResult<Integer> countBySubParameters(Map<String,Object> params);
    ReturnResult<List<TaskVo>> selectBySubParameters(Map<String,Object> params);

}
