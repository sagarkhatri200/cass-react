package com.reakaf.reactfe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.UUID;

@Service
public class RestaurantsService {

    @Autowired
    KafkaSender sender;

    public Mono<Restaurant> Save(Restaurant restaurant){
        return sender.createOutbound()
                .send(Flux.range(1,10)
                        .map(i-> {
                            Gson gson = new GsonBuilder().create();
                            restaurant.setId(UUID.randomUUID());
                            String restaurantString = gson.toJson(restaurant);
                            return SenderRecord.create(new ProducerRecord<>("restaurantevents", restaurant.getId().toString(), restaurantString),i);}))
                .then()
                .map(c-> Mono.just(restaurant));
    }
}
