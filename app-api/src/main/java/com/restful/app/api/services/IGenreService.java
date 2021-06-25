package com.restful.app.api.services;

import com.restful.app.api.dto.GenreDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IGenreService {

    void deleteGenre(long id);

    GenreDto getGenreById(long id);

    boolean isGenreExist(String name);

    List<GenreDto> getAllGenresOrderByName();

    void addGenre(GenreDto genreDto);

}
