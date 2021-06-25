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
@Entity
@SuperBuilder
@EqualsAndHashCode
@Table(name = "publisher")
public class Publisher extends AEntity<Long>{

    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "publisher",
            fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();
}
