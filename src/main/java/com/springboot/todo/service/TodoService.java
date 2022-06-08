package com.springboot.todo.service;
import com.springboot.todo.exception.DuplicateEntryException;
import com.springboot.todo.model.Todo;
import com.springboot.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Todo addTodo(Todo todo) throws DuplicateEntryException {
        Optional<Todo> todoAtRepo = todoRepository.findByName(todo.getName());
        if(todoAtRepo != null) {
            throw new DuplicateEntryException("Todo already exist with given name:" + todo.getName());
        }
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
