package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.DegreeService;
import com.ft.backend.work.client.vo.DegreeVo;
import com.ft.backend.work.dao.XueliMapper;
import com.ft.backend.work.model.Xueli;
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
 * Created by huanghongyi on 2017/11/26.
 */
@Service("degreeService")
public class DegreeServiceImpl implements DegreeService {
    private Logger logger= LogManager.getLogger(DegreeService.class);

    @Resource
    private XueliMapper xueliMapper;

    @Override
    public ReturnResult<List<DegreeVo>> selectByCondition(Map<String, Object> params) {
        ReturnResult<List<DegreeVo>> returnResult = ReturnResult.getNewSuccessedInstance();
        List<DegreeVo> res =new ArrayList<>();
        logger.info("selectByCondition - info, params:{}", params);
        try {
            List<Xueli> records =  xueliMapper.selectByCondition(new HashedMap());
            for(Xueli item: records){
                DegreeVo it =new DegreeVo();
                it.setId(item.getId());
                it.setDegreename(item.getDegreename());
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
    public ReturnResult<DegreeVo> selectById(Long id) {
        ReturnResult<DegreeVo> result = ReturnResult.getNewFailedInstance();
        logger.info("selectById - start, id:{}", id);
        DegreeVo degreeVo =null;
        try {
            Xueli xueli= xueliMapper.selectByPrimaryKey(id);
            degreeVo= new DegreeVo();

            BeanUtils.copyProperties(xueli, degreeVo);
        }
        catch (Exception e){
            logger.error("selectById - error:{}", e);
        }
        result= ReturnResult.getNewSuccessedInstance(degreeVo);
        logger.info("selectById - end, result:{}", result);
        return result;
    }
}
