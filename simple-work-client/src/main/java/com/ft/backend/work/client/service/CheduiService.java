package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.ChejianVo;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/10/23.
 */
public interface CheduiService {
    ReturnResult<CheduiVo> add(CheduiVo record);

    ReturnResult<Boolean> deleteById(Long id);

    ReturnResult<Boolean> update(CheduiVo record);

    ReturnResult<Boolean> batchUpdate(List<CheduiVo> records);

    ReturnResult<CheduiVo> selectById(Long id);

    ReturnResult<Integer> countByParameters(Map<String,Object> params);
    ReturnResult<List<CheduiVo>> selectByParameters(Map<String,Object> params);

    ReturnResult<List<CheduiVo>> selectByIdList(List<Long> ids);
}
