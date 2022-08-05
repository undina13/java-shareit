package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.BookingForItem;
import ru.practicum.shareit.booking.repositiory.BookingRepository;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.UserIsNotBookerException;
import ru.practicum.shareit.item.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, BookingRepository bookingRepository, CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Item create(Long userId, Item item) {
        item.setOwner(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found")));
        return itemRepository.save(item);
    }

    @Override
    public Item update(long userId, Long itemId, Item item) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        // нельзя изменить вещь, если ее нет в хранилище  или нет такого пользователя или вещь чужая
        if (!owner.equals(itemRepository.findById(itemId).get().getOwner())) {
            throw new UserIsNotOwnerException("Вы пытаетесь изменить чужую вещь");
        }
        return itemRepository.save(item);
    }

    @Override
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("item not found"));
    }

    @Override
    public List<Item> getAllItemsByUser(long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        return itemRepository.findByOwner(owner);
    }

    @Override
    public List<Item> search(String text) {
        if (text.equals("")) {
            return new ArrayList<>();
        }
        return itemRepository.search(text);
    }

    public List<BookingForItem> setLastAndNextBookingDate(Item item) {
        List<BookingForItem> bookings = bookingRepository.findAllByItem(item);
        BookingForItem last = bookings.stream()
                .filter(booking -> booking.getStart().isBefore(LocalDateTime.now()))
                .max((booking, booking1) -> booking1.getStart().compareTo(booking.getStart()))
                .orElse(null);

        BookingForItem next = bookings.stream()
                .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                .min((booking, booking1) -> booking.getStart().compareTo(booking1.getStart()))
                .orElse(null);

        ArrayList<BookingForItem> bookingsList = new ArrayList<>();
        bookingsList.add(last);
        bookingsList.add(next);
        return bookingsList;
    }

    @Override
    public Comment createComment(long userId, long itemId, Comment comment) {
        if (comment.getText().isEmpty()) {
            throw new UserIsNotBookerException("text is empty");
        }
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("item not found"));
        List<BookingForItem> bookings = bookingRepository.findAllByItem(item);
        BookingForItem booking = bookings.stream()
                .filter(bookingForItem -> bookingForItem.getBooker().getId() == userId)
                .findAny()
                .orElseThrow(() -> new UserIsNotBookerException("user not booking this item"));
        if (booking.getEnd().isAfter(LocalDateTime.now())) {
            throw new UserIsNotBookerException("user do not end booking;");
        }
        comment.setItem(item);
        comment.setAuthor(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found")));
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByItem(long itemId) {
        return commentRepository.findCommentsByItem(itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("item not found")));
    }
}
