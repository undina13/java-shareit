package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Autowired
    public ItemServiceImpl(ItemStorage itemStorage, UserStorage userStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
    }

    @Override
    public ItemDto create(Long userId, ItemDto itemDto) {

        Item item = ItemMapper.toItem(itemDto);
        if (userStorage.getUserById(userId) == null) {
            throw new UserNotFoundException("User not found");
        }

        item.setOwner(userStorage.getUserById(userId));
        itemStorage.create(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(long userId, Long itemId, ItemDto itemDto) {
        if (userStorage.getUserById(userId) == null
                || itemStorage.getItemById(itemId) == null
                || !itemStorage.getItemById(itemId).getOwner().getId().equals(userId)) {
            throw new UserIsNotOwnerException("Вы пытаетесь изменить чужую вещь");
        }
        Item item = itemStorage.getItemById(itemId);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getRequest() != null) {
            item.setRequest(itemDto.getRequest());
        }
        itemStorage.update(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        Item item = itemStorage.getItemById(itemId);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAllItemsByUser(long userId) {
        if (userStorage.getUserById(userId) == null) {
            throw new UserNotFoundException("Нет такого юзера");
        }
        return itemStorage.getAllItems()
                .stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.equals("")) {
            return new ArrayList<>();
        }

        Predicate<Item> inName = item -> item.getName().toLowerCase().contains(text.toLowerCase());
        Predicate<Item> inDesc = item -> item.getDescription().toLowerCase().contains(text.toLowerCase());
        return itemStorage.getAllItems()
                .stream()
                .filter(inName.or(inDesc))
                .filter(Item::isAvailable)
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
