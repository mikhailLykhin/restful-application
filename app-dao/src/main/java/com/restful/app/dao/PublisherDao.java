package com.restful.app.dao;

import com.restful.app.api.dao.IPublisherDao;
import com.restful.app.entity.Publisher;
import com.restful.app.entity.Publisher_;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class PublisherDao extends AGenericDao<Publisher> implements IPublisherDao {

    public PublisherDao() {
        super(Publisher.class);
    }

    @Override
    public Publisher getByName(String name) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Publisher> query = builder.createQuery(getGenericClass());
            Root<Publisher> publisherRoot = query.from(Publisher.class);
            query.select(publisherRoot).where(builder.equal(publisherRoot.get(Publisher_.name), name));
            TypedQuery<Publisher> result = entityManager.createQuery(query);
            return result.getResultList().stream().findFirst().orElse(null);
        } catch (NoResultException e) {
            throw new NoResultException(e.getMessage());
        }
    }

    @Override
    public boolean isPublisherExist(String name) {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Publisher> query = builder.createQuery(getGenericClass());
            Root<Publisher> publisherRoot = query.from(Publisher.class);
            query.select(publisherRoot).where(builder.equal(publisherRoot.get(Publisher_.name), name));
            TypedQuery<Publisher> result = entityManager.createQuery(query);
            return result.getResultList().stream().findFirst().isPresent();

    }
}
