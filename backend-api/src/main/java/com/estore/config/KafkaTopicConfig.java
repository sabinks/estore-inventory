package com.estore.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic esiTopic(){
        return TopicBuilder.name("esi_one").build();
    }

    @Bean
    public NewTopic esiJsonTopic(){
        return TopicBuilder.name("json_esi_one").build();
    }
}
