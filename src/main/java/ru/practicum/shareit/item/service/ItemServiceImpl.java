package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Item create(Long userId, Item item) {
        item.setOwner(userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("user not found")));
        return itemRepository.save(item);
    }

    @Override
    public ItemDto update(long userId, Long itemId, ItemDto itemDto) {
        User owner = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
        // нельзя изменить вещь, если ее нет в хранилище  или нет такого пользователя или вещь чужая
        if (!owner.equals(itemRepository.findById(itemId).get().getOwner())) {
            throw new UserIsNotOwnerException("Вы пытаетесь изменить чужую вещь");
        }
        Item item = itemRepository.findById(itemId).get();
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
//        if (itemDto.getRequest() != null) {
//            item.setRequest(itemDto.getRequest());
//        }
        itemRepository.save(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    @Override
    public List<Item> getAllItemsByUser(long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        return itemRepository.findByOwner(owner);
    }

    @Override
    public List<Item> search(String text) {
        if (text.equals("")) {
            return new ArrayList<>();
        }

// предикаты для поиска в наименовании и описании
        Predicate<Item> inName = item -> item.getName().toLowerCase().contains(text.toLowerCase());
        Predicate<Item> inDesc = item -> item.getDescription().toLowerCase().contains(text.toLowerCase());

        return itemRepository.findAll()
                .stream()
                .filter(inName.or(inDesc))
                .filter(Item::isAvailable)
                .collect(Collectors.toList());
    }
}
