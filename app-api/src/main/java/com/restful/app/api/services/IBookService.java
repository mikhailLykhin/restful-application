package com.restful.app.api.services;

import com.restful.app.api.dto.BookDto;
import com.restful.app.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookService {

    List<BookDto> findAllBooksTypedQuery();

    BookDto getBookById(long id);

    void deleteBook(long id);

    BookDto getBookByIsbn(String isbn);

    Page<BookDto> getAllBooksOrderByDateOfCreation(int pageNumber, int pageSize);

    Page<BookDto> getAllBooksOrderByRequestWithAvgRating(String orderBy, String genre, int pageNumber, int pageSize);

    Page<BookDto> getAllBooksWithAvgRating(int pageNumber, int pageSize);

    void addBook(BookDto bookDto);

    List<Integer> getTotalPages(Page<BookDto> page);

    Page<BookDto> getAllBooksBySearchAndOrderByRequestWithAvgRating(String search, String genre, String orderBy, int pageNumber, int pageSize);









}
