package com.luna.luna_project.services;

import com.luna.luna_project.enums.StatusScheduling;
import com.luna.luna_project.models.Queue;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;
    private Queue<Scheduling> queue = new Queue<Scheduling>();

    @Autowired
    public SchedulingService(SchedulingRepository schedulingRepository, ClientService clientService) {
        this.schedulingRepository = schedulingRepository;
    }

    public Boolean existsById(Long id) {
        return schedulingRepository.existsById(id);
    }

    public Set<LocalDateTime> listBusySchedules(Long clientId,
                                                LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Scheduling> schedulings = schedulingRepository.
                findSchedulingByClient_IdAndStartDateTimeBetween(clientId, startDateTime, endDateTime);
        Set<LocalDateTime> busySchedules = new HashSet<>();

        for (Scheduling scheduling : schedulings) {
            LocalDateTime start = scheduling.getStartDateTime();
            LocalDateTime end = scheduling.calculateEndDate();
            for (LocalDateTime time = start; time.isBefore(end); time = time.plusMinutes(30)) {
                busySchedules.add(time);
            }
        }

        if (busySchedules.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há ocupados horários neste intervalo");
        }

        return busySchedules;
    }

    public List<LocalDateTime> listAvailable(Long employeeId, Long clientId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // Verifica se o cliente e o funcionário existem


        // Obtém os agendamentos do cliente e do funcionário dentro do período especificado
        List<Scheduling> schedulingsEmployee = schedulingRepository
                .findSchedulingByEmployee_IdAndStartDateTimeBetween(employeeId, startDateTime, endDateTime);

        List<Scheduling> agendamentosClient = schedulingRepository
                .findSchedulingByClient_IdAndStartDateTimeBetween(clientId, startDateTime, endDateTime);

        Set<Scheduling> schedulings = new HashSet<>();

        schedulings.addAll(schedulingsEmployee);
        schedulings.addAll(agendamentosClient);
        schedulings.stream().sorted(Comparator.comparing(Scheduling::getStartDateTime));

        // Gera todos os horários possíveis dentro do período

        List<LocalDateTime> availableHours = new ArrayList<>();
        for (LocalDateTime time = startDateTime; time.plusMinutes(45).isBefore(endDateTime); time = time.plusMinutes(45)) {
            availableHours.add(time);
        }

        for (Scheduling scheduling : schedulings) {
            availableHours.removeIf(time ->
                    (time.isBefore(scheduling.calculateEndDate()) && time.plusMinutes(45).isAfter(scheduling.getStartDateTime()))
            );
        }

        // Verifica se há horários disponíveis
        if (availableHours.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não há horários disponíveis entre " + startDateTime + " e " + endDateTime);
        }

        return availableHours;
    }


    public List<Scheduling> listSchedulingByEmployeeId(Long employeeId, LocalDateTime startDateTime,
                                                       LocalDateTime endDateTime) {
        return schedulingRepository.findSchedulingByEmployee_IdAndStartDateTimeBetween(employeeId,
                startDateTime, endDateTime);
    }

    public List<Scheduling> listSchedulingByClientId(Long clientId, LocalDateTime startDateTime) {

        List<Scheduling> schedulings = schedulingRepository.findSchedulingByClient_IdAndStartDateTimeAfter
                (clientId, startDateTime);
        if (schedulings.isEmpty()) {
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Não há agendamentos para este usuários a partir deste dia e horário");
        }
        return schedulings;
    }

    public Scheduling schedulingSave(Scheduling scheduling) {
        scheduling.setId(null);
        queue.insert(scheduling);
        return registerSchedule();
    }

    public Scheduling registerSchedule() {
        Scheduling scheduling = queue.poll();
        if (!validatyScheduleSave(scheduling)) {
            throw new ResponseStatusException
                    (HttpStatus.CONFLICT, "Já existe agendamentos nesse horário");
        }
        return schedulingRepository.save(scheduling);
    }

    public Boolean validatyScheduleSave(Scheduling scheduling) {
        List<LocalDateTime> times =
                listAvailable(scheduling.getEmployee().getId(),
                        scheduling.getClient().getId(), scheduling.getStartDateTime(),
                        scheduling.calculateEndDate());

        boolean validate = false;

        for (LocalDateTime time : times) {
            if (time.isEqual(scheduling.getStartDateTime())) {
                validate = true;
            }
        }
        return validate;
    }

    public void deleteById(Long id) {
       Optional<Scheduling> scheduling =  schedulingRepository.findById(id);
        if (scheduling.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "CPF já cadastrado Agendamento de id: %d não encontrado".formatted(id));
        }

        schedulingRepository.deleteById(id);
    }

    public Scheduling changeStatus(Long schedulingId, StatusScheduling statusScheduling){
        Optional<Scheduling> scheduling = schedulingRepository.findById(schedulingId);

        if (scheduling.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe agendamento com o id:%d".formatted(schedulingId));
        }
        scheduling.get().setStatusScheduling(statusScheduling);

        return schedulingRepository.save(scheduling.get());
    }



    public Scheduling updateScheduling(Scheduling scheduling) {
        Optional<Scheduling> schedulingOptional = schedulingRepository.findById(scheduling.getId());
        if (schedulingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe agendamento com o id:%d".formatted(scheduling.getId()));
        }
        schedulingRepository.save(scheduling);
        return schedulingOptional.get();
    }
}

