package com.restful.app.dao;

import com.restful.app.api.dao.IAGenericDao;
import com.restful.app.entity.AEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AGenericDao<T extends AEntity<Long>> implements IAGenericDao<T> {

    private final Class<T> entity;

    @PersistenceContext(unitName = "Criteria")
    protected EntityManager entityManager;

    public AGenericDao(Class<T> clazz) {
        this.entity = clazz;
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T get(long id) {
        return entityManager.find(getGenericClass(), id);
    }

    public List<T> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getGenericClass());
        Root<T> root = query.from(getGenericClass());
        query.select(root);
        TypedQuery<T> result = entityManager.createQuery(query);
        return result.getResultList();
    }

    public void update(T entity) {
        entityManager.detach(entity);
        entityManager.merge(entity);
        entityManager.flush();
    }

    public void detach(T entity) {
        entityManager.detach(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public Class<T> getGenericClass() {
        return this.entity;
    }
}
