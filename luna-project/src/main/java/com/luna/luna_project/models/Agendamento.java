package com.luna.luna_project.models;

import com.luna.luna_project.enums.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Future
    private LocalDateTime dataHoraInicio;

    @NotBlank
    @ElementCollection(targetClass = Task.class)
    private List<Task> itens;

    @ManyToOne
    private Client client;
    @ManyToOne
    private Client funcionario;




    public int calcularDuracaoTotal() {
        if (itens != null && !itens.isEmpty()) {
            return itens.stream()
                    .mapToInt(Task::getDuration)
                    .sum();
        }
        return 0;
    }

    public LocalDateTime calcularDataFim() {
        if (dataHoraInicio != null) {
            int totalDuration = calcularDuracaoTotal();
            return dataHoraInicio.plusMinutes(totalDuration);
        } else {
            return null;
        }
    }
}
