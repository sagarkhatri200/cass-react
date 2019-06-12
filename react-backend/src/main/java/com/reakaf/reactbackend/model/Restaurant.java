package com.reakaf.reactbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Restaurant {

    @PrimaryKey
    private UUID id;

    private String name;
    private String city;
    private int noOfLikes;
    private Boolean isApproved;
    private LocalDateTime dateCreated;
}
