package com.reakaf.reactfe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RestaurantsController {

    @Autowired
    RestaurantsService restaurantsService;

    @PostMapping("/restaurants")
    public Mono<RestaurantViewModel> post(@RequestBody Restaurant restaurant){
        return restaurantsService
                .Save(restaurant).log()
                .map(RestaurantsController::mapIntoViewModel);
    }

    public static RestaurantViewModel mapIntoViewModel(Restaurant restaurant){
        return new ObjectMapper().convertValue(restaurant, RestaurantViewModel.class);
    }
}
