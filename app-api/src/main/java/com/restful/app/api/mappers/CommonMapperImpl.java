package com.restful.app.api.mappers;

import com.restful.app.api.services.CommonMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommonMapperImpl implements CommonMapper {

    private final ModelMapper modelMapper;

    public CommonMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public <T, K> K map(T entity, Class<K> dto) {
        return this.modelMapper.map(entity,dto);
    }

    @Override
    public <T, K> List<K> mapAll(List<T> entities, Class<K> dto) {
        return entities.stream().map(e -> map(e,dto)).collect(Collectors.toList());
    }
}
