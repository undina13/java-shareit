package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
        log.debug("create item");
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    ItemDto update(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId,
                   @PathVariable long itemId,
                   @RequestBody ItemDto itemDto) {
        log.debug("update item id={}", itemId);
        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    ItemDto getItemById(@PathVariable long itemId) {
        log.debug("get item id={}", itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping()
    List<ItemDto> getAllItemsByUser(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId) {
        log.debug("get all items from user id={}", userId);
        return itemService.getAllItemsByUser(userId);
    }

    @GetMapping("search")
    List<ItemDto> search(@RequestParam(required = false) String text) {
        log.debug("search text={}", text);
        return itemService.search(text);
    }
}

