package com.ft.backend.work.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ft.backend.work.client.QueryCondition.GonggaoQueryCondition;
import com.ft.backend.work.client.common.ReturnResult;
import com.ft.backend.work.client.service.GonggaoService;
import com.ft.backend.work.client.vo.GonggaoVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/gonggao")
public class GonggaoController {
    private Logger logger = LogManager.getLogger(GonggaoController.class);

    @Resource
    private GonggaoService gonggaoService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public JSONObject listGonggao(HttpServletRequest request){

        JSONObject jsonFromBean=null;

        String title=request.getParameter("title");
        String keyword=request.getParameter("keyword");

        GonggaoQueryCondition condition=new GonggaoQueryCondition();
        if(StringUtils.isBlank(title)){
            condition.setTitle(title);
            condition.setKeyword(keyword);
        }


        try {
            String res="{\"rows\":[{\"id\":38,\"createTime\":\"2017-10-23 15:37:59\",\"title\":\"京九车队\",\"keyword\":\"丰台机务车间\",\"details\":\"丰台机务车间\",\"publisher\":\"hhy\"},{\"id\":38,\"createTime\":\"2017-10-23 15:37:59\",\"title\":\"京九车队\",\"keyword\":\"丰台机务车间\",\"details\":\"丰台机务车间\",\"publisher\":\"ahg\"}],\"results\":37}";

            jsonFromBean=new JSONObject();

            ReturnResult<Integer> res1=gonggaoService.countByCondition(condition);
            ReturnResult<List<GonggaoVo>> res2=gonggaoService.selectByCondition(condition);
            jsonFromBean.put("results",res1.getResult());
            jsonFromBean.put("rows",res2.getResult());

            //jsonFromBean = (JSONObject)JSONObject.parse(res);
        }catch (Exception e){

        }
        return jsonFromBean;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public JSONObject addGonggao(HttpServletRequest request){

        String currentPage=request.getParameter("title");
        JSONObject jsonFromBean=null;

        try {
            String res="{\"rows\":[{\"id\":38,\"createTime\":\"2017-10-23 15:37:59\",\"title\":\"京九车队\",\"keyword\":\"丰台机务车间\",\"details\":\"丰台机务车间\",\"publisher\":\"hhy\"},{\"id\":38,\"createTime\":\"2017-10-23 15:37:59\",\"title\":\"京九车队\",\"keyword\":\"丰台机务车间\",\"details\":\"丰台机务车间\",\"publisher\":\"ahg\"}],\"results\":37}";

            String title=request.getParameter("title");
            String keyword=request.getParameter("keyword");
            String content=request.getParameter("content");
            String creater="hhy";

            GonggaoVo gonggaoVo=new GonggaoVo();
            gonggaoVo.setTitle(title);
            gonggaoVo.setKeyword(keyword);
            gonggaoVo.setContent(content);
            gonggaoVo.setmState(1);

            gonggaoService.add(gonggaoVo);

            //jsonFromBean = (JSONObject)JSONObject.parse(res);
        }catch (Exception e){

        }
        return jsonFromBean;
    }

}
