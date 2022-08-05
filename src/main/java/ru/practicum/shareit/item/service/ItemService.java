package ru.practicum.shareit.item.service;

import ru.practicum.shareit.booking.model.BookingForItem;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item create(Long userId, Item item);

    Item update(long userId, Long itemId, Item item);

    Item getItemById(Long itemId);

    List<Item> getAllItemsByUser(long userId);

    List<Item> search(String text);

    List<BookingForItem> setLastAndNextBookingDate(Item item);

    Comment createComment(long userId, long itemId, Comment comment);

    List<Comment>findCommentsByItem(long itemId);
}
