package com.restful.app.api.dao.extension.specifications;

import com.restful.app.api.enums.SearchOperation;
import com.restful.app.extension_entity.Vehicle;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public VehicleSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public VehicleSpecificationsBuilder with(String key, String operation, Object value) {
        SearchOperation searchOperation = SearchOperation.getSimpleOperation(operation.charAt(0));
        params.add(new SearchCriteria(key, searchOperation, value));
        return this;
    }

    public Specification<Vehicle> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(VehicleSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
