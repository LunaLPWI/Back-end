package com.luna.luna_project.services;

import com.luna.luna_project.dtos.assessment.AssessmentRequest;
import com.luna.luna_project.enums.StatusScheduling;
import com.luna.luna_project.models.Assessment;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.models.Queue;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.AssessmentRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import jakarta.transaction.Transactional;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;

    private final AssessmentRepository assessmentRepository;
    private Queue<Scheduling> queue = new Queue<Scheduling>();
    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    public SchedulingService(SchedulingRepository schedulingRepository,
                             AssessmentRepository assessmentRepository) {
        this.schedulingRepository = schedulingRepository;
        this.assessmentRepository = assessmentRepository;
    }

    @Autowired
    QuartzSchedulerJob quartzScheduler;

    public Boolean existsById(Long id) {
        return schedulingRepository.existsById(id);
    }

    public Set<Scheduling> listBusySchedules(Long employeeId,Long clientId,
                                                LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Scheduling> schedulingsEmployee = schedulingRepository
                .findSchedulingByEmployee_IdAndStartDateTimeBetween(employeeId, startDateTime, endDateTime);

        List<Scheduling> agendamentosClient = schedulingRepository
                .findSchedulingByClient_IdAndStartDateTimeBetween(clientId, startDateTime, endDateTime);

        Set<Scheduling> schedulings = new HashSet<>();
        schedulings.addAll(schedulingsEmployee);
        schedulings.addAll(agendamentosClient);
        schedulings.stream().sorted(Comparator.comparing(Scheduling::getStartDateTime));

        return schedulings;
    }

    public Set<LocalDateTime> listAvailable(Long employeeId, Long clientId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // Obtém os agendamentos do cliente e do funcionário dentro do período especificado

        Set<Scheduling> schedulings = listBusySchedules(employeeId,clientId,startDateTime,endDateTime);
        for (Scheduling scheduling : schedulings) {
            System.out.println(scheduling);
        }
        // Gera todos os horários possíveis dentro do período
        Set<LocalDateTime> availableHours = new LinkedHashSet<>();
        for (LocalDateTime time = startDateTime; !time.plusMinutes(30).isAfter(endDateTime); time = time.plusMinutes(30)) {
            // Verifica se o horário "time" cai dentro de algum agendamento
            LocalDateTime time1 = time;
            for (Scheduling scheduling : schedulings) {
                // Se "time" estiver dentro de um agendamento, ajusta "time" para o final do agendamento
                if (time.isAfter(scheduling.getStartDateTime()) && time.isBefore(scheduling.calculateEndDate())) {
                    time = scheduling.calculateEndDate();  // Ajusta o horário para o final do agendamento
                    break;  // Não precisamos verificar os outros agendamentos, pois já ajustamos o horário
                }
            }
            availableHours.add(time);
            time = time1;
        }

        System.out.println("Horários gerados:");
        for (LocalDateTime time : availableHours) {
            System.out.println(time);
        }
        if (schedulings.isEmpty()) {
            availableHours.add(startDateTime);
        }else{
            for (Scheduling scheduling : schedulings) {
                availableHours.removeIf(time ->
                        time.isEqual(scheduling.getStartDateTime()) ||
                                (time.isAfter(scheduling.getStartDateTime()) && time.isBefore(scheduling.calculateEndDate()))
                );
            }
        }

        // Remover horários ocupados


        // Verifica se há horários disponíveis
        if (availableHours.isEmpty() && !schedulings.isEmpty()) {
            System.out.println("Não há horários disponíveis!");
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
                    (HttpStatus.NO_CONTENT, "Não há agendamentos para este usuários a partir deste dia e horário");
        }
        return schedulings;
    }


    public Scheduling schedulingSave(Scheduling scheduling) throws SchedulerException {
        scheduling.setId(null);
        queue.insert(scheduling);
        return registerSchedule();
    }

    @Transactional
    public Scheduling registerSchedule() throws SchedulerException {
        Scheduling scheduling = queue.poll();
        System.out.println("[registerSchedule] Iniciando registro de agendamento...");
        System.out.println("[registerSchedule] Scheduling polled da fila: " + scheduling);

        if (!validatyScheduleSave(scheduling)) {
            System.out.println("[registerSchedule] Conflito de horário detectado para: " + scheduling);
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Já existe agendamentos nesse horário"
            );
        }

        System.out.println("[registerSchedule] Antes do save: scheduling.getId() = " + scheduling.getId());
        Scheduling scheduling1 = schedulingRepository.save(scheduling);
        System.out.println("[registerSchedule] Depois do save: scheduling1.getId() = " + scheduling1.getId());

        schedulingRepository.flush();

        Optional<Assessment> existingAssessment = assessmentRepository.findByScheduling(scheduling1);
        if (existingAssessment.isEmpty()) {
            System.out.println("[registerSchedule] Nenhum Assessment encontrado, criando novo...");

            Long establishmentId = scheduling1.getEmployee()
                    .getEstablishments()
                    .stream()
                    .findFirst()
                    .map(Establishment::getId)
                    .orElse(null);

            System.out.println("[registerSchedule] establishmentId extraído: " + establishmentId);

            AssessmentRequest assessment = AssessmentRequest.builder()
                    .messaging(null)
                    .schedulingId(scheduling1.getId())
                    .rating(null)
                    .establishmentId(establishmentId)
                    .build();

            System.out.println("[registerSchedule] Enviando para assessmentService.saveAssessment: " + assessment);
            assessmentService.saveAssessment(assessment);
        } else {
            System.out.println("[registerSchedule] Assessment já existente para o scheduling ID: " + scheduling1.getId());
        }

        Establishment firstEstablishment = scheduling1.getEmployee().getEstablishments()
                .stream()
                .findFirst()
                .orElse(null);

        String estabelecimentoNome = (firstEstablishment != null) ? firstEstablishment.getName() : "Estabelecimento não encontrado";

        String texto = "Olá, " + scheduling1.getClient().getName() +
                "! Seu horário na " + estabelecimentoNome +
                " foi confirmado para o dia " + scheduling1.getStartDateTime() + " com " +
                scheduling1.getEmployee().getName() + ". Te esperamos lá!.";

        System.out.println("[registerSchedule] Mensagem gerada para envio: " + texto);

        quartzScheduler.agendarEnvio(scheduling1, texto);
        System.out.println("[registerSchedule] Agendamento enviado para o QuartzScheduler.");
        System.out.println("[registerSchedule] Scheduling final: " + scheduling1);

        return scheduling1;
    }



    public Boolean validatyScheduleSave(Scheduling scheduling) {
        Set<LocalDateTime> times =
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
                    " Agendamento de id: %d não encontrado".formatted(id));
        }
        Assessment assessment =  assessmentRepository.findByScheduling_Id(id).get();
        assessmentRepository.deleteById(assessment.getAssessment_id());
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

    public Scheduling getSchedulingById(Long id) {
        return schedulingRepository.findTopByClient_IdOrderByStartDateTimeDesc(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento com ID %d não encontrado".formatted(id)
                ));
    }
}

