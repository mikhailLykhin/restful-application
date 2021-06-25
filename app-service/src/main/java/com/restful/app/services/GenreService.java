package com.restful.app.services;

import com.restful.app.api.dao.IGenreDao;
import com.restful.app.api.dto.GenreDto;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.IGenreService;
import com.restful.app.entity.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService implements IGenreService {

    private final IGenreDao genreDao;
    private final CommonMapper commonMapper;

    public GenreService(IGenreDao genreDao, CommonMapper commonMapper) {
        this.genreDao = genreDao;
        this.commonMapper = commonMapper;
    }

    @Override
    @Transactional
    public GenreDto getGenreById(long id) {
        return commonMapper.map(this.genreDao.getGenreById(id), GenreDto.class);
    }

    @Override
    @Transactional
    public boolean isGenreExist(String name) {
        return this.genreDao.isGenreExistByName(name);
    }

    @Override
    @Transactional
    public void addGenre(GenreDto genreDto) {
        Genre genre = Genre.builder().name(genreDto.getName()).build();
        this.genreDao.create(genre);

    }

    @Override
    @Transactional
    public List<GenreDto> getAllGenresOrderByName() {
        return commonMapper.mapAll(this.genreDao.findAllGenresOrderByName(), GenreDto.class);
    }

    @Override
    @Transactional
    public void deleteGenre(long id) {
        this.genreDao.delete(this.genreDao.getGenreById(id));
    }

}
