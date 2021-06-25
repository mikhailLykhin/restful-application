package com.restful.app.api.dto.extension;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.restful.app.extension_entity.Vehicle;
import com.restful.app.extension_enum.EngineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EngineDto {

    private long id;
    String number;
    private EngineType type;
    private float volume;
    private VehicleDto vehicle;


}
