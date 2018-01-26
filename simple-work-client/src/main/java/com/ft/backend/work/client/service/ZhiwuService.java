package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.ZhiwuVo;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/13.
 */
public interface ZhiwuService {

    ReturnResult<List<ZhiwuVo>> selectByCondition(Map<String,Object> params);

    ReturnResult<ZhiwuVo> selectById(Long id);
}
