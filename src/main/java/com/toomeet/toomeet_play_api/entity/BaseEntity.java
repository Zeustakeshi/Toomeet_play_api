package com.toomeet.toomeet_play_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @SequenceGenerator(
            name = "primary_key_seq",
            sequenceName = "primary_key_seq",
            initialValue = 1000,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    private Long id;

}
