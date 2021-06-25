package com.restful.app.services.extension.jdbc_services;

import com.restful.app.api.dao.extension.jdbc_dao.JdbcParkingDao;
import com.restful.app.api.dto.extension.ParkingDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.jdbc.JdbcParkingService;
import com.restful.app.extension_entity.Parking;
import com.restful.app.extension_entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JdbcParkingServiceImpl implements JdbcParkingService {

    private final JdbcParkingDao parkingDao;
    private final CommonMapper commonMapper;

    public JdbcParkingServiceImpl(JdbcParkingDao parkingDao, CommonMapper commonMapper) {
        this.parkingDao = parkingDao;
        this.commonMapper = commonMapper;
    }

    @Override
    @Transactional
    public void createParking(ParkingDto parkingDto) {
        parkingDao.createParking(commonMapper.map(parkingDto, Parking.class));
    }

    @Override
    @Transactional
    public void updateParking(long id, ParkingDto parkingDto) {
        parkingDao.updateParking(id, commonMapper.map(parkingDto, Parking.class));
    }

    @Override
    @Transactional
    public List<ParkingDto>  getAllParking() {
        return commonMapper.mapAll(parkingDao.getAllParkings(), ParkingDto.class);
    }

    @Override
    @Transactional
    public ParkingDto getParking(long id) {
        return commonMapper.map(parkingDao.getParking(id), ParkingDto.class);
    }

    @Override
    @Transactional
    public void deleteParking(long id) {
        parkingDao.deleteParking(id);
    }
}
