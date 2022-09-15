package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController controller;
    Film film1 = Film.builder()
            .name("фильм1")
            .description("о фильме1")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(100)
            .build();

    Film film2 = Film.builder()
            .name("фильм2")
            .description("о фильме2")
            .releaseDate(LocalDate.of(1999, 2, 2))
            .duration(200)
            .build();

    @BeforeEach
    void setUp() {
        controller = new FilmController();
    }

    @Test
    void getAll() {
        assertEquals(0, controller.getAll().size(),
                "при создании контроллека список должен быть пустым");
    }

    @Test
    void createFilm() {
        controller.createFilm(film1);
        controller.createFilm(film2);
        assertEquals(2, controller.getAll().size(),
                "Фильмы не были добавлены в контроллер");
    }

    @Test
    void updateFilm() {
        controller.createFilm(film2);
        controller.createFilm(film1);
        Film oldFilm = controller.getAll()
                .stream()
                .collect(Collectors.toList())
                .get(0);
        String description = "изменённое описание фильма 2";
        Film film = Film.builder()
                .id(oldFilm.getId())
                .name(oldFilm.getName())
                .releaseDate(oldFilm.getReleaseDate())
                .duration(oldFilm.getDuration())
                .description(description)
                .build();
        controller.updateFilm(film);
        assertEquals(description,
                controller.getAll()
                        .stream()
                        .collect(Collectors.toList())
                        .get(0)
                        .getDescription(),
                "Не удалось внести изменения");
    }
}