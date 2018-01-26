package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.CheduiService;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.ChejianVo;
import com.ft.backend.work.dao.CheduiMapper;
import com.ft.backend.work.model.Chedui;
import com.ft.backend.work.model.Chejian;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/3.
 */
@Service("cheduiService")
public class CheduiServiceImpl implements CheduiService{

    private Logger logger= LogManager.getLogger(CheduiServiceImpl.class);

    @Resource
    private CheduiMapper cheduiMapper;

    @Override
    public ReturnResult<CheduiVo> add(CheduiVo record) {

        ReturnResult<CheduiVo> cheduiVoReturnResult=ReturnResult.getNewSuccessedInstance();
        Chedui chedui=new Chedui();
        BeanUtils.copyProperties(record,chedui);

        cheduiMapper.insert(chedui);
        record.setId(chedui.getId());

        cheduiVoReturnResult = ReturnResult.getNewSuccessedInstance(record);
        return cheduiVoReturnResult;
    }

    @Override
    public ReturnResult<Boolean> deleteById(Long id) {
        logger.info("deleteById - info,  id:{}",id);
        ReturnResult<Boolean> result=null;
        try {
            cheduiMapper.deleteById(id);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.info("deleteById - error:{} ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("deleteById - end, result:{}",result);
        return result;
    }

    @Override
    public ReturnResult<Boolean> batchUpdate(List<CheduiVo> records) {
        return null;
    }

    @Override
    public ReturnResult<Boolean> update(CheduiVo record) {
        logger.info("update chedui begin, record={}",record);
        ReturnResult<Boolean> result=null;
        try {
            Chedui chedui=new Chedui();
            BeanUtils.copyProperties(record,chedui);
            cheduiMapper.updateByPrimaryKeySelective(chedui);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.info("update chedui by id error ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("update chedui end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<CheduiVo> selectById(Long id) {
        logger.info("selectById - start, id={}",id);
        ReturnResult<CheduiVo> result=null;
        try {
            Chedui chedui=cheduiMapper.selectByPrimaryKey(id);
            CheduiVo cheduiVo=new CheduiVo();
            BeanUtils.copyProperties(chedui,cheduiVo);
            result = ReturnResult.getNewSuccessedInstance(cheduiVo);
        }
        catch (Exception e){
            logger.info("selectById - error:{}",e);
            result = ReturnResult.getNewFailedInstance();
        }
        logger.info("selectById -  end, result:{}",result);
        return result;
    }

    @Override
    public ReturnResult<Integer> countByParameters(Map<String, Object> params) {
        ReturnResult<Integer> returnResult=ReturnResult.getNewSuccessedInstance(0);
        Integer count= cheduiMapper.countByCondition(params);
        returnResult = ReturnResult.getNewSuccessedInstance(count);
        return returnResult;
    }

    @Override
    public ReturnResult<List<CheduiVo>> selectByParameters(Map<String, Object> params) {

        logger.info("selectByParameters - start, params:{}", params);
        ReturnResult<List<CheduiVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<Chedui> cheduis= cheduiMapper.selectByCondition(params);

        List<CheduiVo> cheduiVoList =new ArrayList<>();
        for(Chedui item: cheduis){
            CheduiVo cheduiVo=new CheduiVo();
            BeanUtils.copyProperties(item,cheduiVo);
            cheduiVoList.add(cheduiVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(cheduiVoList);
        logger.info("selectByParameters - end, result:{}", returnResult);
        return returnResult;
    }

    @Override
    public ReturnResult<List<CheduiVo>> selectByIdList(List<Long> ids) {
        ReturnResult<List<CheduiVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<Chedui> cheduis= cheduiMapper.selectByIdList(ids);

        List<CheduiVo> cheduiVoList =new ArrayList<>();
        for(Chedui item: cheduis){
            CheduiVo cheduiVo=new CheduiVo();
            BeanUtils.copyProperties(item,cheduiVo);
            cheduiVoList.add(cheduiVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(cheduiVoList);
        return returnResult;
    }
}
