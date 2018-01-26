package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.PositionTitleVo;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/25.
 */
public interface PositionTitleService {
    ReturnResult<List<PositionTitleVo>> selectByCondition(Map<String,Object> params);

    ReturnResult<PositionTitleVo> selectById(Long id);
}
