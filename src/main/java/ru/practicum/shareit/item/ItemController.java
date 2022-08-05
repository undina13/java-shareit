package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.model.BookingForItem;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping()
    ItemDto create(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId,
                   @RequestBody @Valid ItemDto itemDto) {
        log.info("create item");
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemService.create(userId, item));
    }

    @PatchMapping("/{itemId}")
    ItemDto update(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId,
                   @PathVariable long itemId,
                   @RequestBody ItemDto itemDto) {
        log.info("update item id={}", itemId);
        Item item = itemService.getItemById(itemId);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getRequest() != null) {
            item.setRequest(itemDto.getRequest());
        }
        itemService.update(userId, itemId, item);
        return ItemMapper.toItemDto(item);
    }

    @GetMapping("/{itemId}")
    ItemDto getItemById(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId,
                        @PathVariable long itemId) {
        log.info("get item id={}", itemId);
        Item item = itemService.getItemById(itemId);
        if (userId == item.getOwner().getId()) {
            List<BookingForItem> bookings = itemService.setLastAndNextBookingDate(item);
            return createItemDtoBooking(item, bookings);
        } else return ItemMapper.toItemDto(item);
    }

    @GetMapping()
    List<ItemDto> getAllItemsByUser(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("get all items from user id={}", userId);
        List<ItemDto> items = itemService.getAllItemsByUser(userId)
                .stream()
                .sorted(Comparator.comparing(item -> item.getId()))
                .map(item -> createItemDtoBooking(item, itemService.setLastAndNextBookingDate(item)))
                .collect(Collectors.toList());
        return items;

    }

    @GetMapping("search")
    List<ItemDto> search(@RequestParam(required = false) String text) {
        log.info("search text={}", text);
        return itemService.search(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public ItemDto createItemDtoBooking(Item item, List<BookingForItem> bookings) {
        ItemDto itemDto = ItemMapper.toItemDto(item);
        if (bookings.get(0) != null) {
            itemDto.setLastBooking(BookingMapper.toBookingDtoItem(bookings.get(0)));
        }
        if (bookings.get(1) != null) {
            itemDto.setNextBooking(BookingMapper.toBookingDtoItem(bookings.get(1)));
        }
        return itemDto;
    }
}

