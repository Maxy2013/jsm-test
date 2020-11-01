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

public class JMSOutConfiguration {




    private String userName ="weblogic";


    private String connectionFactory ="ConnectionFactory-test";


    private String password ="weblogic123";


    private String topic ="QueueTestOut";


    private String url ="t3://localhost:7001";

    @Bean(name ="weblogicJmsOut")

    public JndiTemplate weblogicJmsOut(){

        Properties props =new Properties();

        props.setProperty("java.naming.factory.initial","weblogic.jndi.WLInitialContextFactory");

        props.setProperty("java.naming.provider.url",url );

        props.setProperty("java.naming.security.principal",userName);

        props.setProperty("java.naming.security.credentials",password);

        JndiTemplate jmsJndiTemplate =new JndiTemplate();

        jmsJndiTemplate.setEnvironment(props);

        return jmsJndiTemplate;

    }


    @Bean(name ="jmsConnectionFactoryOut")

    public JndiObjectFactoryBean jmsConnectionFactoryOut(){

        JndiObjectFactoryBean jndiObjectFactoryBean =new JndiObjectFactoryBean();

        jndiObjectFactoryBean.setJndiName(connectionFactory);

        jndiObjectFactoryBean.setJndiTemplate(weblogicJmsOut());

        return jndiObjectFactoryBean;

    }



    @Bean(name ="jmsDestinationOut")

    public JndiObjectFactoryBean jmsDestinationOut() {

        JndiObjectFactoryBean jmsDestination = new JndiObjectFactoryBean();

        jmsDestination.setJndiName(topic);

        jmsDestination.setJndiTemplate(weblogicJmsOut());

        return jmsDestination;

    }

    @Bean(name ="jmsTemplateOut" )

//    @ConditionalOnMissingBean

    public JmsTemplate getJmsTemplateOut(){

        JmsTemplate jmsTemplate =new JmsTemplate();

        jmsTemplate.setConnectionFactory((ConnectionFactory) jmsConnectionFactoryOut().getObject());

        jmsTemplate.setDefaultDestination((Destination) jmsDestinationOut().getObject());

        jmsTemplate.setPubSubDomain(true);

        jmsTemplate.setExplicitQosEnabled(true);

        return jmsTemplate;

    }
}