package com.toomeet.toomeet_play_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoCategory extends Auditable {
    @Column(nullable = false, unique = true)
    private String categoryId;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany()
    private List<Video> videos;
}
