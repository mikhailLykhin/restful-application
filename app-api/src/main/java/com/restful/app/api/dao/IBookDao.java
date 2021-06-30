package com.restful.app.api.dao;


import com.restful.app.entity.Book;

import javax.persistence.Tuple;
import java.util.List;

public interface IBookDao extends IAGenericDao<Book> {

    List<Book> findAllBooksTypedQuery();

    Book findBookByIsbn(String isbn);

    Tuple findBookByIsbnWithAvgRating(String isbn);

    List<Tuple> findAllBooksWithAvgRating();

    List<Book> findAllBooksOrderByDateOfCreation();

    List<Tuple> findAllBooksOrderByRequestWithAvgRating(String orderBy, String genre);

    List<Tuple> findAllBooksBySearchAndOrderByRequestWithAvgRating(String search, String genre, String orderBy);

    boolean isBookExistByIsbn(String isbn);

}
