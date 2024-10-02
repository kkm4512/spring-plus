package org.example.expert.domain.comment.repository;

import org.example.expert.domain.comment.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryQuery {
    // 게시글 찾으면서, 관련된 유저도 전부 가져오기
    List<Comment> findByTodoIdWithUser(Long todoId);
}
