package com.luna.luna_project.models;

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
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Future
    private LocalDateTime startDateTime;
    @NotEmpty
    @ElementCollection(targetClass = Task.class)
    private List<Task> items;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Client employee;

    @ElementCollection
    @CollectionTable(name = "scheduling_products", joinColumns = @JoinColumn(name = "scheduling_id"))
    private List<ProductScheduling> products;






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
