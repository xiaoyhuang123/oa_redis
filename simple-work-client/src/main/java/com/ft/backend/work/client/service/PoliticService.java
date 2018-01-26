package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.PoliticVo;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/25.
 */
public interface PoliticService {
    ReturnResult<List<PoliticVo>> selectByCondition(Map<String,Object> params);

    ReturnResult<PoliticVo> selectById(Long id);
}
