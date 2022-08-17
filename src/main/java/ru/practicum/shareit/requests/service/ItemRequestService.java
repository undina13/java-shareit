package ru.practicum.shareit.requests.service;

import org.springframework.data.domain.Page;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.List;

public interface ItemRequestService {
    RequestDto create(long userId, RequestDto itemRequestDto);

    List<ItemRequestDto> getByRequestor(long userId);

    Page<ItemRequest> getAll(long userId, int from, int size);

    ItemRequestDto getById(long userId, long requestId);
}
