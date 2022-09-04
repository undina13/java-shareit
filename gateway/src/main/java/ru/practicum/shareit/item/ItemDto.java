package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    long id;

@NotEmpty
    String name;

@NotEmpty
    String description;

@NotNull
    Boolean available;

    Long requestId;

   List<CommentDto> comments;
}
