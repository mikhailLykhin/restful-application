package com.restful.app.api.services;

import com.restful.app.api.dto.RatingDto;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface IRatingService {

    void addRating(Principal principal, String isbn, RatingDto ratingDto);

    boolean isRatingExistFromCurrentUser(String isbn, String email);

}
