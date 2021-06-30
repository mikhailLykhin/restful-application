package com.restful.app.extension_entity;

import com.restful.app.entity.AEntity;
import com.restful.app.extension_enum.EngineType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "engine")
public class Engine extends AEntity<Long> {

    @Column(name = "number")
    private String number;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EngineType type;

    @Column(name = "volume")
    private float volume;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            mappedBy = "engine")
    private Set<Vehicle> vehicles = new HashSet<>();
}
