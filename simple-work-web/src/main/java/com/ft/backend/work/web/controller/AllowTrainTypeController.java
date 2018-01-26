package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ft.backend.work.baseVo.ListItemVo;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.vo.CheduiVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghongyi on 2017/11/25.
 */
@Controller
@RequestMapping("/allowTrainType")
public class AllowTrainTypeController {

    private Logger logger = LogManager.getLogger(AllowTrainTypeController.class);


    @Value("${prop.allowTrainType.items}")
    private String allowTrainTypeItems;

    @RequestMapping(value = "/select")
    @ResponseBody
    public JSONArray select(HttpServletRequest request){

        JSONArray jsonFromBean=null;

        try {
            Map<String,Object> params=new HashedMap();

            List<ListItemVo> itemVos =new ArrayList<>();
            ListItemVo defaultI=new ListItemVo();


            defaultI.setText("请选择");
            defaultI.setValue("");
            itemVos.add(defaultI);

            String[] items = allowTrainTypeItems.split(",");
            for(String item: items) {

                ListItemVo itemVo = new ListItemVo();
                itemVo.setValue(item);
                itemVo.setText(item);
                itemVos.add(itemVo);
            }


            String jsonstr= JSON.toJSONString(itemVos);
            logger.info("select -info, tess:{}", jsonstr);
            jsonFromBean = (JSONArray) JSONArray.parseArray(jsonstr);
        }catch (Exception e){

        }
        return jsonFromBean;
    }
}
