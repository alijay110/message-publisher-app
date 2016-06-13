package com.sky.search.publisher.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    ActiveMQConnectionFactory amqConnectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setUserName("producer_login");
        factory.setPassword("producer_password");

        return factory;
    }

    @Bean
    ActiveMQTopic topicDestination() {
        return new ActiveMQTopic("e20m_uk_vq.client.refresh");
    }

    @Bean
    JmsTemplate topicTemplate(){
        JmsTemplate template = new JmsTemplate(amqConnectionFactory());
        template.setDefaultDestination(topicDestination());
        template.setPubSubDomain(true);

        return template;
    }
}
