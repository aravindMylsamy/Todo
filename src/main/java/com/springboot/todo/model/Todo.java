package com.springboot.todo.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "todo")
public class Todo {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name",unique = true)
    @NotNull
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_done")
    private Boolean is_done = false;

    public Todo() {
    }

    public Todo(String name) {
        this.name = name;
    }

    public Todo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_done() {
        return is_done;
    }

    public void setIs_done(Boolean is_done) {
        this.is_done = is_done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return name.equals(todo.name) && Objects.equals(description, todo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
