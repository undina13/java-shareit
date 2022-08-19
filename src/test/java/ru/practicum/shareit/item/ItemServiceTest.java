package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.UserIsNotBookerException;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.practicum.shareit.item.ItemTestData.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceTest {

    @Autowired
    private final ItemServiceImpl itemService;

    @Test
    @DirtiesContext
    void testCreate() {
        long itemId = itemService.create(1L, itemDtoCreated).getId();
        ItemDto itemDtoFromSQL = itemService.getItemById(1L, itemId);
        assertThat(itemDtoFromSQL, equalTo(itemDtoCreated));
    }

    @Test
    @DirtiesContext
    void testUpdate() {
        itemDto1.setName("new name");
        itemService.update(1L, 1L, itemDto1);
        ItemDto itemDtoFromSQL = itemService.getItemById(1L, 1L);
        assertThat(itemDtoFromSQL.getName(), equalTo(itemDto1.getName()));
        itemDto1.setName("item1");
    }

    @Test
    void testUpdateWrongItem() {
        itemDto1.setName("new name");
        assertThrows(ItemNotFoundException.class, () -> itemService.update(1L, 50L, itemDto1));
        itemDto1.setName("item1");
    }

    @Test
    void testUpdateWrongOwner() {
        itemDto1.setName("new name");
        assertThrows(UserNotFoundException.class, () -> itemService.update(10L, 1L, itemDto1));
        itemDto1.setName("item1");
    }

    @Test
    void testGetItemById() {
        ItemDto itemDtoFromSQL = itemService.getItemById(2L, 2L);
        assertThat(itemDtoFromSQL, equalTo(itemDto2));
    }

    @Test
    void testGetItemByWrongId() {
        assertThrows(ItemNotFoundException.class, () -> itemService.getItemById(1L, 10L));
    }

    @Test
    void testGetAllItemsByUser() {
        List<ItemDto> items = itemService.getAllItemsByUser(1, 10, 2L);
        assertThat(items, equalTo(List.of(itemDto4)));
    }

    @Test
    void testSearch() {
        //этот метод выводит ItemDto без комментариев, поэтому убираем их
        itemDto1.setComments(null);
        List<ItemDto> items = itemService.search(1, 10, "item1");
        assertThat(items, equalTo(List.of(itemDto1)));
        itemDto1.setComments(new ArrayList<>());
    }

    @Test
    @DirtiesContext
    void testCreateComment() {
        CommentDto commentDto1 = itemService.createComment(2L, 1L, commentDto);
        assertThat(commentDto1, equalTo(commentDto));
    }

    @Test
    void testCreateCommentNotBooker() {
        assertThrows(UserIsNotBookerException.class, () -> itemService.createComment(3L, 1L, commentDto));
    }

}
