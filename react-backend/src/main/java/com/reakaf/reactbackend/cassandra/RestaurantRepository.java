package com.reakaf.reactbackend.cassandra;

import com.reakaf.reactbackend.model.Restaurant;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends ReactiveCassandraRepository<Restaurant, UUID> {
}
