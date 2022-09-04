package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoState;
import ru.practicum.shareit.booking.dto.BookingDtoToUser;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping()
    BookingDtoToUser create(@RequestHeader("X-Sharer-User-Id") long userId,
                            @RequestBody BookingDto bookingDto) {
        log.info("create booking");
        return bookingService.create(userId, bookingDto.getItemId(), bookingDto);
    }

    @PatchMapping("/{bookingId}")
    BookingDtoToUser approveStatus(@RequestHeader("X-Sharer-User-Id") long userId,
                                   @PathVariable Long bookingId,
                                   @RequestParam boolean approved) {
        return bookingService.approveStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    BookingDtoToUser getBookingById(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @PathVariable long bookingId) {
        log.info("get booking id={}", bookingId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping()
    List<BookingDtoState> getBookingCurrentUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @RequestParam(defaultValue = "ALL") String state,
                                                @RequestParam(defaultValue = "1") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        log.info("get booking current user id ={}", userId);
        State stateEnum = State.valueOf(state.toUpperCase());
        return bookingService.getBookingCurrentUser(userId, stateEnum, from, size);
    }

    @GetMapping("/owner")
    List<BookingDtoState> getBookingCurrentOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @RequestParam(defaultValue = "ALL") String state,
                                                 @RequestParam(defaultValue = "1") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        log.info("get booking current owner id ={}", userId);
        State stateEnum = State.valueOf(state.toUpperCase());
        return bookingService.getBookingCurrentOwner(userId, stateEnum, from, size);
    }

}
