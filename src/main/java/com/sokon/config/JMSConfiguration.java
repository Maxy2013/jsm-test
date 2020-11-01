package com.sokon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import java.util.Properties;

/**
 * @author liuzheng
 * @since 16:02 2020/11/1
 */
@Configuration

public class JMSConfiguration{




    private String userName ="weblogic";


    private String connectionFactory ="ConnectionFactory-test";


    private String password ="weblogic123";


    private String topic ="com.QueueTest";


    private String url ="t3://localhost:7001";

    @Autowired
    private MyMessageListener myMessageListener;


    @Bean(name ="weblogicJms")

    public JndiTemplate weblogicJms(){

        Properties props =new Properties();

        props.setProperty("java.naming.factory.initial","weblogic.jndi.WLInitialContextFactory");

        props.setProperty("java.naming.provider.url",url );

        props.setProperty("java.naming.security.principal",userName);

        props.setProperty("java.naming.security.credentials",password);

        JndiTemplate jmsJndiTemplate =new JndiTemplate();

        jmsJndiTemplate.setEnvironment(props);

        return jmsJndiTemplate;

    }


    @Bean(name ="jmsConnectionFactory")

    public JndiObjectFactoryBean jmsConnectionFactory(){

        JndiObjectFactoryBean jndiObjectFactoryBean =new JndiObjectFactoryBean();

        jndiObjectFactoryBean.setJndiName(connectionFactory);

        jndiObjectFactoryBean.setJndiTemplate(weblogicJms());

        return jndiObjectFactoryBean;

    }



    @Bean(name ="jmsDestination")

    public JndiObjectFactoryBean jmsDestination(){

        JndiObjectFactoryBean jmsDestination =new JndiObjectFactoryBean();

        jmsDestination.setJndiName(topic);

        jmsDestination.setJndiTemplate(weblogicJms());

        return jmsDestination;

    }


    @Bean(name ="listenerTopic")

    @ConditionalOnMissingBean

    public DefaultMessageListenerContainer listenerTopic(){

        DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();

        listener.setConnectionFactory((ConnectionFactory) jmsConnectionFactory().getObject());

        listener.setDestination((Destination) jmsDestination().getObject());

        listener.setAutoStartup(true);

        listener.setPubSubDomain(true);

        listener.setMessageListener(myMessageListener);

        return listener;

    }


    @Bean(name ="jmsTemplate" )

//    @ConditionalOnMissingBean

    public JmsTemplate getJmsTemplate(){

        JmsTemplate jmsTemplate =new JmsTemplate();

        jmsTemplate.setConnectionFactory((ConnectionFactory) jmsConnectionFactory().getObject());

        jmsTemplate.setDefaultDestination((Destination) jmsDestination().getObject());

        jmsTemplate.setPubSubDomain(true);

        jmsTemplate.setExplicitQosEnabled(true);

        return jmsTemplate;

    }
}