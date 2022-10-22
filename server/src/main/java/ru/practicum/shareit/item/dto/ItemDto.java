package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoItem;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    long id;

    String name;

    String description;

    Boolean available;

    Long requestId;

    BookingDtoItem nextBooking;

    BookingDtoItem lastBooking;

    List<CommentDto> comments;
}
