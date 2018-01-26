package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ft.backend.work.baseVo.ListItemVo;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.DegreeService;
import com.ft.backend.work.client.vo.DegreeVo;
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
 * Created by huanghongyi on 2017/11/26.
 */
@Controller
@RequestMapping("/degree")
public class DegreeController {
    @Resource
    private DegreeService degreeService;

    private Logger logger = LogManager.getLogger(DegreeController.class);

    @RequestMapping(value = "/select")
    @ResponseBody
    public JSONArray select(HttpServletRequest request){

        JSONArray jsonFromBean=null;
        try {

            Map<String,Object> params = new HashMap<>();

            ReturnResult<List<DegreeVo>> resp=  degreeService.selectByCondition(params);
            List<DegreeVo> degreeVos =new ArrayList<>();
            if(resp!=null && resp.getResult()!=null){
                degreeVos=resp.getResult();
            }

            List<ListItemVo> itemVos =new ArrayList<>();
            ListItemVo itemVo=new ListItemVo();
            itemVo.setText("请选择");
            itemVo.setValue("");
            itemVos.add(itemVo);
            for(DegreeVo item: degreeVos){
                itemVo=new ListItemVo();
                itemVo.setText(item.getDegreename());
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
