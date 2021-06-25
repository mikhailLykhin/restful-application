package com.restful.app.api.dao;

import com.restful.app.entity.Publisher;

public interface IPublisherDao extends IAGenericDao<Publisher>{

    Publisher getByName(String name);

    boolean isPublisherExist(String name);
}
