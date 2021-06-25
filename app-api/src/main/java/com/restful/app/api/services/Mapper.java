package com.restful.app.api.services;

import com.restful.app.entity.AEntity;

import java.util.List;

public interface Mapper<E extends AEntity<Long>, D> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> mapListEntity(List<D> listDto);

    List<D> mapListDto(List<E> listEntity);

}
