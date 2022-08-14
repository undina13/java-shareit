package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto create(Long userId, ItemDto itemDto);

    ItemDto update(long userId, Long itemId, ItemDto itemDto);

    ItemDto getItemById(Long userId, Long itemId);

    List<ItemDto> getAllItemsByUser(long userId);

    List<Item> search(String text);

    CommentDto createComment(long userId, long itemId, CommentDto commentDto);
}
