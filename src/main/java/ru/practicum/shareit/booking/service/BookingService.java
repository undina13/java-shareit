package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;

import java.util.Collection;
import java.util.List;

public interface BookingService {
     Booking create(long userId, long itemId, Booking booking);

     Booking approveStatus(long userId, long bookingId, boolean approved);

     Booking getBookingById(long userId, long bookingId);

    List<Booking> getBookingCurrentUser(long userId);

    List<Booking> getBookingCurrentOwner(long userId);
}
