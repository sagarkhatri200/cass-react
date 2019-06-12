package com.reakaf.reactbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reakaf.reactbackend.cassandra.RestaurantRepository;
import com.reakaf.reactbackend.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverRecord;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class KafkaRestaurantMessageProcessor implements CommandLineRunner {


    @Autowired
    KafkaReceiver kafkaReceiver;

    @Autowired
    RestaurantRepository repository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");

    @Override
    public void run(String... args) throws Exception {
        Flux<ReceiverRecord<String,String>> kafkaFlux = kafkaReceiver.receive();
        kafkaFlux.flatMap(r->
                {
                    Gson gson = new GsonBuilder().create();
                    Restaurant rest = gson.fromJson(r.value(), Restaurant.class);

                    System.out.println("Processed: " + rest.getName());
                    return Flux.zip(repository.save(rest),Flux.just(r));
                }).flatMap(r->{
                    ReceiverOffset offset = r.getT2().receiverOffset();
                    System.out.printf("Received message: %s\n", r);
                    offset.commit();
                    return Flux.just(r);
                }).subscribe();
    }
}
