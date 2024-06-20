package com.toomeet.toomeet_play_api.entity;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "createdBy", "updatedBy"}, allowGetters = true)
public class Auditable extends BaseEntity {
    @ManyToOne()
    @CreatedBy
    @JoinColumn(name = "created_by", updatable = false)
    @JsonProperty("createdBy")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User createdBy;


    @ManyToOne()
    @LastModifiedBy
    @JoinColumn(name = "updated_by")
    @JsonProperty("updatedBy")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User updatedBy;


    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updatedAt")
    private Date updatedAt;
}
