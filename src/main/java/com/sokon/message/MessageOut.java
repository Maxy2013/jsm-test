package com.sokon.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author liuzheng
 * @since 16:59 2020/11/1
 */
@Component
public class MessageOut {


    @Autowired
    @Qualifier("jmsTemplateOut")
    private JmsTemplate jmsTemplateOut;

    public void sendNotifyMessage(String message){
        jmsTemplateOut.convertAndSend(message);
        System.out.println("回执发送成功");
    }
}
