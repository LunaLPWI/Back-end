package com.luna.luna_project.services;

import com.luna.luna_project.models.ProductScheduling;
import com.luna.luna_project.models.ProductStock;
import com.luna.luna_project.models.Queue;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.models.Stack;
import com.luna.luna_project.repositories.ProductStockRepository;
import com.luna.luna_project.repositories.ProductSchedulingRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;
    private final ClientService clientService;
    private final ProductSchedulingRepository productSchedulingRepository;
    private final ProductStockRepository productStockRepository;
    private final ProductService productService;
    private Queue<Scheduling> queue = new Queue<Scheduling>();

    @Autowired
    public SchedulingService(SchedulingRepository schedulingRepository, ClientService clientService, ProductSchedulingRepository productSchedulingRepository, ProductStockRepository productStockRepository, ProductService productService) {
        this.schedulingRepository = schedulingRepository;
        this.clientService = clientService;
        this.productSchedulingRepository = productSchedulingRepository;
        this.productStockRepository = productStockRepository;
        this.productService = productService;
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
                    (HttpStatus.CONFLICT, "Não há agendamentos para este usuários a partir deste dia e horário");
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

    //// ele faz um rollback no banco caso de algum erro
    @Transactional
    public Scheduling addProducts(Long schedulingId, List<ProductScheduling> productScheduling) {

        if (productScheduling.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "A lista passada está vazia");
        }
        Optional<Scheduling> scheduling = schedulingRepository.findById(schedulingId);


        if (scheduling.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe agendamento com o id:%d".formatted(schedulingId));
        }

        for (ProductScheduling newProduct : productScheduling) {
            Optional<ProductScheduling> existingProductOpt = scheduling.get().getProducts().stream()
                    .filter(p -> p.getId().equals(newProduct.getId()))
                    .findFirst();

            if (existingProductOpt.isPresent()) {
                ProductScheduling existingProduct = existingProductOpt.get();
                existingProduct.setAmount(existingProduct.getAmount() + newProduct.getAmount());
            } else {
                if (productStockRepository.findById(newProduct.getId()).isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                            (" o produto com o nome %s não está cadastrado no sistema" +
                                    "(Não existe Produto com o id:%d cadastrado no sistema)")
                                    .formatted(newProduct.getProductName(), newProduct.getId()));
                }
                scheduling.get().getProducts().add(newProduct);
            }
        }
        return schedulingRepository.save(scheduling.get());
    }


    @Transactional
    public Scheduling removeProduct(Long schedulingId, Long productScheduleId) {

        Optional<Scheduling> schedulingOptional = schedulingRepository.findById(schedulingId);

        if (schedulingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe agendamento com o id:%d".formatted(schedulingId));
        }
        Scheduling scheduling = schedulingOptional.get();

        if (scheduling.getProducts().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não há produtos na lista");
        }

        List<ProductScheduling> productSchedulingList = scheduling.getProducts();
        boolean removed = productSchedulingList.removeIf(product -> product.getId().equals(productScheduleId));

        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Produto com o id:%d não encontrado".formatted(productScheduleId));
        }

        scheduling.setProducts(productSchedulingList);

        return schedulingRepository.save(scheduling);
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

