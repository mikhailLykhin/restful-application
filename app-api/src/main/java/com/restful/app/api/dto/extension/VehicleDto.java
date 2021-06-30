package com.restful.app.api.dto.extension;

import com.restful.app.extension_entity.Engine;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Set;

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
    private long engineId;
    private Set<Long> parkingIds;


}
