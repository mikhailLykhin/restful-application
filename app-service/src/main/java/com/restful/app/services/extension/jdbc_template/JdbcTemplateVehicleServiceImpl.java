package com.restful.app.services.extension.jdbc_template;

import com.restful.app.api.dao.extension.jdbc_template_dao.JdbcTemplateVehicleDao;
import com.restful.app.api.dto.extension.VehicleDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.extension.jdbc_template.JdbcTemplateVehicleService;
import com.restful.app.extension_entity.Vehicle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JdbcTemplateVehicleServiceImpl implements JdbcTemplateVehicleService {

    private final JdbcTemplateVehicleDao vehicleDao;
    private final CommonMapper commonMapper;

    public JdbcTemplateVehicleServiceImpl(JdbcTemplateVehicleDao vehicleDao, CommonMapper commonMapper) {
        this.vehicleDao = vehicleDao;
        this.commonMapper = commonMapper;
    }

    @Override
    @Transactional
    public void createVehicle(VehicleDto vehicleDto) {
        vehicleDao.createVehicle(commonMapper.map(vehicleDto, Vehicle.class));

    }

    @Override
    @Transactional
    public void updateVehicle(long id, VehicleDto vehicleDto) {
        vehicleDao.updateVehicle(id, commonMapper.map(vehicleDto, Vehicle.class));
    }

    @Override
    @Transactional
    public List<VehicleDto> getAllVehicle() {
        return commonMapper.mapAll(vehicleDao.getAllVehicles(), VehicleDto.class);
    }

    @Override
    @Transactional
    public VehicleDto getVehicle(long id) {
        return commonMapper.map(vehicleDao.getVehicle(id), VehicleDto.class);
    }

    @Override
    @Transactional
    public void deleteVehicle(long id) {
        vehicleDao.deleteVehicle(id);


    }
}
