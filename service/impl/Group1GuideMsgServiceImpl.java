/**  
 * All rights Reserved, Designed By www.maihaoche.com
 * 
 * @Package com.mhc.guide.core.biz.service.impl
 * @author: 三帝（sandi@maihaoche.com）
 * @date: 2018-11-30 09:37:59
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved. 
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */ 
package com.mhc.guide.core.biz.service.impl;

import com.camaro.message.dto.DingTalkMsgRequest;
import com.camaro.message.dto.SendResult;
import com.camaro.message.enums.DingMsgTypeEnum;
import com.camaro.message.enums.MessageChannelEnum;
import com.camaro.message.service.MessageService;
import com.mhc.bs.common.enums.PublicEnum;
import com.mhc.framework.common.base.biz.BaseServiceImpl;
import com.mhc.framework.common.util.ValidatorUtil;
import com.mhc.guide.core.biz.service.Group1GuideMsgService;
import com.mhc.guide.core.biz.validator.TxtMsgVoValidator;
import com.mhc.guide.core.biz.vo.LinkMsgVo;
import com.mhc.guide.core.biz.vo.TxtMsgVo;
import com.mhc.guide.dal.domain.Group1GuideMsg;
import com.mhc.guide.dal.domain.Group1GuideTemplate;
import com.mhc.guide.dal.manager.Group1GuideMsgManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**   
 * <p>  服务实现类 </p>
 *   
 * @author: 三帝（sandi@maihaoche.com）
 * @date: 2018-11-30 09:37:59
 * @since V1.0 
 */
@Service
public class Group1GuideMsgServiceImpl extends BaseServiceImpl implements Group1GuideMsgService {

//    private List<Integer> dingTalkIds=new ArrayList<>();

    @Autowired
    private Group1GuideMsgManager group1GuideMsgManager;

    @Autowired
    private MessageService messageService;

    /**
     * <p>
     *      1，校验入参
     *      * 2，调用钉钉接口发送消息
     *      * 3，对发送结果进行处理，保存发送日志，如果发送结果失败，日志内状态为0=失败
     * </p>
     *
     * @param
     * @return null
     * @throws
     * @author 文远（wenyuan@maihaoche.com）
     * @since V1.1.0-SNAPSHOT
     */
    public void sendTxtMsg(TxtMsgVo vo)
    {
        //校验入参
        ValidatorUtil.validateObject(vo, new TxtMsgVoValidator(), true, null);
        //发送消息
        SendResult sendResult = messageService.sendMessage(DingTalkMsgRequest.dBuilder().channel(MessageChannelEnum.DTE_DEFAULT)
                .dingMsgTypeEnum(DingMsgTypeEnum.TEXT).textContent(vo.getMsgTitle()).ids(vo.getMsgReceive()).build());

        //对发送结果进行处理，保存发送日志，如果发送结果失败，日志内状态为0=失败
        Group1GuideMsg guideMsg = new Group1GuideMsg();
        //保存标题
        guideMsg.setMsgTitle(vo.getMsgTitle());
        //保存消息发送状态
        if (sendResult.isSuccess())
        {
            guideMsg.setMsgSendState(Integer.parseInt(PublicEnum.YES.getCode()));
        }
        else {
            guideMsg.setMsgSendState(Integer.parseInt(PublicEnum.NO.getCode()));
        }
        //设置默认的消息阅读状态为否
        guideMsg.setMsgState(Integer.parseInt(PublicEnum.NO.getCode()));
        guideMsg.setMsgSendTime(new Date());
        //保存
        group1GuideMsgManager.insert(guideMsg);
    }

    /**
     * <p> 发送链接消息
     * 1，校验入参
     * 2，调用钉钉接口发送消息
     * 3，对发送结果进行处理，保存发送日志，如果发送结果失败，日志内状态为0=失败
     * </p>
     *
     * @param
     * @return null
     * @throws
     * @author 文远（wenyuan@maihaoche.com）
     * @since V1.1.0-SNAPSHOT
     */
    public void sendLinkMsg(LinkMsgVo vo)
    {
        //校验入参
        ValidatorUtil.validateObject(vo, new TxtMsgVoValidator(), true, null);
        // 发送链接样式的企业通知
        SendResult sendResult = messageService.sendMessage(DingTalkMsgRequest.dBuilder()
                .channel(MessageChannelEnum.DTE_DEFAULT)
                .dingMsgTypeEnum(DingMsgTypeEnum.LINK)
                .linkMessageUrl(vo.getLinkMessageUrl())
                .linkPicUrl("http://img.dawanju.com/mclogo.png")
                .linkText(vo.getLinkText())
                .linkTitle(vo.getLinkTitle())
                .ids(vo.getMsgReceive()).build());

        //对发送结果进行处理，保存发送日志，如果发送结果失败，日志内状态为0=失败
        Group1GuideMsg guideMsg = new Group1GuideMsg();
        //保存标题
        guideMsg.setMsgTitle(vo.getLinkTitle());

        if (!sendResult.isSuccess()) {
            //发送成功
            guideMsg.setMsgSendState(Integer.parseInt(PublicEnum.NO.getCode()));
        }
        else
        {
            guideMsg.setMsgSendState(Integer.parseInt(PublicEnum.YES.getCode()));
        }
        //保存消息阅读状态
        guideMsg.setMsgState(Integer.parseInt(PublicEnum.NO.getCode()));

        //保存消息发送时间
        guideMsg.setMsgSendTime(new Date());
        //保存
        group1GuideMsgManager.insert(guideMsg);

    }



    @Override
    public List<Group1GuideMsg> selectAll()
    {

         return group1GuideMsgManager.selectAll();
    }


    @Override
    public List<Group1GuideTemplate> selectAllTemplate(String templateRange)
    {

        return group1GuideMsgManager.selectAllTemplate(templateRange);
    }


    /**
     *  <p> 测试一下发送消息 </p>
     *
     * @MethodName
     * @param
     * @return
     * @author shimiao
     */
    @Override
    public String send()
    {
        List<String> ids=new ArrayList<String>();
        ids.add("1on-9osjz4ot9r");
        //发送消息
        SendResult sendResult = messageService.sendMessage(DingTalkMsgRequest.dBuilder().channel(MessageChannelEnum.DTE_DEFAULT)
                .dingMsgTypeEnum(DingMsgTypeEnum.TEXT).textContent("hello! my name is shimiao").ids(ids).build());

        if(sendResult.isSuccess())
        {
            return "success!";
        }
        else
        {
            return "fail!";
        }
    }
}
