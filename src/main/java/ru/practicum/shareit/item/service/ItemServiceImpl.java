package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repositiory.BookingRepository;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemForOwnerDto;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public Item getItemById( Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("item not found"));

    }

    @Override
    public List<ItemForOwnerDto> getAllItemsByUser(long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));

       List<ItemForOwnerDto>items = itemRepository.findByOwner(owner)
               .stream()
               .map(this::setLastAndNextBookingDate)
               .collect(Collectors.toList());
        return items;
    }

    @Override
    public List<Item> search(String text) {
        if (text.equals("")) {
            return new ArrayList<>();
        }
        return itemRepository.search(text);
    }

    public ItemForOwnerDto setLastAndNextBookingDate(Item item){
        List<Booking> bookings = bookingRepository.findAllByItem(item);
        ItemForOwnerDto itemForOwnerDto = ItemMapper.toItemForOwnerDto(item);
        Booking last = bookings.stream()
                .filter(booking -> booking.getStart().isBefore(LocalDateTime.now()))
                .max((booking, booking1) -> booking1.getStart().compareTo(booking.getStart()))
                .orElse(null)
                ;

        Booking next = bookings.stream()
                .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                .min((booking, booking1) -> booking.getStart().compareTo(booking1.getStart()))
                .orElse(null)
                ;
        itemForOwnerDto.setLastBooking(last);
        itemForOwnerDto.setNextBooking(next);
        return itemForOwnerDto;
    }
}
