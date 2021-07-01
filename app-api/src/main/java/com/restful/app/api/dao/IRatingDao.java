package com.restful.app.api.dao;

import com.restful.app.entity.Rating;

import java.util.List;

public interface IRatingDao extends IAGenericDao<Rating> {

    boolean isRatingExistFromCurrentUser(String isbn, String email);

}
