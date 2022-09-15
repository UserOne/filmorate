package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController controller;
    User user1 = User.builder()
            .email("user1@yandex.ru")
            .login("user1")
            .build();

    @BeforeEach
    void setUp() {
        controller = new UserController();
    }

    @Test
    void getAll() {
        assertEquals(0, controller.getAll().size(),
                "При создании контроллера список пользователей должен быть пустым");
    }

    @Test
    void createUser() {
        controller.createUser(user1);
        assertEquals(1, controller.getAll().size(), "Не удалось создать пользователя");
    }

    @Test
    void updateUser() {
        controller.createUser(user1);
        User user = controller.getAll()
                .stream()
                .collect(Collectors.toList())
                .get(0);
        String name = "user";
        user.setName(name);
        assertEquals(name, controller.getAll()
                .stream()
                .collect(Collectors.toList())
                .get(0).getName(), "Не удалось изменить пользователя");
    }
}