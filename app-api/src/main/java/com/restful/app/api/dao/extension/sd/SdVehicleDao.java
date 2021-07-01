package com.restful.app.api.dao.extension.sd;

import com.restful.app.extension_entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SdVehicleDao extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
}
