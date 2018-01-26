package com.ft.backend.work.dao;

import com.ft.backend.work.model.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Wonderful on 2017/11/15.
 */
public interface FileMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);

    int countByCondition(Map<String,Object> params);
    List<File> selectByCondition(Map<String,Object> params);

    Long deleteById(Long id);

    int deleteByTaskIds(List<Long> taskIds);

    int updateFile(File file);
    List<File> selectByIdList(List<Long> ids);
}
