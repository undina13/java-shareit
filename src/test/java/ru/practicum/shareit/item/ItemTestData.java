package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ItemTestData {
    public static ItemDto itemDto1 = ItemDto.builder()
            .id(1).name("item1").description("description1").available(true)
            .comments(new ArrayList<>()).build();
    public static ItemDto itemDto2 = ItemDto.builder()
            .id(2).name("item2").description("description2").available(true)
            .comments(new ArrayList<>()).build();
    public static ItemDto itemDto3 = ItemDto.builder()
            .id(3).name("item3").description("description3").available(false)
            .comments(new ArrayList<>()).build();
    public static ItemDto itemDto4 = ItemDto.builder()
            .id(4).name("item4").description("description4")
            .comments(new ArrayList<>()).available(true).build();
    public static ItemDto itemDtoCreated = ItemDto.builder()
            .id(5).name("itemCreated").description("descriptionCreated").available(true)
            .comments(new ArrayList<>()).build();

    public static CommentDto commentDto = CommentDto.builder().id(1L).text("comment")
            .created(LocalDateTime.now()).build();
}
