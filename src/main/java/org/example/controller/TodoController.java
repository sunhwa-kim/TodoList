package org.example.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Todo;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.example.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        log.info("CREATE");
        if(ObjectUtils.isEmpty(request.getTitle())) {
            return ResponseEntity.badRequest().build();
        }
        if(ObjectUtils.isEmpty(request.getOrder())) request.setOrder(0L); // defaule
        if(ObjectUtils.isEmpty(request.getCompleted())) request.setCompleted(false);
        Todo todo = this.todoService.add(request);
        return ResponseEntity.ok(new TodoResponse(todo));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        log.info("READ ONE");
        Todo todo = this.todoService.searchById(id);
        return ResponseEntity.ok(new TodoResponse(todo));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        log.info("READ ALL");
        List<Todo> todoEntities = this.todoService.searchAll();
        List<TodoResponse> responses = todoEntities.stream().map(TodoResponse::new)
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        log.info("UPDATE");
        Todo todo = this.todoService.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse(todo));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        log.info("DELETE ONE");
        this.todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll() {
        log.info("DELETE ALL");
        this.todoService.deleteAll();
        return ResponseEntity.ok().build();
    }



}
