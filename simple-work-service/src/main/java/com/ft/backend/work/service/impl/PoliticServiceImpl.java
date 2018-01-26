package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.PoliticService;
import com.ft.backend.work.client.vo.PoliticVo;
import com.ft.backend.work.dao.PoliticMapper;
import com.ft.backend.work.model.Politic;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/25.
 */
@Service("politicService")
public class PoliticServiceImpl implements PoliticService{
    private Logger logger= LogManager.getLogger(PoliticService.class);

    @Resource
    private PoliticMapper politicMapper;

    @Override
    public ReturnResult<List<PoliticVo>> selectByCondition(Map<String, Object> params) {
        ReturnResult<List<PoliticVo>> returnResult = ReturnResult.getNewSuccessedInstance();
        List<PoliticVo> res =new ArrayList<>();
        logger.info("selectByCondition - info, params:{}", params);
        try {
            List<Politic> records =  politicMapper.selectByCondition(new HashedMap());
            for(Politic item: records){
                PoliticVo it =new PoliticVo();
                it.setId(item.getId());
                it.setPoliticname(item.getPoliticname());
                res.add(it);
            }
        }
        catch (Exception e){
            logger.error("selectByCondition - error:{}", e);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(res);
        logger.info("selectByCondition - end, returnResult:{}", returnResult);
        return returnResult;
    }

    @Override
    public ReturnResult<PoliticVo> selectById(Long id) {
        ReturnResult<PoliticVo> result = ReturnResult.getNewFailedInstance();
        logger.info("selectById - start, id:{}", id);
        PoliticVo politicVo =null;
        try {
            Politic politic= politicMapper.selectByPrimaryKey(id);
            politicVo = new PoliticVo();

            BeanUtils.copyProperties(politic, politicVo);
        }
        catch (Exception e){
            logger.error("selectById - error:{}", e);
        }
        result= ReturnResult.getNewSuccessedInstance(politicVo);
        logger.info("selectById - end, result:{}", result);
        return result;
    }
}
