package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.QueryCondition.GonggaoQueryCondition;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.GonggaoService;
import com.ft.backend.work.client.vo.GonggaoVo;
import com.ft.backend.work.dao.GonggaoMapper;
import com.ft.backend.work.model.Gonggao;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanghongyi on 2017/10/29.
 */
@Service("gonggaoService")
public class GonggaoServiceImpl implements GonggaoService{

    @Resource
    private GonggaoMapper gonggaoMapper;

    @Override
    public ReturnResult<GonggaoVo> add(GonggaoVo gonggaoVo) {

        ReturnResult<GonggaoVo> returnResult=ReturnResult.getNewSuccessedInstance();
        Gonggao record =new Gonggao();
        BeanUtils.copyProperties(gonggaoVo,record);
        gonggaoMapper.insert(record);
        gonggaoVo.setId(record.getId());
        returnResult = ReturnResult.getNewSuccessedInstance(gonggaoVo);
        return returnResult;
    }

    @Override
    public ReturnResult<Boolean> deleteById(Long id) {
        return null;
    }

    @Override
    public ReturnResult<Boolean> update(GonggaoVo gonggaoVo) {
        return null;
    }

    @Override
    public ReturnResult<GonggaoVo> selectById(Long id) {
        return null;
    }

    @Override
    public ReturnResult<Integer> countByCondition(GonggaoQueryCondition condition) {
        Integer count = gonggaoMapper.countByCondition(condition);

        return ReturnResult.getNewSuccessedInstance(count);
    }
    @Override
    public ReturnResult<List<GonggaoVo>> selectByCondition(GonggaoQueryCondition condition) {
        List<Gonggao> gonggaos = gonggaoMapper.selectByCondition(condition);

        List<GonggaoVo> gonggaoVos = new ArrayList<>();
        for(Gonggao item: gonggaos){
            GonggaoVo gonggaoVo = new GonggaoVo();
            BeanUtils.copyProperties(item,gonggaoVo);
            gonggaoVos.add(gonggaoVo);
        }
        return ReturnResult.getNewSuccessedInstance(gonggaoVos);
    }
}
