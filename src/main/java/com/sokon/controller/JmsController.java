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
public class JmsController {


    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    @GetMapping("/sendMsg")
    public String sendMsg(){
        String msg = System.currentTimeMillis() + "发送";


        jmsTemplate.convertAndSend(msg);

        return msg;

    }

}
