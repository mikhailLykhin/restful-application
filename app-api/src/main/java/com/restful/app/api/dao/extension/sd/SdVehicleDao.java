package com.restful.app.api.dao.extension.sd;

import com.restful.app.entity.User;
import com.restful.app.extension_entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface SdVehicleDao extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
}
