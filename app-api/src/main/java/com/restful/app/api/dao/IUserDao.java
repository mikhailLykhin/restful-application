package com.restful.app.api.dao;

import com.restful.app.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao extends IAGenericDao<User> {

    boolean isUserExist(String email);

    boolean isUserExist(int id);

    User findUserByEmail(String email);

    List<User> findUsersBySearchRequest(String search);


}
