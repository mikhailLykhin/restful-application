package com.restful.app.services.extension.sd_services;

import com.restful.app.api.dao.extension.sd.SdEngineDao;
import com.restful.app.api.dao.extension.sd.SdParkingDao;
import com.restful.app.api.dao.extension.sd.SdVehicleDao;
import com.restful.app.api.dao.extension.specifications.SearchCriteria;
import com.restful.app.api.dao.extension.specifications.VehicleSpecification;
import com.restful.app.api.dao.extension.specifications.VehicleSpecificationsBuilder;
import com.restful.app.api.dto.extension.VehicleDto;
import com.restful.app.api.enums.SearchOperation;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.mappers.VehicleMapper;
import com.restful.app.api.services.extension.sd.SdVehicleService;
import com.restful.app.extension_entity.Engine;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SdVehicleServiceImpl implements SdVehicleService {

    private final SdVehicleDao vehicleDao;
    private final SdEngineDao engineDao;
    private final SdParkingDao parkingDao;
    private final VehicleMapper vehicleMapper;

    public SdVehicleServiceImpl(SdVehicleDao vehicleDao, SdEngineDao engineDao, SdParkingDao parkingDao, VehicleMapper vehicleMapper) {
        this.vehicleDao = vehicleDao;
        this.engineDao = engineDao;
        this.parkingDao = parkingDao;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void createVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
        Engine engine = engineDao.findById(vehicleDto.getEngineId()).orElse(null);
        vehicle.setEngine(engine);
        Set<Parking> parkings = new HashSet<>();
        for (Long parkingId : vehicleDto.getParkingIds()) {
            Parking parking = parkingDao.findById(parkingId).orElse(null);
            parkings.add(parking);
        }
        vehicle.setParkings(parkings);
        vehicleDao.save(vehicle);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void updateVehicle(long id, VehicleDto vehicleDto) {
        Optional<Vehicle> entity = vehicleDao.findById(id);
        Vehicle vehicle = entity.orElse(null);
        if (vehicle == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        vehicle = vehicleMapper.toEntity(vehicleDto);
        vehicleDao.save(vehicle);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<VehicleDto> getAllVehicle() {
        return vehicleMapper.mapListDto(vehicleDao.findAll());
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<VehicleDto> getAllVehiclesWithPagination(int page, int size) {
        Page<Vehicle> vehiclePage = vehicleDao.findAll(PageRequest.of(page, size, Sort.by("manufacture")));
        return vehicleMapper.mapListDto(vehiclePage.getContent());
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<VehicleDto> getAllWithSpecificationSearch(String search) {
        VehicleSpecificationsBuilder builder = new VehicleSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Vehicle> spec = builder.build();
        return vehicleMapper.mapListDto(vehicleDao.findAll(spec));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<VehicleDto> getWithSimpleSpecification() {
        VehicleSpecification specification = new VehicleSpecification(
                new SearchCriteria("model", SearchOperation.EQUALITY, "RX350"));
        return vehicleMapper.mapListDto(vehicleDao.findAll(Specification.where(specification)));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public VehicleDto getVehicle(long id) {
        Optional<Vehicle> entity = vehicleDao.findById(id);
        Vehicle vehicle = entity.orElse(null);
        if (vehicle == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        return vehicleMapper.toDto(vehicle);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void deleteVehicle(long id) {
        Optional<Vehicle> entity = vehicleDao.findById(id);
        Vehicle vehicle = entity.orElse(null);
        if (vehicle == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        vehicleDao.delete(vehicle);
    }
}
