package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByClient_IdAndDataHoraInicioBetween(Long clientId, LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim);
}
