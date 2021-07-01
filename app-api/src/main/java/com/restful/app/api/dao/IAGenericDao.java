package com.restful.app.api.dao;



import com.restful.app.entity.AEntity;

import java.util.List;

public interface IAGenericDao<T extends AEntity<Long>> {

    Class<T> getGenericClass();

    void create(T entity);

    T get(long id);

    List<T> getAll();

    void update(T entity);

    void delete(T entity);

}
