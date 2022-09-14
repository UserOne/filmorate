package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private int id;
    private final HashMap<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        log.info("Получен GET запрос на получение списка всех пользователей");
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен POST запрос на создание пользователя");
        checkLogin(user);
        checkName(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь успешно создан");
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен PUT запрос на изменение ползователя");
        if (!users.containsKey(user.getId())) {
            log.warn("Указанного Id пользователя не существует");
            throw new ValidationException("Указанного Id пользователя не существует");
        }
        checkLogin(user);
        checkName(user);
        users.put(user.getId(), user);
        log.info("Пользователь успешно изменён");
        return user;
    }

    private boolean checkLogin(User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("В имени пользователя присутствует пробел");
            throw new ValidationException("Логин содержит пробельный символ");
        }
        return true;
    }

    private boolean checkName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            return false;
        }
        return true;
    }

    private int generateId() {
        return ++id;
    }
}
