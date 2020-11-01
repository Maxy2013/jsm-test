package com.sokon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liuzheng
 * @since 16:08 2020/11/1
 */
@RestController
@RequestMapping("/jms")
public class JmsOutController {


//    @Autowired
//    @Qualifier("jmsTemplateOut")
//    private JmsTemplate jmsTemplateOut;


//    @GetMapping("/sendMsgOut")
//    public String sendMsgOut(){
//        String msg = System.currentTimeMillis() + "sendMsgOut";
//
//
//        jmsTemplateOut.convertAndSend(msg);
//
//        return msg;
//
//    }
}
