package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto update(long userId, UserDto userDto);

    List<UserDto> getAllUsers();

    void delete(Long userId);

    UserDto getUserById(Long userId);
}
