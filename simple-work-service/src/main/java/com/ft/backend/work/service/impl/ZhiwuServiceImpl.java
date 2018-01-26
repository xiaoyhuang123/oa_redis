package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.ZhiwuService;
import com.ft.backend.work.client.vo.ZhiwuVo;
import com.ft.backend.work.dao.ZhiwuMapper;
import com.ft.backend.work.model.Zhiwu;
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
 * Created by huanghongyi on 2017/11/13.
 */
@Service("zhiwuService")
public class ZhiwuServiceImpl implements ZhiwuService{

    private Logger logger= LogManager.getLogger(ZhiwuService.class);

    @Resource
    private ZhiwuMapper zhiwuMapper;

    @Override
    public ReturnResult<List<ZhiwuVo>> selectByCondition(Map<String, Object> params) {
        ReturnResult<List<ZhiwuVo>> returnResult = ReturnResult.getNewSuccessedInstance();
        List<ZhiwuVo> zhiwuVos =new ArrayList<>();
        logger.info("selectByCondition - info, params:{}", params);
        try {
            List<Zhiwu> zhiwus =  zhiwuMapper.selectByCondition(new HashedMap());
            for(Zhiwu item: zhiwus){
                ZhiwuVo zhiwuVo =new ZhiwuVo();
                zhiwuVo.setId(item.getId());
                zhiwuVo.setName(item.getName());
                zhiwuVos.add(zhiwuVo);
            }
        }
        catch (Exception e){
            logger.error("selectByCondition - error:{}", e);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(zhiwuVos);
        logger.info("selectByCondition - end, returnResult:{}", returnResult);
        return returnResult;
    }

    @Override
    public ReturnResult<ZhiwuVo> selectById(Long id) {
        ReturnResult<ZhiwuVo> result = ReturnResult.getNewFailedInstance();
        logger.info("selectById - start, id:{}", id);
        ZhiwuVo zhiwuVo =null;
        try {
            Zhiwu zhiwu = zhiwuMapper.selectByPrimaryKey(id);
            zhiwuVo = new ZhiwuVo();

            BeanUtils.copyProperties(zhiwu, zhiwuVo);
        }
        catch (Exception e){
            logger.error("selectById - error:{}", e);
        }
        result= ReturnResult.getNewSuccessedInstance(zhiwuVo);
        logger.info("selectById - end, result:{}", result);
        return result;
    }
}
