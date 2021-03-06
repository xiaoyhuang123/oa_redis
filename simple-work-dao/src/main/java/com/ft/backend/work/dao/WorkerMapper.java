package com.ft.backend.work.dao;

import com.ft.backend.work.model.Worker;

import java.util.List;
import java.util.Map;

public interface WorkerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbggenerated
     */
    int insert(Worker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbggenerated
     */
    int insertSelective(Worker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbggenerated
     */
    Worker selectByPrimaryKey(Long id);
    Worker selectByWorkId(String workId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Worker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(Worker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Worker record);

    int countByCondition(Map<String,Object> params);
    List<Worker> selectByCondition(Map<String,Object> params);

    Long deleteById(Long id);

    List<Worker> selectByIdList(List<Long> ids);
}