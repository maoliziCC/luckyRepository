package com.tuniu.finance.xff.lrm.controller;
/****samplesamplesamplesample***/
import com.tiefan.fbs.fdg.base.ResponseVo;
import com.tiefan.fbs.fsp.base.core.annotation.Json;
import com.tiefan.fbs.fsp.client.annotation.FSPServiceInfo;
import com.tuniu.finance.xff.lrm.constant.ResponseCode;
import com.tuniu.finance.xff.lrm.dto.drools.DroolsParam;
import com.tuniu.finance.xff.lrm.dto.drools.JyqAdmitScoreRuleDto;
import com.tuniu.finance.xff.lrm.dto.trade.LoanRepayTimeNotifyDto;
import com.tuniu.finance.xff.lrm.service.impl.drools.SetRuleItemValueServiceImpl;
import com.tuniu.finance.xff.lrm.util.JsonUtil;
import com.tuniu.finance.xff.lrm.util.ResponseUtil;
import com.tuniu.operation.platform.base.rest.RestException;
import com.tuniu.operation.platform.base.rest.RestServer;
import com.tuniu.operation.platform.base.text.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lizhang on 2017/10/10.
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private SetRuleItemValueServiceImpl setRuleItemValueServiceImpl;

    @RequestMapping(value = "/deviceScore", method = RequestMethod.POST)
    @ResponseBody
    public void setDeviceScore(HttpServletRequest request,
                                 HttpServletResponse response) throws RestException {
        RestServer restServer = new RestServer(request, response);
        try {
            String identity = request.getParameter("identity");
            String name = request.getParameter("name");
            String mobile = request.getParameter("mobile");
            String property = request.getParameter("property"); //

            if (StringUtils.isBlank(identity)
                    || StringUtils.isBlank(name)
                    || StringUtils.isBlank(mobile)
                    || StringUtils.isBlank(property)) {
                restServer.sendRestData(JsonUtil.toString(ResponseUtil.getResponseVo(ResponseCode.CODE_1000004)));
                return;
            }
            DroolsParam droolsParam = new DroolsParam();
            droolsParam.setAccount(mobile);
            droolsParam.setName(name);
            droolsParam.setIdNo(identity);
            JyqAdmitScoreRuleDto dto = new JyqAdmitScoreRuleDto();
            dto.setDroolsParam(droolsParam);
            setRuleItemValueServiceImpl.setRuleItemValue(dto,property);
            ResponseVo responseVo = ResponseUtil.getResponseVo(ResponseCode.CODE_1000000, dto);

            restServer.sendRestData(JsonUtil.toString(responseVo));
        } catch (Exception e) {
            LOGGER.error("接口异常", e);
            restServer.sendRestData(JsonUtil.toString(ResponseUtil.getResponseVo(ResponseCode.CODE_1000002)));
        }
    }

}
