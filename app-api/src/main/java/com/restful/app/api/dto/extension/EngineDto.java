package com.restful.app.api.dto.extension;

import com.restful.app.extension_enum.EngineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

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
    private Set<VehicleDto> vehicles;


}
