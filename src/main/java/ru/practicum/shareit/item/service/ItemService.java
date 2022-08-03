package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item create(Long userId, Item item);

    ItemDto update(long userId, Long itemId, ItemDto itemDto);

    Item getItemById(Long itemId);

    List<Item> getAllItemsByUser(long userId);

    List<Item> search(String text);
}
