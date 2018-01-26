package com.ft.backend.work.client.service;

import com.ft.backend.work.client.QueryCondition.GonggaoQueryCondition;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.GonggaoVo;

import java.util.List;

/**
 * Created by huanghongyi on 2017/10/29.
 */
public interface GonggaoService {
    ReturnResult<GonggaoVo> add(GonggaoVo gonggaoVo);

    ReturnResult<Boolean> deleteById(Long id);

    ReturnResult<Boolean> update(GonggaoVo gonggaoVo);

    ReturnResult<GonggaoVo> selectById(Long id);

    ReturnResult<List<GonggaoVo>> selectByCondition(GonggaoQueryCondition condition);

    ReturnResult<Integer> countByCondition(GonggaoQueryCondition condition);
}
