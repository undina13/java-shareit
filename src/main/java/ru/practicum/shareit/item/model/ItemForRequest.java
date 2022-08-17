package ru.practicum.shareit.item.model;

import ru.practicum.shareit.user.model.User;

public interface ItemForRequest {
    long getId();

    String getName();

    User getOwner();
}
