package com.springboot.todo.service;
import com.springboot.todo.model.Todo;
import com.springboot.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(int id) {
        return todoRepository.findById(id).get();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(int id, Todo todo) {
        Todo todoById = todoRepository.findById(id).get();
        todoById.setDescription(todo.getDescription());
        todoRepository.save(todoById);
        return todoById;
    }

    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }
}
