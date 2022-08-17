package ru.practicum.shareit.requests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.requests.ItemRequestMapper;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.itemRequestRepository = itemRequestRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public RequestDto create(long userId, RequestDto itemRequestDto) {
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);
        itemRequest.setRequestor(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found")));
        itemRequest.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toRequestDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getByRequestor(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        List<ItemRequest> requests = itemRequestRepository.findByRequestorOrderByCreatedDesc(user);
        List<ItemRequestDto> itemRequestDtoList = new ArrayList<>();
        for (ItemRequest itemRequest:requests) {
            itemRequestDtoList.add(createItemRequestDto(itemRequest));
        }
        return itemRequestDtoList;
    }

    @Override
    public Page<ItemRequest> getAll(long userId, int from, int size) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        return itemRequestRepository.findAllByRequestorIsNot(user, PageRequest.of( from, size));
    }

    @Override
    public ItemRequestDto getById(long requestId) {
        ItemRequest itemRequest= itemRequestRepository.findById(requestId)
                .orElseThrow(()-> new ItemRequestNotFoundException("ItemRequest not found"));
        return createItemRequestDto(itemRequest);
    }

    public ItemRequestDto createItemRequestDto(ItemRequest itemRequest) {
        List<ItemDto> items = itemRepository
                .findByRequest(itemRequest)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
        return ItemRequestMapper.toItemRequestDto(itemRequest, items);
    }
}
