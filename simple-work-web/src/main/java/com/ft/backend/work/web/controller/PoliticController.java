package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ft.backend.work.baseVo.ListItemVo;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.PoliticService;
import com.ft.backend.work.client.vo.PoliticVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/25.
 */
@Controller
@RequestMapping("/politic")
public class PoliticController {
    @Resource
    private PoliticService politicService;

    private Logger logger = LogManager.getLogger(PoliticController.class);

    @RequestMapping(value = "/select")
    @ResponseBody
    public JSONArray select(HttpServletRequest request){

        JSONArray jsonFromBean=null;
        try {

            Map<String,Object> params = new HashMap<>();

            ReturnResult<List<PoliticVo>> resp=  politicService.selectByCondition(params);
            List<PoliticVo> politicVos =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                politicVos=resp.getResult();
            }

            List<ListItemVo> itemVos =new ArrayList<>();
            ListItemVo itemVo=new ListItemVo();
            itemVo.setText("请选择");
            itemVo.setValue("");
            itemVos.add(itemVo);
            for(PoliticVo item: politicVos){
                itemVo=new ListItemVo();
                itemVo.setText(item.getPoliticname());
                itemVo.setValue(item.getId().toString());
                itemVos.add(itemVo);
            }

            String jsonstr= JSON.toJSONString(itemVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){
            logger.info("select -error:{}", e);
        }
        return jsonFromBean;
    }
}
