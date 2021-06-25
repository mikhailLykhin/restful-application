package com.restful.app.api.dao;

import com.restful.app.entity.Author;


public interface IAuthorDao extends IAGenericDao<Author> {

    boolean isAuthorExist(String name);

    Author getByName(String name);

}
