package com.restful.app.rest.controllers;

import com.restful.app.api.dto.RatingDto;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.IBookService;
import com.restful.app.api.services.IRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@RestController
@RequestMapping()
public class
RatingController {

    private final IRatingService ratingService;
    private final IBookService bookService;

    public RatingController(IRatingService ratingService, IBookService bookService) {
        this.ratingService = ratingService;
        this.bookService = bookService;
    }

    @PostMapping("/books/book/{isbn}")
    public ResponseEntity<HttpStatus> addRating(@PathVariable("isbn") String isbn,
                                                @RequestBody RatingDto ratingDto,
                                                Principal principal){
        if (this.ratingService.isRatingExistFromCurrentUser(isbn, principal.getName())) {
            throw new IncorrectDataException("Rating already exists from current user");
        }
        this.ratingService.addRating(principal, isbn, ratingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
