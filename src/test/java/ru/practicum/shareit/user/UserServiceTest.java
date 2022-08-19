package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.practicum.shareit.user.UserTestDats.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DirtiesContext
    void testCreate() {
        userService.create(userDtoCreated);
        List<UserDto> users = userService.getAllUsers();
        assertThat(users.size(), equalTo(3));
        assertThat(users, equalTo(List.of(userDto1, userDto2, userDtoCreated)));
    }

    @Test
    void testGetAll() {
        List<UserDto> users = userService.getAllUsers();
        assertThat(users.size(), equalTo(2));
        assertThat(users, equalTo(List.of(userDto1, userDto2)));
    }

    @Test
    @DirtiesContext
    void testSaveUpdateUser() {
        userDto2.setName("User2new");
        UserDto userDtoFromSQL = userService.update(2L, userDto2);
        assertThat(userDtoFromSQL, equalTo(userDto2));
        userDto2.setName("user2");
    }

    @Test
    @DirtiesContext
    void testDeleteUser() {
        userService.delete(1L);
        assertThat(userService.getAllUsers(), equalTo(List.of(userDto2)));
    }

    @Test
    void testGetUserById() {
        UserDto userDtoFromSQL = userService.getUserById(1L);
        assertThat(userDtoFromSQL, equalTo(userDto1));
    }

    @Test
    void testGetUserByWrongId() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(100L));
    }

}
