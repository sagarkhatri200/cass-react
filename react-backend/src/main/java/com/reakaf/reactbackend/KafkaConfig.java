package com.reakaf.reactbackend;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.internals.ConsumerFactory;
import reactor.kafka.receiver.internals.DefaultKafkaReceiver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
class KafkaConfig {
    @Bean
    KafkaReceiver kafkaReceiver(){
        String bootstrapAddress = "127.0.0.1:9092";

        Map configProps = new HashMap<>();
        configProps.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put( ConsumerConfig.CLIENT_ID_CONFIG, "sample-client");
        configProps.put( ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        configProps.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        //configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaReceiver(
                ConsumerFactory.INSTANCE,
                ReceiverOptions.create(configProps).subscription(Arrays.asList("restaurantevents"))
		);
    }
}