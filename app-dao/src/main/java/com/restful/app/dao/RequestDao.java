package com.restful.app.dao;

import com.restful.app.api.dao.IRequestDao;
import com.restful.app.api.enums.RequestStatusName;
import com.restful.app.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestDao extends AGenericDao<Request> implements IRequestDao {
    public RequestDao() {
        super(Request.class);
    }

    public boolean isRequestExistForCurrentBookFromUser(String isbn, String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> query = builder.createQuery(getGenericClass());
        Root<Request> requestRoot = query.from(Request.class);
        Join<Request, Book> bookJoin = requestRoot.join(Request_.book);
        Join<Request, User> userJoin = requestRoot.join(Request_.user);
        Predicate predicate = builder.or(builder.equal(requestRoot.get(Request_.status), RequestStatusName.CONFIRMED.getNameDB()),
                builder.equal(requestRoot.get(Request_.status), RequestStatusName.CREATED.getNameDB()),
                builder.equal(requestRoot.get(Request_.status), RequestStatusName.PROCESSED.getNameDB()));
        query.select(requestRoot).where(builder.and(builder.equal(bookJoin.get(Book_.isbn), isbn),
                builder.equal(userJoin.get(User_.email), email),
                predicate));
        TypedQuery<Request> result = entityManager.createQuery(query);
        return result.getResultList().stream().findFirst().isPresent();
    }

    public List<Request> findAllCreatedRequestsFromUserByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> query = builder.createQuery(getGenericClass());
        Root<Request> requestRoot = query.from(Request.class);
        Join<Request, User> userJoin = requestRoot.join(Request_.user);
        Predicate predicate = builder.equal(requestRoot.get(Request_.status), RequestStatusName.CREATED.getNameDB());
        query.select(requestRoot).where(builder.and(builder.equal(userJoin.get(User_.email), email),
                predicate));
        TypedQuery<Request> result = entityManager.createQuery(query);
        return result.getResultList();
    }

    public List<Request> findAllConfirmedRequestsFromUserByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> query = builder.createQuery(getGenericClass());
        Root<Request> requestRoot = query.from(Request.class);
        Join<Request, User> userJoin = requestRoot.join(Request_.user);
        Predicate predicate = builder.or(builder.equal(requestRoot.get(Request_.status), RequestStatusName.CONFIRMED.getNameDB()),
                builder.equal(requestRoot.get(Request_.status), RequestStatusName.PROCESSED.getNameDB()));
        query.select(requestRoot).where(builder.and(builder.equal(userJoin.get(User_.email), email),
                predicate));
        TypedQuery<Request> result = entityManager.createQuery(query);
        return result.getResultList();
    }

    public List<Request> findAllRequests(String status) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> query = builder.createQuery(getGenericClass());
        Root<Request> requestRoot = query.from(Request.class);
//        Join<Request, User> userJoin = requestRoot.join(Request_.user);
        Predicate predicate = builder.equal(requestRoot.get(Request_.status), status);
        query.select(requestRoot).where(predicate);
        TypedQuery<Request> result = entityManager.createQuery(query);
        return result.getResultList();
    }

    public List<Request> findAllRequestsBySearch(String status, String search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> query = builder.createQuery(getGenericClass());
        Root<Request> requestRoot = query.from(Request.class);
        Join<Request, Book> bookJoin = requestRoot.join(Request_.book);
        Join<Request, User> userJoin = requestRoot.join(Request_.user);
        Join<Book, Author> bookAuthorJoin = bookJoin.join(Book_.authors);
        Join<User, UserDetail> userDetailJoin = userJoin.join(User_.userDetails);
        List<Predicate> predicates = new ArrayList<>();
        String[] requestWords = search.replaceAll("[\\p{P}]", " ").split(" +");
        for (String requestWord : requestWords) {
            Predicate predicate = builder.or(builder.like(bookJoin.get(Book_.isbn), "%" + requestWord + "%"),
                    builder.like(userJoin.get(User_.lastName), "%" + requestWord + "%"),
                    builder.like(userDetailJoin.get(UserDetail_.passportNumber), "%" + requestWord + "%"),
                    builder.like(bookJoin.get(Book_.name), "%" + requestWord + "%"),
            builder.like(bookAuthorJoin.get(Author_.name), "%" + requestWord + "%"));
            predicates.add(predicate);
        }
        Predicate searchPredicate = builder.or(predicates.toArray(new Predicate[0]));
        Predicate genrePredicate = builder.equal(requestRoot.get(Request_.status), status);
        query.select(requestRoot).where(searchPredicate, genrePredicate).groupBy(requestRoot.get(Request_.id));
        return entityManager.createQuery(query).getResultList();
    }
}
