package org.oka.simpletaskmanager.service;

import org.oka.simpletaskmanager.model.SubTask;
import org.oka.simpletaskmanager.model.Task;
import org.oka.simpletaskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Service component to provide management of the Tasks
 */
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Returns all the tasks in the system
     *
     * @return
     */
    public Collection<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Returns all the overdue tasks (based on the deadline field)
     *
     * @return
     */
    public Collection<Task> getOverdueTasks() {
        return taskRepository.findTaskByDeadlineBefore(LocalDateTime.now());
    }

    /**
     * Returns Tasks based on the category of the Task
     *
     * @param category
     * @return
     */
    public Collection<Task> getTasksByCategory(final String category) {
        return taskRepository.findTaskByCategory(category);
    }

    /**
     * Returns subtasks based on the category of the Task
     *
     * @param category
     * @return
     */
    public Collection<SubTask> getSubTasksByCategory(final String category) {
        return taskRepository.findTaskByCategory(category).stream().map(Task::getSubtasks).flatMap(List::stream).collect(toList());
    }

    /**
     * Persist a new Task
     *
     * @param task
     * @return
     */
    public Task createTask(final Task task) {
        return taskRepository.save(task);
    }

    /**
     * Update the Task
     *
     * @param task
     * @return
     */
    public Task updateTask(final Task task) {
        return taskRepository.save(task);
    }

    /**
     * Removes the Task from the system
     *
     * @param task
     */
    public void removeTask(final Task task) {
        taskRepository.delete(task);
    }

    /**
     * Add SubTask to a Task and persists it
     *
     * @param task
     * @param subTask
     * @return
     */
    public Task addSubTask(final Task task, final SubTask subTask) {
        Task fromSystem = taskRepository.findById(task.getId()).orElseThrow();
        fromSystem.getSubtasks().add(subTask);

        return taskRepository.save(fromSystem);
    }

    /**
     * Deletes subTask from a Task
     *
     * @param task
     * @param subTask
     * @return
     */
    public Task deleteSubTask(final Task task, final SubTask subTask) {
        Task fromSystem = taskRepository.findById(task.getId()).orElseThrow();
        List<SubTask> subtasks = fromSystem.getSubtasks();
        fromSystem.setSubtasks(subtasks.stream().filter(s -> !s.equals(subTask)).collect(toList()));

        return taskRepository.save(fromSystem);
    }

    /**
     * Full Text search on the description field of the Task
     *
     * @param description
     * @return
     */
    public Collection<Task> findByDescription(final String description) {
        return taskRepository.findTaskByDescription(description);
    }

    /**
     * Full text search on the name field of the SubTask
     *
     * @param name
     * @return
     */
    public Collection<Task> findBySubTaskName(final String name) {
        return taskRepository.findTaskBySubTaskName(name);
    }

    /**
     * Deletes all the Task. Useful to reset the DB.
     */
    public void deleteAll() {
        taskRepository.deleteAll();
    }
}