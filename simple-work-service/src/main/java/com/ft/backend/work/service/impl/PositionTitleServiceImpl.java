package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.PositionTitleService;
import com.ft.backend.work.client.vo.PositionTitleVo;
import com.ft.backend.work.dao.PositionTitleMapper;
import com.ft.backend.work.model.PositionTitle;
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
@Service("positionTitleService")
public class PositionTitleServiceImpl implements PositionTitleService{
    private Logger logger= LogManager.getLogger(PositionTitleService.class);

    @Resource
    private PositionTitleMapper positionTitleMapper;

    @Override
    public ReturnResult<List<PositionTitleVo>> selectByCondition(Map<String, Object> params) {
        ReturnResult<List<PositionTitleVo>> returnResult = ReturnResult.getNewSuccessedInstance();
        List<PositionTitleVo> res =new ArrayList<>();
        logger.info("selectByCondition - info, params:{}", params);
        try {
            List<PositionTitle> records =  positionTitleMapper.selectByCondition(new HashedMap());
            for(PositionTitle item: records){
                PositionTitleVo it =new PositionTitleVo();
                it.setId(item.getId());
                it.setPositionTitleName(item.getPositionTitleName());
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
    public ReturnResult<PositionTitleVo> selectById(Long id) {
        ReturnResult<PositionTitleVo> result = ReturnResult.getNewFailedInstance();
        logger.info("selectById - start, id:{}", id);
        PositionTitleVo positionTitleVo =null;
        try {
            PositionTitle positionTitle= positionTitleMapper.selectByPrimaryKey(id);
            positionTitleVo = new PositionTitleVo();

            BeanUtils.copyProperties(positionTitle, positionTitleVo);
        }
        catch (Exception e){
            logger.error("selectById - error:{}", e);
        }
        result= ReturnResult.getNewSuccessedInstance(positionTitleVo);
        logger.info("selectById - end, result:{}", result);
        return result;
    }
}
