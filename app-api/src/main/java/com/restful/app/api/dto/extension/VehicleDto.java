package com.restful.app.api.dto.extension;

import com.restful.app.extension_entity.Engine;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_enum.VehicleType;
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
public class VehicleDto {

    private long id;
    private VehicleType type;
    private String manufacture;
    private String model;
    private Engine engine;
    private List<ParkingDto> parkings;


}
