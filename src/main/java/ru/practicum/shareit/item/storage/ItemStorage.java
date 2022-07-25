package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item create(Item item);

    Item getItemById(Long itemId);

    Item update(Item item);

    List<Item> getAllItems();
}
