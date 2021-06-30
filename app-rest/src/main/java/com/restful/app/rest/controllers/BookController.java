package com.restful.app.rest.controllers;

import com.restful.app.api.dto.BookDto;
import com.restful.app.api.dto.RatingDto;
import com.restful.app.api.services.IBookService;
import com.restful.app.api.services.IGenreService;
import com.restful.app.api.services.IRatingService;
import com.restful.app.api.services.IRequestService;
import com.restful.app.entity.Book;
import com.restful.app.utils.LogoFileUploader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping()
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books/typed")
    public List<BookDto> findBooksTyped() {
        return this.bookService.findAllBooksTypedQuery();
    }

    @GetMapping("/books/book/{isbn}")
    public BookDto findBook(@PathVariable("isbn") String isbn) {
        return this.bookService.getBookByIsbn(isbn);
    }

    @GetMapping("/books")
    public Page<BookDto> getBooks(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                           @RequestParam(value = "size", defaultValue = "5") int pageSize) {
        return this.bookService.getAllBooksWithAvgRating(pageNumber, pageSize);
    }

    @GetMapping("/books/s")
    public Page<BookDto> getBooksWithSearchOrOrderBy(@RequestParam(value = "genre", required = false) String genre,
                                                  @RequestParam(value = "search", required = false) String search,
                                                  @RequestParam(value = "orderBy", required = false) String orderBy,
                                                     @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                                     @RequestParam(value = "size", defaultValue = "5") int pageSize) {
        if (!search.isEmpty()) {
            return this.bookService.getAllBooksBySearchAndOrderByRequestWithAvgRating(genre, search, orderBy, pageNumber, pageSize);
        }

        return this.bookService.getAllBooksOrderByRequestWithAvgRating(orderBy, genre, pageNumber, pageSize);
    }
}
