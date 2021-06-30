package com.restful.app.api.dto.extension;

import com.restful.app.extension_entity.Parking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersonDto {

    private long id;
    private String name;
    private int age;
    private String email;
    private Set<ParkingDto> parkings;

}
