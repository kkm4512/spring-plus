package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TodoRepositoryQuery {
    /**
     * 일정을 검색하는 기능
     *
     * 일정의 제목 검색 가능
     *   - 제목이 부분적으로 일치하여도 가능
     * 일정의 생성일 범위로 검색 가능
     *   - 일정을 생성일 최신순으로 정렬하기
     * 담당자의 닉네임으로도 검색 가능
     *   - 닉네임이 부분적으로 일치하여도 검색 가능
     * 일정의 제목만 반환
     * 일정의 담당자수 반환
     * 일정의 총 댓글개수 반환
     * 검색 결과는 페이징 처리 되어 반환시키기
     */

    Page<TodoSearchResponseDto> searchTodosByFilter(
            String title,
            LocalDateTime startPeriod,
            LocalDateTime endPeriod ,
            String nickname,
            Pageable pageable);
}
