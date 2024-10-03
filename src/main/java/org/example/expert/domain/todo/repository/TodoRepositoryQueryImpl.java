package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.example.expert.domain.todo.entity.QTodo.todo;

@Component
@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery {
    private final JPAQueryFactory q;


    @Override
    public Page<TodoSearchResponseDto> searchTodosByFilter(
            String todoTitle,
            LocalDateTime startPeriod,
            LocalDateTime endPeriod,
            String nickname,
            Pageable pageable
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        if (todoTitle != null) {
            builder.and(todo.title.contains(todoTitle));
        }

        if (startPeriod != null) {
            builder.and(todo.createdAt.goe(startPeriod));
        }

        if (endPeriod != null) {
            builder.and(todo.createdAt.loe(endPeriod));
        }

        if (nickname != null) {
            builder.and(todo.user.nickname.contains(nickname));
        }

        var result = q
                .select(Projections.bean(TodoSearchResponseDto.class,
                        todo.title.as("title"),
                        todo.managers.size().as("managers"),
                        todo.comments.size().as("comments")
                        ))
                .from(todo)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(todo.createdAt.desc())
                .fetch();
        var total = q
                .select(todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();
        return new PageImpl<>(result,pageable,total);

    }
}
