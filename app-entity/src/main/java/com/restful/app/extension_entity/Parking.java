package com.restful.app.extension_entity;

import com.restful.app.entity.AEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "parking")
@NamedEntityGraph(
        name = "parking-graph-with-vehicle-person-subgraph-engine",
        attributeNodes = {
                @NamedAttributeNode(value = "vehicles", subgraph = "vehicles-engine"),
                @NamedAttributeNode("person"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "vehicles-engine",
                        attributeNodes = {
                                @NamedAttributeNode("engine")
                        }
                )
        }
)
@NamedEntityGraph(
        name = "parking-graph-with-vehicle",
        attributeNodes = {
                @NamedAttributeNode("vehicles"),
                @NamedAttributeNode("person")
        }
)
public class Parking extends AEntity<Long> {

    @Column(name = "square")
    private float square;

    @Column(name = "address")
    private String address;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "parking_vehicle",
            joinColumns = @JoinColumn(name = "parking_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"))
    private Set<Vehicle> vehicles = new HashSet<>();
}
