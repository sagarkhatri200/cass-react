package com.reakaf.reactbackend;

import brave.Tracer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reakaf.reactbackend.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverRecord;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Component
public class KafkaRestaurantMessageProcessor  { //implements CommandLineRunner{

    //@Autowired
    KafkaReceiver kafkaReceiver;

    public KafkaRestaurantMessageProcessor(KafkaReceiver kafkaReceiver){
        this.kafkaReceiver = kafkaReceiver;
        this.run(null);
    }

    //@Override
    public void run(String... args) {
        Flux<ReceiverRecord<String,String>> kafkaFlux = kafkaReceiver.receive();
        kafkaFlux.map(r->{
                ReceiverOffset offset = r.receiverOffset();
                    System.out.printf("Received message: %s\n", r);
                    offset.commit();
                    return Flux.just(r);
                }).subscribe();
    }
}
