package com.reakaf.reactfe.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
class SpringSSEConfiguration {
//    @Bean
//    KafkaReceiver kafkaReceiver(){
//        String bootstrapAddress = "127.0.0.1:9092";
//
//        Map configProps = new HashMap();
//        configProps.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        configProps.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer);
//        configProps.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer);
//        configProps.put( ConsumerConfig.CLIENT_ID_CONFIG, "sample-client");
//        configProps.put( ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
//        configProps.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
//
//        new DefaultKafkaReceiver(
//                ConsumerFactory.INSTANCE,
//                ReceiverOptions.create(configProps).subscription(['test-topic'])
//		)
//    }

    @Bean
    KafkaSender kafkaSender(){
        String bootstrapAddress = "127.0.0.1:9092";
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        SenderOptions<String, String> senderOptions =
                SenderOptions.<String, String>create(producerProps)
                        .maxInFlight(1024);
        return KafkaSender.create(senderOptions);
    }
}