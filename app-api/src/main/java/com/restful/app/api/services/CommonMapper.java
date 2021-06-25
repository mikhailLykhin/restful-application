package com.restful.app.api.services;

import java.util.List;

public interface CommonMapper {

    <T,K> K map(T entity,Class<K> dto);

    <T,K> List<K> mapAll(List<T> entity, Class<K> dto);
}
