package com.ft.work.web.test;

import com.ft.backend.work.client.QueryCondition.ChejianQueryCondition;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.ChejianService;
import com.ft.backend.work.client.vo.ChejianVo;
import com.ft.backend.work.client.vo.WorkerVo;
import com.ft.backend.work.model.Worker;
import com.ft.backend.work.service.impl.redis.RedisUtilsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Created by huanghongyi on 2016/11/8.
 */
public class WorkshopServiceTest extends BaseTest{

    private Logger logger= LogManager.getLogger(WorkshopServiceTest.class);

    @Autowired
    private ChejianService workshopService;

    @Autowired
    private RedisUtilsService redisUtilsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void add(){

        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        ChejianVo workshopVo=new ChejianVo();
        workshopVo.setChejianName("云平台事业部");
        workshopVo.setChejianIntroduction("简介");
        workshopVo.setChejianInfo("reamrks");

        ReturnResult<ChejianVo> result=workshopService.add(workshopVo);

        stopWatch.stop();
        logger.info("return result={}",result);
        System.out.println("-------------stopWatch:"+stopWatch.prettyPrint());
    }

    @Test
    public void delete(){
        Long id=2L;
        ReturnResult<Boolean> result=null;
        result=workshopService.deleteById(id);
        logger.info("result=",result);
    }

    @Test
    public void selectById(){
       Long id=2L;
        ReturnResult<ChejianVo> result=null;
        result=workshopService.selectById(id);

        logger.info("result=",result);
    }

    @Test
    public void selectByCondition(){
        ChejianQueryCondition condition=new ChejianQueryCondition();
        condition.setChejianName("部");

        ReturnResult<List<ChejianVo>> result=null;
        result=workshopService.selectByCondition(condition);

        logger.info("result=",result);
    }


    @Test
    public void encode(){
        String str="[{\"gw_mac\":\"40:a5:ef:51:34:ac\",\"cpu\":\"2\",\"memory\":\"76\",\"wanip\":\"172.29.255.29\",\"upflow\":\"432441\",\"downflow\":\"19387610\",\"wan_down_drop\":\"18231\",\"netsta\":\"total:5/fail:0/max:7/min:5/avg:6\"}]";

        System.out.println("-----------------------------------------------------");
        char[] keyData = "11223344556677889900aabbccddeeff1f2c3de5".toCharArray(); // 密钥
        char[] charArray = str.toCharArray();
        int bit=0;

        System.out.println("-------"+charArray.length);
        System.out.println("-------"+keyData.length);

        for(int i = 0; i < charArray.length; i++){
            if (bit >= keyData.length){
                bit = 0;
            }
            charArray[i] = (char) (charArray[i] ^ keyData[bit++]);
            System.out.print((int)charArray[i]+"-");
        }

         bit=0;
        for(int i = 0; i < charArray.length; i++) {
            if (bit >= keyData.length) {
                bit = 0;
            }
            charArray[i] = (char) (charArray[i] ^ keyData[bit++]);
            System.out.print(charArray[i] + "-");
        }
        System.out.println("-----------------------------------------------------");
    }

    @Test
    public void redisOperateTest(){
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();


        String key="hhy";
        boolean userOfHhy = redisUtilsService.exists(key);
        if(!userOfHhy){

            List<WorkerVo> workerVos = new ArrayList<>();
            WorkerVo workerVo = new WorkerVo();
            workerVo.setId(1L);
            workerVo.setName("hhy");

            WorkerVo workerVo1 = new WorkerVo();
            workerVo1.setId(2L);
            workerVo1.setName("dwq");

            workerVos.add(workerVo);
            workerVos.add(workerVo1);

            redisUtilsService.put(key,workerVos);
        }
        else{
            redisUtilsService.expire(key,1);
        }



        List<WorkerVo> workerVos =redisUtilsService.get(key,WorkerVo.class, ArrayList.class);

        System.out.println("-------------stopWatch:"+stopWatch.prettyPrint());
    }


}
