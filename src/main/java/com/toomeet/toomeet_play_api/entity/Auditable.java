package com.toomeet.toomeet_play_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt", "createdBy", "updatedBy"},
        allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Auditable extends BaseEntity {
    //    @ManyToOne()
    //    @CreatedBy
    //    @JoinColumn(name = "created_by", updatable = false)
    //    @JsonProperty("createdBy")
    //    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    //    @JsonIdentityReference(alwaysAsId = true)
    //    private User createdBy;
    //
    //    @ManyToOne()
    //    @LastModifiedBy
    //    @JoinColumn(name = "updated_by")
    //    @JsonProperty("updatedBy")
    //    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    //    @JsonIdentityReference(alwaysAsId = true)
    //    private User updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
