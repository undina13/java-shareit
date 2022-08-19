package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

public class UserTestDats {
    public static final UserDto userDto1 =
            UserDto.builder().id(1L).name("user1").email("user1@mail.ru").build();
    public static final UserDto userDto2 =
            UserDto.builder().id(2L).name("user2").email("user2@mail.ru").build();
    public static final UserDto userDtoCreated =
            UserDto.builder().id(3L).name("userCreated").email("userCreated@mail.ru").build();
}
