package com.reakaf.reactfe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RestaurantsService {

    @Autowired
    KafkaSender sender;

    private static Logger logger = LoggerFactory.getLogger(RestaurantsService.class);

    public Mono<Restaurant> Save(Restaurant restaurant){
        logger.info("Restaurant Service saving");
        return sender.createOutbound()
                .send(Flux.range(1,1)
                        .map(i-> {
                            Gson gson = new GsonBuilder().create();
                            restaurant.setId(UUID.randomUUID());
                            restaurant.setName(restaurant.getName() + "-" + LocalDateTime.now().toString());
                            String restaurantString = gson.toJson(restaurant);
                            return SenderRecord.create(new ProducerRecord<>("restaurantevents", restaurant.getId().toString(), restaurantString),i);}))
                .then();
    }
}
