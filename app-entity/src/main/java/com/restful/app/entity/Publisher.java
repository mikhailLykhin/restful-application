package com.restful.app.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "publisher")
public class Publisher extends AEntity<Long>{

    private String name;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "publisher",
            fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();
}

