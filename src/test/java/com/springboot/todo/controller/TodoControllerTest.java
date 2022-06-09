package com.springboot.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.todo.model.Todo;
import com.springboot.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObjectWhenCreateEmployeeThenReturnSavedEmployee() throws Exception{

        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");

        given(todoService.addTodo(any(Todo.class)))
                .willReturn(todo);

        ResultActions response = mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todo)));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(todo.getName())))
                .andExpect(jsonPath("$.description",
                        is(todo.getDescription())));
    }

    @Test
    public void givenListOfEmployeesWhenGetAllEmployeesThenReturnEmployeesList() throws Exception{
        List<Todo> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(new Todo("Spring-Boot", "Learn Spring Boot"));
        listOfEmployees.add(new Todo("Node JS", "Learn Node JS"));
        given(todoService.getAllTodos()).willReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(get("/todo"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));
    }

    @Test
    public void givenEmployeeIdWhenGetEmployeeByIdThenReturnEmployeeObject() throws Exception{
        int todoId = 0;
        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");
        given(todoService.getTodoById(todoId)).willReturn(todo);

        ResultActions response = mockMvc.perform(get("/todo/{id}", todoId));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(todo.getName())))
                .andExpect(jsonPath("$.description",
                        is(todo.getDescription())));

    }

    @Test
    public void givenInvalidEmployeeIdWhenGetEmployeeByIdThenReturnEmpty() throws Exception{
        int todoId = 0;
        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");
        given(todoService.getTodoById(todoId)).willReturn(null);

        ResultActions response = mockMvc.perform(get("/todo/{id}", todoId));

        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void givenUpdatedEmployeeWhenUpdateEmployeeThenReturnUpdateEmployeeObject() throws Exception{
        int todoId = 0;
        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");
        Todo updatedTodo = new Todo("Spring-Boot-Updated", "Learn Spring Boot Updated");
        given(todoService.updateTodo(any(Integer.class),any(Todo.class))).willReturn(updatedTodo);

        ResultActions response = mockMvc.perform(put("/todo/{id}", todoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodo)));


        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(updatedTodo.getName())))
                .andExpect(jsonPath("$.description",
                        is(updatedTodo.getDescription())));

    }

    @Test
    public void givenEmployeeIdWhenDeleteEmployeeThenReturn200() throws Exception{
        int todoId = 0;
        willDoNothing().given(todoService).deleteTodo(todoId);

        ResultActions response = mockMvc.perform(delete("/todo/{id}", todoId));

        response.andExpect(status().isOk())
                .andDo(print());
    }

}
