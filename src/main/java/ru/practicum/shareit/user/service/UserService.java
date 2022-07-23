package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> create(UserDto userDto);

    Optional<UserDto> update(long userId, UserDto userDto);

    List<UserDto> getAllUsers();

    void delete(Long userId);

    Optional<UserDto> getUserById(Long userId);
}
