package com.restful.app.api.mappers;

import com.restful.app.api.services.Mapper;
import com.restful.app.entity.AEntity;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbstractMapper<E extends AEntity<Long>, D> implements Mapper<E, D> {

    private final ModelMapper mapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    AbstractMapper(Class<E> entityClass, Class<D> dtoClass, ModelMapper mapper) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.mapper = mapper;
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, dtoClass);
    }

    @Override
    public List<E> mapListEntity(List<D> listDto) {
        return listDto.stream().map(this::toEntity).collect(Collectors.toList());

    }

    @Override
    public List<D> mapListDto(List<E> listEntity) {
     return listEntity.stream().map(this::toDto).collect(Collectors.toList());

    }
}
