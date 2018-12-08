package com.mhc.guide.core.biz.service.impl;

import com.camaro.message.service.MessageService;
import com.google.gson.Gson;
import com.maihaoche.acura.api.IStaffService;
import com.mhc.framework.common.base.biz.BaseServiceImpl;
import com.mhc.framework.support.session.auth.CurrentUserHolder;
import com.mhc.guide.core.biz.service.Group1GuideLoginService;
import com.mhc.guide.dal.manager.Group1GuideMsgManager;
import com.mhc.subaru.validation.annotations.NoLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class Group1GuideLoginServiceImpl extends BaseServiceImpl implements Group1GuideLoginService
{
    private final Gson gson = new Gson();

    @Autowired
    private Group1GuideMsgManager group1GuideMsgManager;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CurrentUserHolder holder;

    @Autowired
    private IStaffService iStaffService;

    @NoLogin
    @RequestMapping(method = RequestMethod.GET, value = "/validate.htm")
    public void add(@RequestParam(value = "ct", required = true) String ciphertext, HttpServletResponse response)
    {

    }


}
