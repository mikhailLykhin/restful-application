package com.restful.app.api.dto.extension;

import com.restful.app.extension_entity.Person;
import com.restful.app.extension_entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ParkingDto {

    private long id;
    private float square;
    private String address;
    private Person person;
    private List<VehicleDto> vehicles;


}
