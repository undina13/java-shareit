package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemForOwnerDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item create(Long userId, Item item);

    Item update(long userId, Long itemId, Item item);

    Item getItemById(Long itemId);

    List<ItemForOwnerDto> getAllItemsByUser(long userId);

    List<Item> search(String text);

     ItemForOwnerDto setLastAndNextBookingDate( Item item);
}
