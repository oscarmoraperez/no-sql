package org.oka.logmanager.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Data
public class LogEntry {
    @PrimaryKey
    private UUID id = UUID.randomUUID();
    @Setter
    private String data;
}
