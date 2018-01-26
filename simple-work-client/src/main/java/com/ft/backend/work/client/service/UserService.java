package com.ft.backend.work.client.service;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2016/12/3.
 */
public interface UserService {
    ReturnResult<UserVo> add(UserVo userVo);

    ReturnResult<UserVo> update(UserVo userVo);

    ReturnResult<UserVo> getById(Long id);

    ReturnResult<UserVo> getByWorkerId(Long workerId);

    ReturnResult<Boolean> deleteById(Long id);

    ReturnResult<List<UserVo>> getByParameters(Map<String,Object> params);

    ReturnResult<UserVo> getByUsernameAndPassword(String username,String password);


     ReturnResult<Integer> countByParameters(Map<String, Object> params);

   ReturnResult<List<UserVo>> selectByParameters(Map<String, Object> params);
}
