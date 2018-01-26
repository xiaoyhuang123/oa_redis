package com.ft.backend.work.client.service;

import com.ft.backend.work.client.QueryCondition.ChejianQueryCondition;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.ChejianVo;

import java.util.List;

/**
 * Created by huanghongyi on 2016/11/8.
 */
public interface ChejianService {

    ReturnResult<ChejianVo> add(ChejianVo workshopVo);

    ReturnResult<Boolean> deleteById(Long id);

    ReturnResult<Boolean> update(ChejianVo workshopVo);

    ReturnResult<ChejianVo> selectById(Long id);

    ReturnResult<List<ChejianVo>> selectByCondition(ChejianQueryCondition condition);

    List<ChejianVo> selectByCondition1(ChejianQueryCondition condition);

    int countByCondition(ChejianQueryCondition condition);

    ReturnResult<List<ChejianVo>> selectByIdList(List<Long> ids);
}
