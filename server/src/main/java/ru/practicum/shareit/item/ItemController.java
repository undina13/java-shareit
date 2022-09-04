package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

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
    ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                   @RequestBody ItemDto itemDto) {
        log.info("create item");
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                   @PathVariable long itemId,
                   @RequestBody ItemDto itemDto) {
        log.info("update item id={}", itemId);
        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                        @PathVariable long itemId) {
        log.info("get item id={}", itemId);
        return itemService.getItemById(userId, itemId);
    }

    @GetMapping()
    List<ItemDto> getAllItemsByUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @RequestParam(defaultValue = "1") int from,
                                    @RequestParam(defaultValue = "10") int size) {
        log.info("get all items from user id={}", userId);
        return itemService.getAllItemsByUser(from, size, userId);

    }

    @GetMapping("search")
    List<ItemDto> search(@RequestParam(required = false) String text,
                         @RequestParam(defaultValue = "1") int from,
                         @RequestParam(defaultValue = "10") int size) {
        log.info("search text={}", text);
        return itemService.search(from, size, text);
    }

    @PostMapping("/{itemId}/comment")
    CommentDto createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                             @PathVariable long itemId,
                             @RequestBody CommentDto commentDto) {
        log.info("create comment");
        return itemService.createComment(userId, itemId, commentDto);
    }
}

