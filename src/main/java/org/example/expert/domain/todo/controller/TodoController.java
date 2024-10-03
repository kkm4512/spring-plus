package org.example.expert.domain.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponseDto;
import org.example.expert.domain.todo.service.TodoService;
import org.example.expert.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoSaveResponse> saveTodo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest
            ) {
        AuthUser authUser = userDetails.getAuthUser();
        return ResponseEntity.ok(todoService.saveTodo(authUser, todoSaveRequest));
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            // 사용자가 기재할 날씨
            @RequestParam(required = false) String inputWeather,
            // 시작 기간
            @RequestParam(required = false) LocalDateTime startPeriod,
            // 끝나는 기간
            @RequestParam(required = false) LocalDateTime endPeriod

            ) {
        return ResponseEntity.ok(todoService.getTodos(
                page,
                size,
                inputWeather,
                startPeriod,
                endPeriod
        ));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.getTodo(todoId));
    }

    @GetMapping("/todos/search")
    public Page<TodoSearchResponseDto> getSearchTodos(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) LocalDateTime startPeriod,
            @RequestParam(required = false) LocalDateTime endPeriod,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return todoService.getSearchTodos(
                title,
                startPeriod,
                endPeriod,
                nickname,
                pageable
        );
    }
}
