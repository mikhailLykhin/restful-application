package com.restful.app.services.extension.jdbc_services;

import com.restful.app.api.dao.extension.jdbc_dao.JdbcParkingDao;
import com.restful.app.api.dto.extension.ParkingDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.jdbc.JdbcParkingService;
import com.restful.app.extension_entity.Parking;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
    public void createParking(ParkingDto parkingDto) throws SQLException {
        parkingDao.createParking(commonMapper.map(parkingDto, Parking.class));
    }

    @Override
    public void updateParking(long id, ParkingDto parkingDto) throws SQLException {
        parkingDao.updateParking(id, commonMapper.map(parkingDto, Parking.class));
    }

    @Override
    public List<ParkingDto>  getAllParking() throws SQLException {
        return commonMapper.mapAll(parkingDao.getAllParkings(), ParkingDto.class);
    }

    @Override
    public ParkingDto getParking(long id) throws SQLException {
        return commonMapper.map(parkingDao.getParking(id), ParkingDto.class);
    }

    @Override
    public void deleteParking(long id) throws SQLException {
        parkingDao.deleteParking(id);
    }
}
