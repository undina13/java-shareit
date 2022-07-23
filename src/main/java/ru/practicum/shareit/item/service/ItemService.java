package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Optional<ItemDto> create(Long userId, ItemDto itemDto);

    Optional<ItemDto> update(long userId, Long itemId, ItemDto itemDto);

    Optional<ItemDto> getItemById(Long itemId);

    List<ItemDto> getAllItemsByUser(long userId);

    List<ItemDto> search(String text);
}
