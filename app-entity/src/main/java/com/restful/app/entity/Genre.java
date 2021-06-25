package com.restful.app.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
@Entity
@Table(name = "genre")
public class Genre extends AEntity<Long>{

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},orphanRemoval = true,
            mappedBy = "genre",
            fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

}
