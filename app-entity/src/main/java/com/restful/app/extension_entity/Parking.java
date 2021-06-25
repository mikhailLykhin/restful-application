package com.restful.app.extension_entity;

import com.restful.app.entity.AEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder
@Entity
@Table(name = "parking")
public class Parking extends AEntity<Long> {

    @Column(name = "square")
    private float square;

    @Column(name = "address")
    private String address;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "parking_vehicle",
            joinColumns = @JoinColumn(name = "parking_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"))
    private List<Vehicle> vehicles = new ArrayList<>();
}
