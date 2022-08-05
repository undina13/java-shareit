package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoItem;
import ru.practicum.shareit.requests.model.ItemRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    long id;

    @NotEmpty
    String name;

    @NotEmpty
    String description;

    @NotNull
    Boolean available;

    ItemRequest request;

    BookingDtoItem nextBooking;

    BookingDtoItem lastBooking;
}
