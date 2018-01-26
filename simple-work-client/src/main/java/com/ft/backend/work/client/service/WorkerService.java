package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.ChejianVo;
import com.ft.backend.work.client.vo.WorkerVo;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/10/23.
 */
public interface WorkerService {
    ReturnResult<WorkerVo> add(WorkerVo record);

    ReturnResult<Boolean> deleteById(Long id);

    ReturnResult<Boolean> update(WorkerVo record);

    ReturnResult<WorkerVo> selectById(Long id);

    ReturnResult<Integer> countByParameters(Map<String,Object> params);
    ReturnResult<List<WorkerVo>> selectByParameters(Map<String,Object> params);

    ReturnResult<List<WorkerVo>> selectByIdList(List<Long> ids);
}
