package com.restful.app.services.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplateParkingDao;
import com.restful.app.api.dto.extension.ParkingDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.jdbc_template.JdbcTemplateParkingService;
import com.restful.app.extension_entity.Parking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JdbcTemplateParkingServiceImpl implements JdbcTemplateParkingService {

    private final JdbcTemplateParkingDao jdbcTemplateParkingDao;
    private final CommonMapper commonMapper;

    public JdbcTemplateParkingServiceImpl(JdbcTemplateParkingDao jdbcTemplateParkingDao, CommonMapper commonMapper) {
        this.jdbcTemplateParkingDao = jdbcTemplateParkingDao;
        this.commonMapper = commonMapper;
    }


    @Override
    @Transactional("extensionTransactionManager")
    public void createParking(ParkingDto parkingDto) {
        jdbcTemplateParkingDao.createParking(commonMapper.map(parkingDto, Parking.class));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void updateParking(long id, ParkingDto parkingDto) {
        jdbcTemplateParkingDao.updateParking(id, commonMapper.map(parkingDto, Parking.class));
    }

    @Override
    @Transactional("extensionTransactionManager")
    public List<ParkingDto> getAllParking() {
        return commonMapper.mapAll(jdbcTemplateParkingDao.getAllParkings(), ParkingDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public ParkingDto getParking(long id) {
        return commonMapper.map(jdbcTemplateParkingDao.getParking(id), ParkingDto.class);
    }

    @Override
    @Transactional("extensionTransactionManager")
    public void deleteParking(long id) {
        jdbcTemplateParkingDao.deleteParking(id);
    }
}


