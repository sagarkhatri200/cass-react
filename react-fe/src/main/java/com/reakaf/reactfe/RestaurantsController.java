package com.reakaf.reactfe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.UUID;

@RestController
public class RestaurantsController {

    @Autowired
    RestaurantsService restaurantsService;

    private static Logger logger = LoggerFactory.getLogger(RestaurantsController.class);

    @PostMapping("/restaurants")
    public Mono<RestaurantViewModel> post(@RequestBody Restaurant restaurant){
        logger.info("In restaurant controller");

        return restaurantsService
                .Save(restaurant)
                .map(RestaurantsController::mapIntoViewModel)
                .doOnError(ex-> System.out.println(ex.getStackTrace()));

    }

    public Mono<Restaurant> getMenu(String id, String menuId) {
        System.out.println("Thread " + Thread.currentThread().getId() + ", getting menu in call");
        return WebClient
                .create("https://www.5milerestaurants.com")
                .get()
                .uri("/api/facade/MenuByRestaurantId/" + id + "?menuId=" + menuId)
                .retrieve()
                .bodyToMono(Restaurant.class);
    }

    public static RestaurantViewModel mapIntoViewModel(Restaurant restaurant){
        return new ObjectMapper().convertValue(restaurant, RestaurantViewModel.class);
    }
}
