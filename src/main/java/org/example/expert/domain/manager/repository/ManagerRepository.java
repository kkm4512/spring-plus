package org.example.expert.domain.manager.repository;

import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Query("SELECT m FROM Manager m JOIN FETCH m.user WHERE m.todo.id = :todoId")
    List<Manager> findByTodoIdWithUser(@Param("todoId") Long todoId);

    Manager findByTodo(Todo todo);
}
