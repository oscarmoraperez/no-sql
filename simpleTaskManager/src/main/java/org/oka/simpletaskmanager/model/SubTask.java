package org.oka.simpletaskmanager.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a subtask. Simple steps within a Task.
 */
@EqualsAndHashCode
@Builder
@Getter
@Document("subtasks")
@ToString
public class SubTask {
    @Setter
    public String name;
    @Setter
    @TextIndexed
    public String description;
}
