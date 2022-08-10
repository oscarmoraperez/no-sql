package org.oka.simpletaskmanager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Task Model
 */
@Getter
@Document("tasks")
@Builder
@ToString
public class Task {
    @Id
    public final String id;
    @Setter
    public String name;
    @Setter
    @TextIndexed
    public String description;
    @Setter
    public String category;
    @Setter
    public LocalDateTime creationDate;
    @Setter
    public LocalDateTime deadline;
    @Setter
    @Builder.Default
    List<SubTask> subtasks = new ArrayList<>();
}

