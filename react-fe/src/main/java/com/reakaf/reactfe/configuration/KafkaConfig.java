package com.reakaf.reactfe.configuration;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
class KafkaConfig {


    @Bean
    KafkaSender kafkaSender(){
        String bootstrapAddress = "127.0.0.1:9092";
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

//        final Map<String, String> overrides = new LinkedHashMap<>();
//        overrides.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, getSecurityProtocol());
//        overrides.put(SaslConfigs.SASL_MECHANISM, getSaslMechanism());
//        overrides.put(SaslConfigs.SASL_JAAS_CONFIG, getSaslJaasConfig());
//
//        final KafkaSender sender = KafkaSender.newBuilder()
//                .bootstrapServers(bootstrapServers).topic(topic).overrides(overrides).build();

        SenderOptions<String, String> senderOptions =
                SenderOptions.<String, String>create(producerProps)
                        .maxInFlight(1024);
        return KafkaSender.create(senderOptions);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}