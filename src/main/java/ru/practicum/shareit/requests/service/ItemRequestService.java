package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.RequestDto;

import java.util.List;

public interface ItemRequestService {
    RequestDto create(long userId, RequestDto itemRequestDto);

    List<ItemRequestDto> getByRequestor(long userId);

    List<ItemRequestDto> getAll(long userId, int from, int size);

    ItemRequestDto getById(long userId, long requestId);
}
