package com.restful.app.services;


import com.restful.app.api.constants.BookDetailsNames;
import com.restful.app.api.dao.IAuthorDao;
import com.restful.app.api.dao.IBookDao;
import com.restful.app.api.dao.IGenreDao;
import com.restful.app.api.dao.IPublisherDao;
import com.restful.app.api.dto.BookDto;
import com.restful.app.api.dto.RatingDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.IBookService;
import com.restful.app.entity.Author;
import com.restful.app.entity.Book;
import com.restful.app.entity.Publisher;
import com.restful.app.utils.PaginationUtil;
import com.restful.app.web.WebScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService implements IBookService {

    private final IBookDao bookDao;
    private final IGenreDao genreDao;
    private final IAuthorDao authorDao;
    private final IPublisherDao publisherDao;
    private final WebScraper webScraper;
    private final CommonMapper commonMapper;

    public BookService(IBookDao bookDao, IGenreDao genreDao, IAuthorDao authorDao, IPublisherDao publisherDao, WebScraper webScraper, CommonMapper commonMapper) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.authorDao = authorDao;
        this.publisherDao = publisherDao;
        this.webScraper = webScraper;
        this.commonMapper = commonMapper;
    }

    @Override
    @Transactional
    public BookDto getBookById(long id) {
        return commonMapper.map(this.bookDao.get(id), BookDto.class);
    }

    @Override
    @Transactional
    public BookDto getBookByIsbn(String isbn) {
        BookDto bookDto = commonMapper.map(this.bookDao.findBookByIsbnWithAvgRating(isbn).get(0, Book.class),BookDto.class);
            bookDto.setAverageRating(this.bookDao.findBookByIsbnWithAvgRating(isbn).get(1, Double.class));
        if (bookDto.getRatings().isEmpty()) {
            return bookDto;
        }
        bookDto.setRatings(bookDto
                .getRatings()
                .stream()
                .sorted(Comparator.comparing(RatingDto::getDateOfpost, Comparator.reverseOrder()))
                .collect(Collectors.toList()));
        return bookDto;
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        this.bookDao.delete(this.bookDao.get(id));
    }

    @Override
    @Transactional
    public Page<BookDto> getAllBooksOrderByDateOfCreation(int pageNumber, int pageSize) {
        List<BookDto> allBooks = commonMapper.mapAll(this.bookDao.findAllBooksOrderByDateOfCreation(), BookDto.class);
        return PaginationUtil.getPageBookDto(allBooks, PageRequest.of(pageNumber - 1, pageSize));
    }

    @Override
    @Transactional
    public  Page<BookDto> getAllBooksWithAvgRating(int pageNumber, int pageSize) {
        List<BookDto> allBooks = parseTupleToListBookDto( this.bookDao.findAllBooksWithAvgRating());
        return PaginationUtil.getPageBookDto(allBooks, PageRequest.of(pageNumber - 1, pageSize));
    }

    @Override
    @Transactional
    public Page<BookDto> getAllBooksOrderByRequestWithAvgRating(String orderBy, String genre, int pageNumber, int pageSize) {
        List<BookDto> allBooks = parseTupleToListBookDto(this.bookDao.findAllBooksOrderByRequestWithAvgRating(orderBy, genre));
        return PaginationUtil.getPageBookDto(allBooks, PageRequest.of(pageNumber - 1, pageSize));
    }

    @Override
    @Transactional
    public Page<BookDto> getAllBooksBySearchAndOrderByRequestWithAvgRating(String search, String genre, String orderBy,
                                                                           int pageNumber, int pageSize) {
      List<BookDto> allBooks = parseTupleToListBookDto(this.bookDao.findAllBooksBySearchAndOrderByRequestWithAvgRating(search, genre, orderBy));
        return PaginationUtil.getPageBookDto(allBooks, PageRequest.of(pageNumber - 1, pageSize));
    }

    @Override
    public List<Integer> getTotalPages(Page<BookDto> page) {
        return PaginationUtil.getListOfPageNumbers(page);
    }

    @Override
    @Transactional
    public void addBook(BookDto bookDto) {
        final int DEFAULT_QUANTITY = 1;
        if (this.bookDao.isBookExistByIsbn(bookDto.getIsbn())) {
            Book book = this.bookDao.findBookByIsbn(bookDto.getIsbn());
            book.setQuantity(book.getQuantity() + DEFAULT_QUANTITY);
        } else {
            Map<String, String> bookDetails = webScraper.getBookDetailsFromWeb(bookDto.getIsbn());
            Book book = new Book();
            book.setGenre(this.genreDao.getGenreByName(bookDto.getGenre().getName()));
            book.setIsbn(bookDto.getIsbn());
            book.setName(bookDetails.get(BookDetailsNames.NAME));
            book.setPicture(bookDetails.get(BookDetailsNames.PICTURE));
            book.setDescription(bookDetails.get(BookDetailsNames.DESCRIPTION));
            book.setQuantity(DEFAULT_QUANTITY);
            book.setDateOfCreation(LocalDateTime.now());
            setAuthorToBook(book, bookDetails);
            setPublisherAndYearOfPublishingToBook(book, bookDetails);

            this.bookDao.create(book);
        }
    }

    private void setPublisherAndYearOfPublishingToBook(Book book, Map<String, String> bookDetails) {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern("yyyy")
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        book.setYearOfPublishing(LocalDate.parse(bookDetails.get(BookDetailsNames.YEAR_OF_PUBLISHING), format));
        if (this.publisherDao.isPublisherExist(bookDetails.get(BookDetailsNames.PUBLISHER))) {
            Publisher publisher = this.publisherDao.getByName(bookDetails.get(BookDetailsNames.PUBLISHER));
            book.setPublisher(publisher);
        } else {
            Publisher publisher = Publisher.builder().name(bookDetails.get(BookDetailsNames.PUBLISHER)).build();
            book.setPublisher(publisher);
        }
    }

    private void setAuthorToBook(Book book, Map<String, String> bookDetails) {
        int nameCounter = 0;
        if (bookDetails.containsKey(BookDetailsNames.AUTHOR + nameCounter)) {
            for (int i = 0; i < Integer.parseInt(bookDetails.get(BookDetailsNames.AUTHORS_NAMES_COUNTER)); i++) {
                if (this.authorDao.isAuthorExist(bookDetails.get(BookDetailsNames.AUTHOR + i))) {
                    Author author = this.authorDao.getByName(bookDetails.get(BookDetailsNames.AUTHOR + i));
                    book.getAuthors().add(author);
                } else {
                    Author author = new Author();
                    author.setName(bookDetails.get(BookDetailsNames.AUTHOR + i));
                    book.getAuthors().add(author);
                }
            }
        }

    }

    private List<BookDto> parseTupleToListBookDto(List<Tuple> tuples) {
        List<BookDto> booksDto = new ArrayList<>();
        int currentAddBookIndex = 0;
        for (Tuple bookWithAvgRating : tuples) {
            booksDto.add(commonMapper.map(bookWithAvgRating.get(0, Book.class), BookDto.class));
            booksDto.get(currentAddBookIndex).setAverageRating(bookWithAvgRating.get(1, Double.class));
            currentAddBookIndex++;
        }
        return booksDto;
    }

}
