package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huanghongyi on 2017/10/25.
 */
@Controller
@RequestMapping("/duty")
public class DutyController {

    private Logger logger = LogManager.getLogger(CheduiController.class);


    @RequestMapping(value = "/select")
    @ResponseBody
    public JSONArray select(HttpServletRequest request){

        JSONArray jsonFromBean=null;

        try {

            String res="[{\"value\":\"1\",\"text\":\"队长\"},{\"value\":\"2\",\"text\":\"书记\"},{\"value\":\"3\",\"text\":\"指导司机\"}]";
            jsonFromBean = (JSONArray) JSONArray.parseArray(res);
        }catch (Exception e){

        }
        return jsonFromBean;
    }
}
