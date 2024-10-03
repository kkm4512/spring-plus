package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoSearchResponseDto {
    private String title;
    private int managers;
    private int comments;
}
