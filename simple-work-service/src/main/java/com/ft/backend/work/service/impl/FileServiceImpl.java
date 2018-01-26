package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.FileService;
import com.ft.backend.work.client.vo.FileVo;
import com.ft.backend.work.dao.FileMapper;
import com.ft.backend.work.model.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Wonderful on 2017/11/15.
 */
@Service("fileService")
public class FileServiceImpl implements FileService {
    private Logger logger= LogManager.getLogger(FileServiceImpl.class);

    @Resource
    private FileMapper fileMapper;

    @Override
    public ReturnResult<FileVo> add(FileVo record) {

        logger.info("FileServiceImpl - start, record:{}", record);
        ReturnResult<FileVo> FileVoReturnResult=ReturnResult.getNewSuccessedInstance();
        File file=new File();
        BeanUtils.copyProperties(record,file);

        fileMapper.insert(file);
        record.setId(file.getId());

        FileVoReturnResult = ReturnResult.getNewSuccessedInstance(record);
        return FileVoReturnResult;
    }

    @Override
    public ReturnResult<Boolean> deleteById(Long id) {
        logger.info("deleteById - info,  id:{}",id);
        ReturnResult<Boolean> result=null;
        try {
            fileMapper.deleteById(id);
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
    public ReturnResult<Boolean> deleteByTaskIds(List<Long> taskIds) {
        logger.info("deleteByTaskIds - info,  taskIds:{}",taskIds);
        ReturnResult<Boolean> result=null;
        try {
            fileMapper.deleteByTaskIds(taskIds);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.info("deleteByTaskIds - error:{} ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("deleteByTaskIds - end, result:{}",result);
        return result;
    }

    @Override
    public ReturnResult<Boolean> batchUpdate(List<FileVo> records) {
        return null;
    }

    @Override
    public ReturnResult<Boolean> update(FileVo record) {
        logger.info("update File begin, record={}",record);
        ReturnResult<Boolean> result=null;
        try {
            File file=new File();
            BeanUtils.copyProperties(record,file);
            fileMapper.updateByPrimaryKeySelective(file);
            result = ReturnResult.getNewSuccessedInstance(true);
        }
        catch (Exception e){
            logger.info("update File by id error ",e);
            result = ReturnResult.getNewFailedInstance();
        }

        logger.info("update File end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<FileVo> selectById(Long id) {
        logger.info("selectById - start, id={}",id);
        ReturnResult<FileVo> result = ReturnResult.getNewSuccessedInstance(null);

        if(id==null){
            return result;
        }

        try {
            File file=fileMapper.selectByPrimaryKey(id);
            if(file!=null) {
                FileVo fileVo = new FileVo();
                BeanUtils.copyProperties(file, fileVo);
                result = ReturnResult.getNewSuccessedInstance(fileVo);
            }
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
        Integer count= fileMapper.countByCondition(params);
        returnResult = ReturnResult.getNewSuccessedInstance(count);
        return returnResult;
    }

    @Override
    public ReturnResult<List<FileVo>> selectByParameters(Map<String, Object> params) {
        ReturnResult<List<FileVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<File> files= fileMapper.selectByCondition(params);

        List<FileVo> fileVoList =new ArrayList<>();
        for(File item: files){
            FileVo fileVo=new FileVo();
            BeanUtils.copyProperties(item,fileVo);
            fileVoList.add(fileVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(fileVoList);
        return returnResult;
    }

    @Override
    public ReturnResult<List<FileVo>> selectByIdList(List<Long> ids) {
        ReturnResult<List<FileVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<File> Files= fileMapper.selectByIdList(ids);

        List<FileVo> fileVos =new ArrayList<>();
        for(File item: Files){
            FileVo fileVo=new FileVo();
            BeanUtils.copyProperties(item,fileVo);
            fileVos.add(fileVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(fileVos);
        return returnResult;
    }
}
