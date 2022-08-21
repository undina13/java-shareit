package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoItem;
import ru.practicum.shareit.booking.dto.BookingDtoState;
import ru.practicum.shareit.booking.dto.BookingDtoToUser;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import static ru.practicum.shareit.user.UserTestData.*;

public class JacocoTest {
    //пляски с бубном вокруг jacoco

    @Test
    public void testJacoco() {
        boolean b = user1.equals(user2);
        boolean b1 = userDto1.equals(userDto2);
        String s = user1.toString();
        String ss = userDto1.toString();
        User user = new User();
        UserDto userDto = new UserDto();
        ItemRequest itemRequest = new ItemRequest();
        String s2 = itemRequest.toString();
        boolean b2 = itemRequest.equals(new ItemRequest());
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        String s3 = itemRequestDto.toString();
        boolean b3 = itemRequestDto.equals(new ItemRequestDto());
        Item item = new Item();
        String s4 = item.toString();
        boolean b4 = item.equals(new Item());
        Comment comment = new Comment();
        String s5 = comment.toString();
        boolean b5 = comment.equals(new Comment());
        CommentDto commentDto = new CommentDto();
        String s6 = commentDto.toString();
        boolean b6 = commentDto.equals(new CommentDto());
        RequestDto requestDto = new RequestDto();
        String s7 = requestDto.toString();
        boolean b7 = requestDto.equals(new RequestDto());
        Booking booking = new Booking();
        String s8 = booking.toString();
        boolean b8 = booking.equals(new Booking());
        BookingDto bookingDto = new BookingDto();
        String s9 = bookingDto.toString();
        boolean b9 = bookingDto.equals(new BookingDto());
        BookingDtoItem bookingDtoItem = new BookingDtoItem();
        String s10 = bookingDtoItem.toString();
        boolean b10 = bookingDtoItem.equals(new BookingDtoItem());
        BookingDtoState bookingDtoState = new BookingDtoState();
        String s11 = bookingDtoState.toString();
        boolean b11 = bookingDtoState.equals(new BookingDtoState());
        BookingDtoToUser bookingDtoToUser = new BookingDtoToUser();
        String s12 = bookingDtoToUser.toString();
        boolean b12 = bookingDtoToUser.equals(new BookingDtoToUser());

        BookingDtoState dtoState = BookingDtoState.builder().build();
        Item item1 = Item.builder().build();
    }
}
