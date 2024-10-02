package org.example.expert.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.expert.domain.comment.entity.QComment.comment;


@RequiredArgsConstructor
@Component
public class CommentRepositoryQueryImpl implements CommentRepositoryQuery {
    private final JPAQueryFactory q;

    @Override
    public List<Comment> findByTodoIdWithUser(Long todoId) {
        return q
                .select(comment)
                .from(comment)
                .leftJoin(comment.user).fetchJoin()
                .fetch();
    }
}
