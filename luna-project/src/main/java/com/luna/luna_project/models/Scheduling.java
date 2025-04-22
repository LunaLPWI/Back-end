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
    @ManyToMany
    @JoinTable(
            name = "scheduling_employee_tasks",
            joinColumns = @JoinColumn(name = "scheduling_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_task_id")
    )
    private List<EmployeeTask> items;
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

    public String toString() {
        return "Scheduling{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", items=" + (items != null ? items.toString() : "[]") +  // Verificando se a lista de items é nula
                ", client=" + (client != null ? client.getName() : "null") +  // Evitar printar toda a entidade, pegar apenas o nome
                ", employee=" + (employee != null ? employee.getName() : "null") +  // Evitar printar toda a entidade, pegar apenas o nome
                ", statusScheduling=" + statusScheduling +
                '}';
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




    public LocalDateTime calculateEndDate() {
        if (items == null || startDateTime == null) {
            throw new IllegalStateException("Tasks ou data de início não podem ser nulos.");
        }

        int totalDuration = items.stream()
                .mapToInt(EmployeeTask::getDuration)
                .sum();

        return startDateTime.plusMinutes(totalDuration);
    }


}
