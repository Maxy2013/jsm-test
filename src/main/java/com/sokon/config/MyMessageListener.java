package com.sokon.config;

import com.sokon.message.MessageOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author liuzheng
 * @since 16:05 2020/11/1
 */
@Component
public class MyMessageListener implements MessageListener {

    @Autowired
    private MessageOut messageOut;

    public void onMessage(Message message) {
        try {
            String msg = ((TextMessage)message).getText();
            System.out.println("打印输出" + msg);
            messageOut.sendNotifyMessage("回执：【" + msg + "】");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
