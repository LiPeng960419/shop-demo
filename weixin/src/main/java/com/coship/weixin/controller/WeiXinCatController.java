package com.coship.weixin.controller;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
项目使用模板接口
 */
@RestController
@RequestMapping("/weiXin")
public class WeiXinCatController {

    @Autowired
    private WxMpService wxService;

    @RequestMapping("/sendTemplate")
    public String createWeiXinCat(@RequestBody WxMpTemplateMessage wxMpTemplateMessage)
            throws WxErrorException {
        WxMpTemplateMsgService templateMsgService = wxService.getTemplateMsgService();
        return templateMsgService.sendTemplateMsg(wxMpTemplateMessage);
    }

}