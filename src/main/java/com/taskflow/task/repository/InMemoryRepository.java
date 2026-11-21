package com.taskflow.task.repository;

import com.taskflow.task.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private long id = 1;

    @Override
    public List<Task> findAll() {
        return List.copyOf(this.tasks);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return this.tasks.stream()
                .filter(task -> Objects.equals(task.getId(), id))
                .findFirst();
    }

    @Override
    public Task save(Task task) {
        task.setId(id);
        tasks.add(task);
        this.id++;
        return task;
    }
}
