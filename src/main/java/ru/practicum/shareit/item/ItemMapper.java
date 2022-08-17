package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemForRequest;

@UtilityClass
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequest()!=null? item.getRequest().getId():null)
                .build();
    }

    public static Item toItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
              // .request(itemDto.getRequestId())
                .build();
    }

    public static ItemDtoForRequest toItemDtoForRequest(ItemForRequest itemForRequest){
        return ItemDtoForRequest.builder()
                .id(itemForRequest.getId())
                .name(itemForRequest.getName())
                .ownerId(itemForRequest.getOwner().getId())
                .build();
    }
}
