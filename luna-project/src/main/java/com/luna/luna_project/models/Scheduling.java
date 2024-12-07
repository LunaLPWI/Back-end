package com.luna.luna_project.models;

import com.luna.luna_project.enums.StatusScheduling;
import com.luna.luna_project.enums.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "scheduling")
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDateTime;
    @ElementCollection(targetClass = Task.class)
    private List<Task> items;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Client employee;
    @ElementCollection
    @CollectionTable(name = "scheduling_products", joinColumns = @JoinColumn(name = "scheduling_id"))
    private List<ProductScheduling> products;
    private StatusScheduling statusScheduling;

    @PrePersist
    public void setDefaultStatusScheduling() {
        if (statusScheduling == null) {
            statusScheduling = StatusScheduling.PENDING;
        }
    }

    public StatusScheduling checkAndUpdateStatus() {
        if (statusScheduling == StatusScheduling.PENDING && startDateTime != null) {
            if (startDateTime.isBefore(LocalDateTime.now())) {
                statusScheduling = StatusScheduling.DELAYED;
                return statusScheduling;
            }
        }
        return statusScheduling;
    }


    public int calculateTotalDuration() {
        if (items != null && !items.isEmpty()) {
            return items.stream()
                    .mapToInt(Task::getDuration)
                    .sum();
        }
        return 0;
    }

    public LocalDateTime calculateEndDate() {
        if (startDateTime != null) {
            int totalDuration = calculateTotalDuration();
            return startDateTime.plusMinutes(totalDuration);
        } else {
            return null;
        }
    }
}
