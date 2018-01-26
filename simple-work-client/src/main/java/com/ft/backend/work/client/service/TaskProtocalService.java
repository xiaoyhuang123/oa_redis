package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.TaskProtocalVo;

/**
 * Created by huanghongyi on 2017/11/9.
 */
public interface TaskProtocalService {

    ReturnResult<TaskProtocalVo> add(TaskProtocalVo taskProtocalVo);

    ReturnResult<Boolean> deleteById(Long id);

    ReturnResult<TaskProtocalVo> update(TaskProtocalVo workshopVo);

    ReturnResult<TaskProtocalVo> selectDetailById(Long id);

}
