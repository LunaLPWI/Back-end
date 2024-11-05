package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findAgendamentoByClient_IdAndDataHoraInicioBetween(Long clientId, LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim);

    List<Agendamento> findAgendamentoByFuncionario_IdAndDataHoraInicioBetween(Long funcId, LocalDateTime dataHoraInicio,
                                                                         LocalDateTime dataHoraFim);
    List<Agendamento> findAgendamentoByClient_IdAndDataHoraInicioAfter(Long funcId, LocalDateTime dataHoraInicio);

}
