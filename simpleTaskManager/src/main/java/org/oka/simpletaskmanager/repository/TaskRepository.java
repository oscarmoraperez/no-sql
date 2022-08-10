package org.oka.simpletaskmanager.repository;

import org.oka.simpletaskmanager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Spring based repository to hide / encapsulate MongoDB specifics.
 */
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    /**
     * Returns tasks where deadline is before the parameter.
     *
     * @param localDateTime
     * @return
     */
    Collection<Task> findTaskByDeadlineBefore(final LocalDateTime localDateTime);

    /**
     * Returns tasks per category
     *
     * @param category
     * @return
     */
    Collection<Task> findTaskByCategory(final String category);

    /**
     * Returns tasks by full text search on description field
     *
     * @param description
     * @return
     */
    @Query("{'description' : /?0/i}")
    Collection<Task> findTaskByDescription(final String description);

    /**
     * Returns tasks matching by full text search on the field name of the subtasks
     *
     * @param name
     * @return
     */
    @Query("{'subtasks.name' : /?0/i}")
    Collection<Task> findTaskBySubTaskName(final String name);
}
