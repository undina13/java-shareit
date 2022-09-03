package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/items")
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @Autowired
    public ItemController(ItemClient itemClient) {
        this.itemClient = itemClient;
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @RequestBody @Valid ItemDto itemDto) {
        log.info("create item{}", itemDto);
        return itemClient.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable long itemId,
                                         @RequestBody ItemDto itemDto) {
        log.info("update item id={}", itemId);
        return itemClient.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @PathVariable long itemId) {
        log.info("get item id={}", itemId);
        return itemClient.getItemById(userId, itemId);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllItemsByUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                    @Valid @RequestParam(name = "from", defaultValue = "1") int from,
                                                    @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("get all items from user id={}", userId);
        return itemClient.getAllItemsByUser(userId, from, size);

    }

    @GetMapping("search")
    public ResponseEntity<Object> search(@RequestParam(required = false) String text,
                                         @Valid @RequestParam(name = "from", defaultValue = "1") int from,
                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("search text={}", text);
        return itemClient.search(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @PathVariable long itemId,
                                                @RequestBody @Valid CommentDto commentDto) {
        log.info("create comment");
        return itemClient.createComment(userId, itemId, commentDto);
    }

}
