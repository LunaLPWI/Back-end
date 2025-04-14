package com.luna.luna_project.models;

import com.luna.luna_project.enums.StatusScheduling;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany
    private List<Office> items;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Client employee;

    private StatusScheduling statusScheduling;


    @PrePersist
    public void setDefaultStatusScheduling() {
        if (statusScheduling == null) {
            statusScheduling = StatusScheduling.PENDING;
            if (startDateTime.isBefore(LocalDateTime.now())) {
                statusScheduling = StatusScheduling.DELAYED;
            }
        }
    }

    public StatusScheduling checkAndUpdateStatus() {
        if (statusScheduling == StatusScheduling.PENDING) {
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
                    .mapToInt(Office::getDuration)
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
