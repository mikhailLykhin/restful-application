package com.restful.app.rest.controllers;

import com.restful.app.api.dto.BookDto;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminBookController {

    private final IBookService bookService;

    @Autowired
    public AdminBookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/addbook")
    public ResponseEntity<HttpStatus> addBook( @RequestBody @Valid  BookDto book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectDataException("Please choose genre");
        }
        this.bookService.addBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public BookDto deleteBook(@PathVariable("id") long id) {
        BookDto test = this.bookService.getBookById(id);
        return test;
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
        this.bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/books")
    public Page<BookDto> getBooks(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                  @RequestParam(value = "size", defaultValue = "5") int pageSize) {
        return this.bookService.getAllBooksOrderByDateOfCreation(pageNumber, pageSize);
    }

    @GetMapping("/books/s")
    public Page<BookDto> getBooksBySearch(@RequestParam(value = "search") String search,
                                   @RequestParam(value = "genre") String genre,
                                   @RequestParam(value = "orderBy", required = false) String orderBy,
                                   @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                   @RequestParam(value = "size", defaultValue = "5") int pageSize) {
        return this.bookService.getAllBooksBySearchAndOrderByRequestWithAvgRating(search, genre, orderBy, pageNumber, pageSize);
    }


}
