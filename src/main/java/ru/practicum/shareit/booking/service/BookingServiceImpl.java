package ru.practicum.shareit.booking.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.exception.BookingNotChangeStatusException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repositiory.BookingRepository;
import ru.practicum.shareit.item.exception.ItemNotAvalibleException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Booking create(long userId, long itemId, Booking booking) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("item not found"));
        if (!(item.getAvailable())) {
            throw new ItemNotAvalibleException("item not availible");
        }
        if (item.getOwner().getId() == userId) {
            throw new UserIsNotOwnerException("you can not booking this item");
        }
        booking.setItem(item);
        booking.setBooker(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found")));
        booking.setStatus(Status.WAITING);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking approveStatus(long userId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("booking not found"));
        if (!(booking.getStatus().equals(Status.WAITING))) {
            throw new BookingNotChangeStatusException("status can not be change");
        }
        if (userId != booking.getItem().getOwner().getId()) {
            throw new UserIsNotOwnerException("user not owner this item and can not approve status");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("booking not found"));
        if (userId != booking.getItem().getOwner().getId() && userId != booking.getBooker().getId()) {
            throw new UserIsNotOwnerException("user not owner this item and can not get this booking");
        }
        return booking;
    }

    @Override
    public List<Booking> getBookingCurrentUser(long userId) {
        User booker = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        return bookingRepository.findAllByBooker(booker);
    }

    @Override
    public List<Booking> getBookingCurrentOwner(long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        return bookingRepository.findAllByItemOwner(owner);
    }

}
