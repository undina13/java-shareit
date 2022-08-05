package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.BookingForItem;
import ru.practicum.shareit.booking.repositiory.BookingRepository;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Item create(Long userId, Item item) {
        item.setOwner(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found")));
        return itemRepository.save(item);
    }

    @Override
    public Item update(long userId, Long itemId, Item item) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        // нельзя изменить вещь, если ее нет в хранилище  или нет такого пользователя или вещь чужая
        if (!owner.equals(itemRepository.findById(itemId).get().getOwner())) {
            throw new UserIsNotOwnerException("Вы пытаетесь изменить чужую вещь");
        }
        return itemRepository.save(item);
    }

    @Override
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("item not found"));
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
        return itemRepository.search(text);
    }

    public List<BookingForItem> setLastAndNextBookingDate(Item item) {
        List<BookingForItem> bookings = bookingRepository.findAllByItem(item);
        BookingForItem last = bookings.stream()
                .filter(booking -> booking.getStart().isBefore(LocalDateTime.now()))
                .max((booking, booking1) -> booking1.getStart().compareTo(booking.getStart()))
                .orElse(null);

        BookingForItem next = bookings.stream()
                .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                .min((booking, booking1) -> booking.getStart().compareTo(booking1.getStart()))
                .orElse(null);

        ArrayList<BookingForItem> bookingsList = new ArrayList<>();
        bookingsList.add(last);
        bookingsList.add(next);
        return bookingsList;
    }
}
