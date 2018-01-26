package com.ft.backend.work.service.impl;

import com.ft.backend.work.client.common.ResultCode;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.UserService;
import com.ft.backend.work.client.vo.CheduiVo;
import com.ft.backend.work.client.vo.UserVo;
import com.ft.backend.work.dao.CheduiMapper;
import com.ft.backend.work.dao.ChejianMapper;
import com.ft.backend.work.dao.UserMapper;
import com.ft.backend.work.dao.WorkerMapper;
import com.ft.backend.work.model.Chedui;
import com.ft.backend.work.model.Chejian;
import com.ft.backend.work.model.User;
import com.ft.backend.work.model.Worker;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by huanghongyi on 2016/12/3.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger logger= LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ChejianMapper chejianMapper;
    @Autowired
    private CheduiMapper cheduiMapper;
    @Autowired
    private WorkerMapper workerMapper;

    @Override
    public ReturnResult<UserVo> add(UserVo userVo) {

        logger.info("UserService - add begin, userVo={}",userVo);

        ReturnResult<UserVo> result =null;

        if(StringUtils.isBlank(userVo.getUserName().trim()) ){
             return  new ReturnResult<>(ResultCode.USER_USERNAME_NULL);
        }

        if(StringUtils.isBlank(userVo.getUserName().trim())){
            return  new ReturnResult<>(ResultCode.USER_PASSWORD_NULL);
        }

        try {

            User user=new User();
            BeanUtils.copyProperties(userVo,user);
            user.setCreateTime(new Date());

            userMapper.insert(user);
            Long id=user.getId();
            user = userMapper.selectByPrimaryKey(id);
            BeanUtils.copyProperties(user,userVo);
            result= ReturnResult.getNewSuccessedInstance(userVo);
        }
        catch (Exception e)
        {
           result = ReturnResult.getNewFailedInstance();
            logger.error("UserService - add error,error={}",e);
        }

        logger.info("UserService - add end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<UserVo> update(UserVo userVo) {

        logger.info("UserService - add begin, userVo={}",userVo);

        ReturnResult<UserVo> result =null;

        /*
        if(StringUtils.isBlank(userVo.getUserName().trim()) ){
            return  new ReturnResult<>(ResultCode.USER_USERNAME_NULL);
        }

        if(StringUtils.isBlank(userVo.getUserName().trim())){
            return  new ReturnResult<>(ResultCode.USER_PASSWORD_NULL);
        }
*/
        try {

            User user=new User();
            BeanUtils.copyProperties(userVo,user);
            user.setCreateTime(new Date());

            userMapper.updateByPrimaryKeySelective(user);
            Long id=user.getId();
            user = userMapper.selectByPrimaryKey(id);
            BeanUtils.copyProperties(user,userVo);
            result= ReturnResult.getNewSuccessedInstance(userVo);
        }
        catch (Exception e)
        {
            result = ReturnResult.getNewFailedInstance();
            logger.error("UserService - add error,error={}",e);
        }

        logger.info("UserService - add end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<Boolean> deleteById(Long id) {
        logger.info("deleteById - info,  id:{}",id);
        ReturnResult<Boolean> result=null;
        try {
            userMapper.deleteById(id);
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
    public ReturnResult<List<UserVo>> getByParameters(Map<String, Object> params) {
        logger.info("getByParameters - start, params:{}", params);
        ReturnResult<List<UserVo>> result=ReturnResult.getNewSuccessedInstance();
        List<UserVo> userVos = new ArrayList<>();

        try {
            List<User> users  =  userMapper.selectByParams(params);
            for(User item: users){
                UserVo userVo=new UserVo();
                BeanUtils.copyProperties(item,userVo);
                userVos.add(userVo);
            }
            result = ReturnResult.getNewSuccessedInstance(userVos);
        }
        catch (Exception e){
            logger.error("getByParameters - error:{}", e);
        }
        logger.info("getByParameters - end, result:{}", result);
        return result;
    }

    @Override
    public ReturnResult<UserVo> getById(Long id) {

        logger.info("UserService - getById begin, id={}",id);

        ReturnResult<UserVo> result=null;
        try{
            User user=userMapper.selectByPrimaryKey(id);

            UserVo userVo= new UserVo();
            BeanUtils.copyProperties(user,userVo);
            addExtraInfo(userVo);

            result = ReturnResult.getNewSuccessedInstance(userVo);

        }catch (Exception e){
            result=ReturnResult.getNewFailedInstance();
            logger.error("UserService - getById error,error={}",e);
        }
        logger.info("UserService - getById end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<UserVo> getByWorkerId(Long workerId) {

        logger.info("getByWorkerId -start, workerid={}",workerId);

        ReturnResult<UserVo> result=null;
        try{

            Map<String,Object> params = new HashedMap();
            params.put("workerId",workerId);
            List<User> users = userMapper.selectByParams(params);

            UserVo userVo= new UserVo();
            if(users!=null && users.size()>0){
                User user =users.get(0);
                BeanUtils.copyProperties(user,userVo);
            }

            addExtraInfo(userVo);
            result = ReturnResult.getNewSuccessedInstance(userVo);

        }catch (Exception e){
            result=ReturnResult.getNewFailedInstance();
            logger.error("UserService - getById error,error={}",e);
        }
        logger.info("UserService - getById end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<UserVo> getByUsernameAndPassword(String username, String password) {
        logger.info("UserService - getByUsernameAndPassword begin, username={},password={}",username,password);

        ReturnResult<UserVo> result =null;

        if(StringUtils.isBlank(username) ){
            return  new ReturnResult<>(ResultCode.USER_USERNAME_NULL);
        }

        if(StringUtils.isBlank(password)){
            return  new ReturnResult<>(ResultCode.USER_PASSWORD_NULL);
        }

        try {

            Map<String,Object> params=new HashedMap();
            params.put("userName",username);
            params.put("password",password);

            List<User> users=userMapper.selectByParams(params);

            if(users==null || users.size()<1){
                return  new ReturnResult<>(ResultCode.USER_PASSWORD_ERROR);
            }
            UserVo userVo=new UserVo();
            BeanUtils.copyProperties(users.get(0),userVo);

            result= ReturnResult.getNewSuccessedInstance(userVo);
        }
        catch (Exception e)
        {
            result = ReturnResult.getNewFailedInstance();
            logger.error("UserService - getByUsernameAndPassword error,error={}",e);
        }

        logger.info("UserService - getByUsernameAndPassword end, result={}",result);
        return result;
    }

    @Override
    public ReturnResult<Integer> countByParameters(Map<String, Object> params) {
        ReturnResult<Integer> returnResult=ReturnResult.getNewSuccessedInstance(0);
        Integer count= userMapper.countByCondition(params);
        returnResult = ReturnResult.getNewSuccessedInstance(count);
        return returnResult;
    }

    @Override
    public ReturnResult<List<UserVo>> selectByParameters(Map<String, Object> params) {
        ReturnResult<List<UserVo>> returnResult=ReturnResult.getNewSuccessedInstance(null);
        List<User> users= userMapper.selectByCondition(params);

        List<UserVo> userVos =new ArrayList<>();
        for(User item: users){
            UserVo userVo=new UserVo();
            BeanUtils.copyProperties(item,userVo);

            addExtraInfo(userVo);
            userVos.add(userVo);
        }
        returnResult = ReturnResult.getNewSuccessedInstance(userVos);
        return returnResult;
    }



    private void addExtraInfo(UserVo userVo){
        logger.info("addExtraInfo - start, userVo:{}", userVo);
        try {

            if(userVo.getChejianId()!=null){
                Chejian chejian = chejianMapper.selectByPrimaryKey(userVo.getChejianId());
                userVo.setChejianName(chejian.getChejianName());
            }

            if(userVo.getCheduiId()!=null){
                Chedui chedui = cheduiMapper.selectByPrimaryKey(userVo.getCheduiId());
                userVo.setCheduiName(chedui.getCheduiName());
            }

            if(userVo.getWorkId()!=null){
                Worker worker = workerMapper.selectByPrimaryKey(userVo.getWorkId());
                userVo.setWorkerName(worker.getName());
            }
        }
        catch (Exception e){
            logger.error("addExtraInfo - error:{}", e);
        }
        logger.info("addExtraInfo - end.");
    }
}
