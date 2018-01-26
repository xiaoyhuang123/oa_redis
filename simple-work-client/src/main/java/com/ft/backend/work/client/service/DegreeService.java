package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.DegreeVo;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/26.
 */
public interface DegreeService {
    ReturnResult<List<DegreeVo>> selectByCondition(Map<String,Object> params);

    ReturnResult<DegreeVo> selectById(Long id);
}
