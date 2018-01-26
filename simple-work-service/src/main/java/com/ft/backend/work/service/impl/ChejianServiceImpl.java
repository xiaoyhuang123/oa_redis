package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.QueryCondition.ChejianQueryCondition;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.ChejianService;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.ChejianVo;
import com.ft.backend.work.dao.ChejianMapper;
import com.ft.backend.work.model.Chedui;
import com.ft.backend.work.model.Chejian;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanghongyi on 2016/11/8.
 */
@Service("chejianService")
public class ChejianServiceImpl implements ChejianService {

    private Logger logger=LogManager.getLogger(ChejianServiceImpl.class);

    @Autowired
    private ChejianMapper chejianMapper;

    @Override
    public ReturnResult<ChejianVo> add(ChejianVo chejianVo) {

        logger.info("add workshop begin, chejianVo={}",chejianVo);

        ReturnResult<ChejianVo> result=null;
        try {
            Chejian chejian=new Chejian();
            BeanUtils.copyProperties(chejianVo,chejian);

            chejianMapper.insert(chejian);
            chejianVo.setId(chejian.getId());

            result = ReturnResult.getNewSuccessedInstance(chejianVo);
        }
        catch (Exception e){
            logger.info("add workshop by id error ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("add workshop end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<Boolean> deleteById(Long id) {
        logger.info("delete workshop  by id begin, id={}",id);
        ReturnResult<Boolean> result=null;
        try {
            chejianMapper.deleteById(id);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.info("delete workshop by id error ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("delete workshop by id end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<Boolean> update(ChejianVo chejianVo) {
        logger.info("update chejian begin, chejianVo={}",chejianVo);
        ReturnResult<Boolean> result=null;
        try {
            Chejian chejian=new Chejian();
            BeanUtils.copyProperties(chejianVo,chejian);
            chejianMapper.updateByPrimaryKeySelective(chejian);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.info("update chejian by id error ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("update chejian end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<ChejianVo> selectById(Long id) {
        logger.info("select chejian by id begin, id={}",id);
        ReturnResult<ChejianVo> result=null;
        try {
            Chejian chejian=chejianMapper.selectByPrimaryKey(id);
            ChejianVo chejianVo=new ChejianVo();
            BeanUtils.copyProperties(chejian,chejianVo);
            result = ReturnResult.getNewSuccessedInstance(chejianVo);
        }
        catch (Exception e){
            logger.info("select workshop by id error ",e);
            result = ReturnResult.getNewFailedInstance();
        }
        logger.info("select workshop end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<List<ChejianVo>> selectByCondition(ChejianQueryCondition condition) {

        logger.info("selectByCondition workshop begin, condition={}",condition);
        ReturnResult<List<ChejianVo>> result=null;

        try {
            List<ChejianVo> chejianVos=new ArrayList<>();

            List<Chejian> chejianList=chejianMapper.selectByCondition(condition);
            for(Chejian chejian: chejianList){
                ChejianVo chejianVo=new ChejianVo();
                BeanUtils.copyProperties(chejian,chejianVo);
                chejianVos.add(chejianVo);
            }
            result = ReturnResult.getNewSuccessedInstance(chejianVos);
        }
        catch (Exception e){
            logger.info("selectByCondition workshop error ",e);
            result = ReturnResult.getNewFailedInstance();
        }
        logger.info("selectByCondition workshop end, result={}",result);
        return result;
    }

    @Override
    public List<ChejianVo> selectByCondition1(ChejianQueryCondition condition) {

        logger.info("selectByCondition workshop begin, condition={}",condition);
        List<ChejianVo> result=new ArrayList<>();

        try {

            List<Chejian> chejianList=chejianMapper.selectByCondition(condition);
            for(Chejian chejian: chejianList){
                ChejianVo chejianVo=new ChejianVo();
                BeanUtils.copyProperties(chejian,chejianVo);
                result.add(chejianVo);
            }
        }
        catch (Exception e){
            logger.info("selectByCondition1 workshop error ",e);
        }
        logger.info("selectByCondition1 workshop end, result={}",result);
        return result;
    }

    @Override
    public int countByCondition(ChejianQueryCondition condition) {
        int n=0;
        n=chejianMapper.countByCondition(condition);
        return n;
    }

    @Override
    public ReturnResult<List<ChejianVo>> selectByIdList(List<Long> ids) {

        logger.info("selectByIdList - start, ids:{}", ids);
        ReturnResult<List<ChejianVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<Chejian> chejians = new ArrayList<>();
        List<ChejianVo> chejianVos =new ArrayList<>();
        if(ids==null || ids.size()<1){
            returnResult= ReturnResult.getNewSuccessedInstance(chejianVos);
            return  returnResult;
        }

        try {

            chejians= chejianMapper.selectByIdList(ids);


            for(Chejian item: chejians){
                ChejianVo chejianVo=new ChejianVo();
                BeanUtils.copyProperties(item,chejianVo);
                chejianVos.add(chejianVo);
            }
            returnResult = ReturnResult.getNewSuccessedInstance(chejianVos);
        }
        catch (Exception e){
            logger.error("selectByIdList - error:{}", e);
        }

        logger.info("selectByIdList - end, returnResult:{}", returnResult);
        return returnResult;
    }
}
