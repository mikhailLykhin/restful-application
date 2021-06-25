package com.restful.app.extension_entity;

import com.restful.app.entity.AEntity;
import com.restful.app.extension_enum.EngineType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            mappedBy = "engine",
            fetch = FetchType.LAZY)
    private List<Vehicle> vehicles;
}
