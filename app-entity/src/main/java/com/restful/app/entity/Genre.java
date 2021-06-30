package com.restful.app.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "genre")
public class Genre extends AEntity<Long>{

    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},orphanRemoval = true,
            mappedBy = "genre")
    private List<Book> books = new ArrayList<>();

}
