package com.restful.app.api.mappers;

import com.restful.app.api.dao.extension.sd.SdPersonDao;
import com.restful.app.api.dao.extension.sd.SdVehicleDao;
import com.restful.app.api.dto.extension.ParkingDto;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class ParkingMapper extends AbstractMapper<Parking, ParkingDto> {

    private final SdPersonDao personDao;
    private final SdVehicleDao vehicleDao;
    private final ModelMapper mapper;


    public ParkingMapper(SdPersonDao personDao, SdVehicleDao vehicleDao, ModelMapper mapper) {
        super(Parking.class, ParkingDto.class, mapper);
        this.personDao = personDao;
        this.vehicleDao = vehicleDao;
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Parking.class, ParkingDto.class)
                .addMappings(m -> m.skip(ParkingDto::setPersonId))
                .addMappings(m -> m.skip(ParkingDto::setVehiclesIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(ParkingDto.class, Parking.class)
                .addMappings(m -> m.skip(Parking::setPerson))
                .addMappings(m -> m.skip(Parking::setVehicles)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Parking source, ParkingDto destination) {
        destination.setPersonId(getId(source));
        destination.setVehiclesIds(getSetId(source));
    }

    @Override
    void mapSpecificFields(ParkingDto source, Parking destination) {
        destination.setPerson(personDao.findById(source.getPersonId()).orElse(null));
        Set<Vehicle> vehicles = new HashSet<>();
        if(!source.getVehiclesIds().isEmpty()) {
            for (Long vehiclId : source.getVehiclesIds()) {
                vehicles.add(vehicleDao.getById(vehiclId));
            }
        }
        destination.setVehicles(vehicles);
    }

    private Long getId(Parking source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getPerson().getId();
    }

    private Set<Long> getSetId(Parking source) {
        if (Objects.isNull(source) || Objects.isNull(source.getId())) {
            return null;
        }
        Set<Long> idS = new HashSet<>();
        for (Vehicle vehicle : source.getVehicles()) {
            idS.add(vehicle.getId());
        }
        return idS;
    }
}
