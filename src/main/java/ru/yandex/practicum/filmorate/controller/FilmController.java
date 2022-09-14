package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private int id;
    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final LocalDate EMERGENCE_OF_CINEMA = LocalDate.of(1895, 12, 25);

    @GetMapping
    public List<Film> findAll() {
        log.info("Получен GET запрос на получение списка всех фильмов");
        if (films.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList(films.values());
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен POST запрос на создание фильма");
        checkDate(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм успешно создан");
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен PUT запрос на изменение фильма");
        if(!films.containsKey(film.getId())){
            log.info("Фильма с указанным Id не существует");
            throw new ValidationException("Фильма с указанным Id не существует");
        }
        checkDate(film);
        films.put(film.getId(), film);
        log.info("Фильм успешно изменён");
        return film;
    }

    private boolean checkDate(Film film) {
        if (film.getReleaseDate().isBefore(EMERGENCE_OF_CINEMA)) {
            log.warn("Указана некоректная дата создания фильма");
            throw new ValidationException("Указана некоректная дата создания фильма");
        }
        return true;
    }

    private int generateId() {
        return ++id;
    }
}
