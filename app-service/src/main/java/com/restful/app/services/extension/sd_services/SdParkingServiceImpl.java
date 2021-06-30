package com.restful.app.services.extension.sd_services;

import com.restful.app.api.dao.extension.sd.SdParkingDao;
import com.restful.app.api.dto.extension.ParkingDto;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.mappers.ParkingMapper;
import com.restful.app.api.services.extension.sd.SdParkingService;
import com.restful.app.extension_entity.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SdParkingServiceImpl implements SdParkingService {

    private final SdParkingDao parkingDao;
    private final ParkingMapper parkingMapper;


    public SdParkingServiceImpl(SdParkingDao parkingDao, ParkingMapper parkingMapper) {
        this.parkingDao = parkingDao;
        this.parkingMapper = parkingMapper;
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void createParking(ParkingDto parkingDto) {
        parkingDao.save(parkingMapper.toEntity(parkingDto));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void updateParking(long id, ParkingDto parkingDto) {
        Optional<Parking> entity = parkingDao.findById(id);
        Parking parking = entity.orElse(null);
        if (parking == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        parking = parkingMapper.toEntity(parkingDto);
        parkingDao.save(parking);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<ParkingDto> getAllParking() {
        return parkingMapper.mapListDto(parkingDao.findAll());

    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<ParkingDto> getAllParkingWithPagination(int page, int size) {
        Page<Parking> parkingPage = parkingDao.findAll(PageRequest.of(page, size, Sort.by("square")));
        return parkingMapper.mapListDto(parkingPage.getContent());
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<ParkingDto> findAllParkingJpql(float square) {
        return parkingMapper.mapListDto(parkingDao.findAllParkingJpql(square, Sort.by("square")));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<ParkingDto> findAllParkingWherePersonIdNative(long id, int page, int size) {
        Page<Parking> parkingPage = parkingDao.findAllParkingWherePersonIdNative(id, PageRequest.of(page, size, Sort.by("square")));
        return parkingMapper.mapListDto(parkingPage.getContent());
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<ParkingDto> findAllParkingWithPaginationNative(int page, int size) {
        Page<Parking> parkingPage = parkingDao.findAllParkingWithPaginationNative(PageRequest.of(page, size));
        return parkingMapper.mapListDto(parkingPage.getContent());
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<ParkingDto> findAllParkingByVehicleManufactureJpql(String manufacture) {
        return parkingMapper.mapListDto(parkingDao.findAllParkingByVehicleManufactureJpql(manufacture));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<ParkingDto> findAllParkingWithVehicleAndPersonsJpql(int page, int size) {
    Page<Parking> parkingPage = parkingDao.findAllParkingWithVehicleAndPersonsJpql(PageRequest.of(page, size));
    return parkingMapper.mapListDto(parkingPage.getContent());
    }

    @Override
    @Transactional("extensionTransactionManager")
    public ParkingDto getParking(long id) {
        Optional<Parking> entity = parkingDao.findById(id);
        Parking parking = entity.orElse(null);
        if (parking == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        return parkingMapper.toDto(parking);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void deleteParking(long id) {
        Optional<Parking> entity = parkingDao.findById(id);
        Parking parking = entity.orElse(null);
        if (parking == null) {
            throw new IncorrectDataException("Engine doesn't exist");
        }
        parkingDao.delete(parking);
    }
}
