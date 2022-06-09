package com.springboot.todo.controller;
import com.springboot.todo.exception.DuplicateEntryException;
import com.springboot.todo.model.Todo;
import com.springboot.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/todo")
    public List<Todo> getTodo() {
        return todoService.getAllTodos();
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") int id) {
        Todo todo = todoService.getTodoById(id);
        if (todo == null) {
            return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

    @PostMapping("/todo")
    public Todo addTodo(@RequestBody Todo todo) throws DuplicateEntryException {
        return todoService.addTodo(todo);
    }

    @PutMapping("/todo/{id}")
    public Todo updateTodoByName(@PathVariable int id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteTodoByName(@PathVariable int id) {
        todoService.deleteTodo(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
