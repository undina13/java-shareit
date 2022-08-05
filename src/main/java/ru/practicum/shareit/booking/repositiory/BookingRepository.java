package ru.practicum.shareit.booking.repositiory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingForItem;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBooker(User booker);

    List<Booking> findAllByItemOwner(User owner);

    List<BookingForItem> findAllByItem(Item item);
}
