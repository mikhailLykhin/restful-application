package com.restful.app.services;

import com.restful.app.api.dao.IBookDao;
import com.restful.app.api.dao.IRatingDao;
import com.restful.app.api.dao.IUserDao;
import com.restful.app.api.dto.RatingDto;
import com.restful.app.api.services.IRatingService;
import com.restful.app.entity.Book;
import com.restful.app.entity.Rating;
import com.restful.app.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingService implements IRatingService {

    private final IUserDao userDao;
    private final IBookDao bookDao;
    private final IRatingDao ratingDao;

    public RatingService(IUserDao userDao, IBookDao bookDao, IRatingDao ratingDao) {
        this.userDao = userDao;
        this.bookDao = bookDao;
        this.ratingDao = ratingDao;
    }

    @Override
    @Transactional
    public void addRating(Principal principal, String isbn, RatingDto ratingDto) {
        User user = this.userDao.findUserByEmail(principal.getName());
        Book book = this.bookDao.findBookByIsbn(isbn);
        Rating rating = new Rating();
        rating.setBook(book);
        rating.setUser(user);
        rating.setRatingValue(ratingDto.getRatingValue());
        rating.setReview(ratingDto.getReview());
        rating.setDateOfpost(LocalDateTime.now());
        book.getRatings().add(rating);
        user.getRatings().add(rating);
        this.ratingDao.create(rating);
    }

    @Override
    @Transactional
    public boolean isRatingExistFromCurrentUser(String isbn, String email) {
        return this.ratingDao.isRatingExistFromCurrentUser(isbn, email);
    }

}
