package com.restful.app.dao;

import com.restful.app.api.dao.IUserDao;
import com.restful.app.entity.User;
import com.restful.app.entity.UserDetail;
import com.restful.app.entity.UserDetail_;
import com.restful.app.entity.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao extends AGenericDao<User> implements IUserDao {
    public UserDao() {
        super(User.class);
    }

    private EntityGraph<User> createEntityGraph() {
        EntityGraph<User> entityGraph = entityManager.createEntityGraph(User.class);
        entityGraph.addAttributeNodes(User_.roles);
        entityGraph.addAttributeNodes(User_.userDetails);
        entityGraph.addAttributeNodes(User_.ratings);
        entityGraph.addAttributeNodes(User_.requests);
        return entityGraph;
    }

    public boolean isUserExist(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(getGenericClass());
        Root<User> userRoot = query.from(User.class);
        query.select(userRoot).where(builder.equal(userRoot.get(User_.email), email));
        TypedQuery<User> result = entityManager.createQuery(query);
        return result.getResultList().stream().findFirst().isPresent();
    }

    public boolean isUserExist(int id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(getGenericClass());
        Root<User> userRoot = query.from(User.class);
        query.select(userRoot).where(builder.equal(userRoot.get(User_.id), id));
        TypedQuery<User> result = entityManager.createQuery(query);
        return result.getResultList().stream().findFirst().isPresent();
    }

    public User findUserByEmail(String email) throws NoResultException {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(getGenericClass());
            Root<User> userRoot = query.from(User.class);
            query.select(userRoot).where(builder.equal(userRoot.get(User_.email), email));
            TypedQuery<User> result = entityManager.createQuery(query);
            result.setHint("javax.persistence.fetchgraph", createEntityGraph());
            return result.getResultList().stream().findFirst().orElse(null);
        } catch (NoResultException e) {
            throw new NoResultException(e.getMessage());
        }
    }

    public List<User> findUsersBySearchRequest(String seacrh) {
        String[] searchWords = seacrh.replaceAll("[\\p{P}]", " ").split(" +");
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(getGenericClass());
            Root<User> userRoot = query.from(User.class);
            Join<User, UserDetail> userDetailJoin = userRoot.join(User_.userDetails);
            List<Predicate> predicates = new ArrayList<>();
            CriteriaBuilder.Case<Object> objectCase = builder.selectCase();
            for (String searchWord : searchWords) {
                Predicate predicate = builder.or(builder.like(userRoot.get(User_.email), "%" + searchWord + "%"),
                        builder.like(userRoot.get(User_.firstName), "%" + searchWord + "%"),
                        builder.like(userRoot.get(User_.lastName), "%" + searchWord + "%"),
                        builder.like(userDetailJoin.get(UserDetail_.passportNumber), "%" + searchWord + "%"));
                predicates.add(predicate);
                objectCase = builder.selectCase()
                        .when(builder.like(userRoot.get(User_.email), "%" + searchWord + "%"), 2)
                        .when(builder.like(userRoot.get(User_.lastName), "%" + searchWord + "%"), 1)
                        .when(builder.like(userRoot.get(User_.firstName), "%" + searchWord + "%"), 3);
            }
            Predicate finalPredicate = builder.or(predicates.toArray(new Predicate[0]));
            Order order = builder.desc(objectCase);
            query.select(userRoot).where(finalPredicate).orderBy(order);
            TypedQuery<User> result = entityManager.createQuery(query);
            return result.getResultList();
        } catch (NoResultException e) {
            throw new NoResultException(e.getMessage());
        }
    }
}
