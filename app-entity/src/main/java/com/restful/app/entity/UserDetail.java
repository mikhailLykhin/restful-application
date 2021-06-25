package com.restful.app.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "user_detail")
@EqualsAndHashCode
public class UserDetail extends AEntity<Long> {

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "educational_institution")
    private String educationalInstitution;

    @Column(name = "ei_address")
    private String educationalInstitutionAddress;

    @OneToOne(mappedBy = "userDetails",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private User user;

}
