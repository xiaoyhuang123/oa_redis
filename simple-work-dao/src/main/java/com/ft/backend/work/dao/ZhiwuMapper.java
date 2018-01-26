package com.ft.backend.work.dao;

import com.ft.backend.work.model.Zhiwu;

import java.util.List;
import java.util.Map;

public interface ZhiwuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_zhiwu
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_zhiwu
     *
     * @mbggenerated
     */
    int insert(Zhiwu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_zhiwu
     *
     * @mbggenerated
     */
    int insertSelective(Zhiwu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_zhiwu
     *
     * @mbggenerated
     */
    Zhiwu selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_zhiwu
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Zhiwu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_zhiwu
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Zhiwu record);

    List<Zhiwu> selectByCondition(Map<String,Object> params);

    int countByCondition(Map<String,Object> params);

}