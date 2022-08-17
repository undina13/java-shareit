package ru.practicum.shareit.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.service.ItemRequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @Autowired
    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping()
    RequestDto create(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId,
                      @RequestBody @Valid RequestDto itemRequestDto) {
        log.info("create request");
        return itemRequestService.create(userId, itemRequestDto);
    }

    @GetMapping()
    List<ItemRequestDto> getByRequestor(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("get requests by user {}", userId);
        return itemRequestService.getByRequestor(userId);
    }

    @GetMapping("/all")
    Page<ItemRequest> getAll(@NotBlank @RequestHeader("X-Sharer-User-Id") long userId,
                             @RequestParam(defaultValue = "0") @Min(1) int from,
                             @RequestParam(defaultValue = "30") int size){
        log.info("get requests all");
        return itemRequestService.getAll(userId, from, size);
    }

    @GetMapping("/{requestId}" )
    ItemRequestDto getById(@PathVariable long requestId){
        log.info("get request id = {}", requestId);
        return itemRequestService.getById(requestId);
    }
}
