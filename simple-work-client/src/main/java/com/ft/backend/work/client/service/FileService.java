package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.FileVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Wonderful on 2017/11/15.
 */
public interface FileService {
    ReturnResult<FileVo> add(FileVo record);

    ReturnResult<Boolean> deleteById(Long id);
    ReturnResult<Boolean> deleteByTaskIds(List<Long> taskIds);

    ReturnResult<Boolean> update(FileVo record);

    ReturnResult<Boolean> batchUpdate(List<FileVo> records);

    ReturnResult<FileVo> selectById(Long id);

    ReturnResult<Integer> countByParameters(Map<String,Object> params);
    ReturnResult<List<FileVo>> selectByParameters(Map<String,Object> params);

    ReturnResult<List<FileVo>> selectByIdList(List<Long> ids);
}
