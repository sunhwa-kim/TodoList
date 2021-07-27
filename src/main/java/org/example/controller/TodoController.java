package org.example.controller;


import lombok.AllArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.example.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        if(ObjectUtils.isEmpty(request.getTitle())) {
            return ResponseEntity.badRequest().build();
        }
        if(ObjectUtils.isEmpty(request.getOrder())) request.setOrder(0L); // defaule
        if(ObjectUtils.isEmpty(request.getCompleted())) request.setCompleted(false);
        TodoEntity todoEntity = this.todoService.add(request);
        return ResponseEntity.ok(new TodoResponse(todoEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        TodoEntity todoEntity = this.todoService.searchById(id);
        return ResponseEntity.ok(new TodoResponse(todoEntity));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        List<TodoEntity> todoEntities = this.todoService.searchAll();
        List<TodoResponse> responses = todoEntities.stream().map(TodoResponse::new)
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        TodoEntity todoEntity = this.todoService.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse(todoEntity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        this.todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll() {
        this.todoService.deleteAll();
        return ResponseEntity.ok().build();
    }



}
