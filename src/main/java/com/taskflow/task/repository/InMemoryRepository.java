package com.taskflow.task.repository;

import com.taskflow.task.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryRepository implements TaskRepository {

    private List<Task> tasks = new ArrayList<>();
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

    @Override
    public Optional<Task> update(Long id) {
        return this.tasks.stream().filter(t -> Objects.equals(t.getId(), id)).findFirst();
    }

    @Override
    public Optional<Task> updateStatus(Long id) {
        return this.tasks.stream().filter(t -> Objects.equals(t.getId(), id)).findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        var foundTask = this.tasks.stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst();

        if (foundTask.isEmpty()) {
            return false;
        }

        this.tasks.remove(foundTask.get());

        return true;
//        var newList = this.tasks.stream().filter(t -> !Objects.equals(t.getId(), id));
//        this.tasks = new ArrayList<>(newList.toList());
    }
}
