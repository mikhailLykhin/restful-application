package com.restful.app.api.mappers;

import com.restful.app.api.dao.extension.sd.SdEngineDao;
import com.restful.app.api.dao.extension.sd.SdParkingDao;
import com.restful.app.api.dto.extension.VehicleDto;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class VehicleMapper extends AbstractMapper<Vehicle, VehicleDto> {

    private final SdEngineDao engineDao;
    private final SdParkingDao parkingDao;
    private final ModelMapper mapper;

    public VehicleMapper(SdEngineDao engineDao, SdParkingDao parkingDao, ModelMapper mapper) {
        super(Vehicle.class, VehicleDto.class, mapper);
        this.engineDao = engineDao;
        this.parkingDao = parkingDao;
        this.mapper = mapper;
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(Vehicle.class, VehicleDto.class)
                .addMappings(m -> m.skip(VehicleDto::setEngineId))
                .addMappings(m -> m.skip(VehicleDto::setParkingIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(VehicleDto.class, Vehicle.class)
                .addMappings(m -> m.skip(Vehicle::setEngine)).
                addMappings(m -> m.skip(Vehicle::setParkings)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Vehicle source, VehicleDto destination) {
        destination.setEngineId(getId(source));
        destination.setParkingIds(getSetId(source));
    }

    @Override
    void mapSpecificFields(VehicleDto source, Vehicle destination) {
        destination.setEngine(engineDao.findById(source.getEngineId()).orElse(null));
        Set<Parking> parking = new HashSet<>();
        for (Long parkingId : source.getParkingIds()) {
            parking.add(parkingDao.getById(parkingId));
        }
        destination.setParkings(parking);
    }

    private Long getId(Vehicle source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getEngine().getId();
    }

    private Set<Long> getSetId(Vehicle source) {
        if (Objects.isNull(source) || Objects.isNull(source.getId())) {
            return null;
        }
        Set<Long> idS = new HashSet<>();
        for (Parking parking : source.getParkings()) {
            idS.add(parking.getId());
        }
        return idS;
    }
}
