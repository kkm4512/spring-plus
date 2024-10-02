package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    /**
     * page,size,weather,기간을 기준으로 찾아서 보내주기
     *   - weather은 있을수도있고, 없을수도있음
     *   - 기간은 있을수도있고, 없을수도있음
     */
    @Query("SELECT t FROM Todo t " +
            "WHERE (:weather IS NULL OR t.weather = :weather) " +
            "AND (:startPeriod IS NULL OR  t.modifiedAt >= :startPeriod) " +
            "AND (:endPeriod IS null OR t.modifiedAt <= :endPeriod) " +
            "ORDER BY t.modifiedAt DESC "
    )
    Page<Todo> findAllTodoByWeatherAndFromStartPeriodToEndPeriod(
            Pageable pageable,
            @Param("weather") String weather,
            @Param("startPeriod") LocalDateTime startPeriod,
            @Param("endPeriod") LocalDateTime endPeriod
            );
}
