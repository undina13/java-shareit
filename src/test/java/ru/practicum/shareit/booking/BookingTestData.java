package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoState;
import ru.practicum.shareit.booking.dto.BookingDtoToUser;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.shareit.item.ItemTestData.item1;
import static ru.practicum.shareit.user.UserTestData.user2;

public class BookingTestData {
    public final static BookingDtoToUser bookingDtoToUser1 = BookingDtoToUser.builder().id(1L)
            .start(LocalDateTime.of(2022, 8, 1, 12, 15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .end(LocalDateTime.of(2022, 8, 10, 12, 15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .item(item1).booker(user2)
            .status(Status.APPROVED).build();
    public final static BookingDtoToUser bookingDtoToUser2 = BookingDtoToUser.builder().id(2L)
            .start(LocalDateTime.of(2023, 8, 1, 12, 15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .end(LocalDateTime.of(2023, 8, 10, 12, 15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .item(item1).booker(user2)
            .status(Status.APPROVED).build();
    public final static BookingDtoToUser bookingDtoToUserCreated = BookingDtoToUser.builder().id(3L)
            .start(LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .end(LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .item(item1).booker(user2)
            .status(Status.WAITING).build();

    public final static BookingDto createdDto = BookingDto.builder().id(3L)
            .start(LocalDateTime.now().plusDays(2))
            .end(LocalDateTime.now().plusDays(3))
            .itemId(1L)
            .booker(2L)
            .build();

    public final static Booking booking1 = Booking.builder().id(1L)
            .start(LocalDateTime.of(2022, 8, 1, 12, 15))
            .end(LocalDateTime.of(2022, 8, 10, 12, 15))
            .item(item1).booker(user2).status(Status.APPROVED).build();

    public final static BookingDtoState bookingDtoState = BookingMapper.toBookingDtoState(booking1);

    public final static BookingDtoState bookingDtoState1 = BookingDtoState.builder()
            .id(1L).start(LocalDateTime.of(2022, 8, 1, 12, 15))
            .end(LocalDateTime.of(2022, 8, 10, 12, 15))
            .item(item1).booker(user2).status(Status.APPROVED).states(List.of(State.ALL, State.PAST))
            .build();

    public final static Booking booking2 = Booking.builder().id(1L)
            .start(LocalDateTime.of(2022, 9, 1, 12, 15))
            .end(LocalDateTime.of(2022, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.WAITING).build();

    public final static BookingDtoState bookingDtoState2 = BookingDtoState.builder()
            .id(1L).start(LocalDateTime.of(2022, 9, 1, 12, 15))
            .end(LocalDateTime.of(2022, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.WAITING).states(List.of(State.ALL, State.WAITING, State.FUTURE))
            .build();

    public final static Booking booking3 = Booking.builder().id(1L)
            .start(LocalDateTime.of(2022, 9, 1, 12, 15))
            .end(LocalDateTime.of(2022, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.APPROVED).build();

    public final static BookingDtoState bookingDtoState3 = BookingDtoState.builder()
            .id(1L).start(LocalDateTime.of(2022, 9, 1, 12, 15))
            .end(LocalDateTime.of(2022, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.APPROVED).states(List.of(State.ALL,  State.FUTURE))
            .build();

    public final static Booking booking4 = Booking.builder().id(1L)
            .start(LocalDateTime.of(2022, 8, 1, 12, 15))
            .end(LocalDateTime.of(2023, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.APPROVED).build();

    public final static BookingDtoState bookingDtoState4 = BookingDtoState.builder()
            .id(1L).start(LocalDateTime.of(2022, 8, 1, 12, 15))
            .end(LocalDateTime.of(2023, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.APPROVED).states(List.of(State.ALL,  State.CURRENT))
            .build();

    public final static Booking booking5 = Booking.builder().id(1L)
            .start(LocalDateTime.of(2022, 9, 1, 12, 15))
            .end(LocalDateTime.of(2022, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.REJECTED).build();

    public final static BookingDtoState bookingDtoState5 = BookingDtoState.builder()
            .id(1L).start(LocalDateTime.of(2022, 9, 1, 12, 15))
            .end(LocalDateTime.of(2022, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.REJECTED).states(List.of(State.ALL,  State.REJECTED, State.FUTURE))
            .build();

    public final static Booking booking6 = Booking.builder().id(1L)
            .start(LocalDateTime.of(2022, 9, 1, 12, 15))
            .end(LocalDateTime.of(2022, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.CANCELED).build();

    public final static BookingDtoState bookingDtoState6 = BookingDtoState.builder()
            .id(1L).start(LocalDateTime.of(2022, 9, 1, 12, 15))
            .end(LocalDateTime.of(2022, 9, 10, 12, 15))
            .item(item1).booker(user2).status(Status.CANCELED).states(List.of(State.ALL,  State.FUTURE))
            .build();
}