package com.restful.app.rest.controllers;

import com.restful.app.api.dto.GenreDto;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.IGenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminGenreController {

    private IGenreService genreService;

    public AdminGenreController(IGenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping(path ="/addgenre",  consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> addGenre(@RequestBody GenreDto genreDto) {
        if (this.genreService.isGenreExist(genreDto.getName())) {
            throw new IncorrectDataException("This genre already exist");
        }
        this.genreService.addGenre(genreDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/genres")
    public List<GenreDto> getGenres() {
        return this.genreService.getAllGenresOrderByName();
    }

    @GetMapping("/genres/{id}")
    public GenreDto deleteGenre(@PathVariable("id") long id) {
        return this.genreService.getGenreById(id);
    }

    @DeleteMapping("/genres/{id}")
    public ResponseEntity<HttpStatus> deleteGenre(@PathVariable("id") int id) {
        this.genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
