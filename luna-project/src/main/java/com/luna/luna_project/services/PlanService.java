package com.luna.luna_project.services;

import com.luna.luna_project.dtos.CpfDTO;
import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.enums.Plans;
import com.luna.luna_project.enums.Task;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.repositories.PlanRepository;
import com.luna.luna_project.repositories.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {
    @Autowired
    private ClientService clientService;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private SubscriptionRepository subscriptionRepository;


    public PlanDTO savePlan(OneStepDTO request, Long idClient) {
        Client client = clientService.findById(idClient);
        PlanDTO planDTO = request.getPlan();

        Boolean planBol = planRepository.existsByIdClient(idClient);

        if (planBol){
            return null;
        }

        Plan planDTOSaved = PlanEFI.createPlan(planDTO, request.getChargeRequest());
        if (planDTOSaved == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Plan planMapp = planMapper.planDTOtoPlan(planDTOSaved);
        planMapp.setIdClient(idClient);

        Plan plan = planRepository.save(planMapp);
        client.setPlan(plan);


        return planMapper.planToPlanDTO(plan);
    }

    public Long searchClientsByPlan(String name) {
        return planRepository.countByName(name);
    }

    @Transactional
    public String cancelPlan(CpfDTO cpfDto) {
        Client client = clientService.searchClientByCpf(cpfDto.getCpf());
        Long idClient = client.getId();

        String subscriptionId = subscriptionRepository.findSubscriptionIdByIdClient(idClient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há nenhum cliente com esse id com plano"));

        String cancelSu = PlanEFI.cancelSubscription(subscriptionId);

        client.setPlan(null);

        subscriptionRepository.deleteBySubscriptionId(subscriptionId);
        planRepository.deleteByIdClient(idClient);

        return cancelSu;
    }


    public Long countPlan() {
        long id = subscriptionRepository.count();

        if (id == 0){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há nenhum id");
        }
        return id;
    }

    //RENOVA OS CUPONS COM BASE NO TEMPO
    //NÃO TA PRONTO

    public void renewCoupons(Long clientId) {
      Optional<Plan> planOptional =  planRepository.findPlanIdClient(clientId);
      if (planOptional.isPresent()){
          Plan plan = planOptional.get();
          plan.setQtdCoupons(4);
          List<Task> coupons = new ArrayList<>();
          switch (plan.getName()){
              case"Cabelo ou barba":
                  coupons.addAll(List.of(Task.BARBA, Task.CORTE));
                  break;
              case "Raspar a cabeça + barba":
                  coupons.addAll(List.of(Task.BARBA, Task.RASPARCABECA));
                  break;
              case"Raspar a cabeça":
                  coupons.add(Task.RASPARCABECA);
              default:
                  coupons.addAll(List.of(Task.BARBA, Task.CORTE , Task.BARBA,Task.CORTE));
          }
      }
    }

    //RETORNAS AS TASKS(SERVIÇOS) A SEREM PAGOS COM BASE NO PLANO

    //INCOMPLETO

    public List<Task> returnTasksToPay(Plan plan, List<Task> tasks){
        if(plan.getQtdCoupons()>= tasks.size()){

            switch (plan.getName()){
                case"Cabelo ou barba":
                   plan.setQtdCoupons(plan.getQtdCoupons() -1);
                   tasks = tasks.stream().filter(task ->
                       task != Task.CORTE && task!= Task.BARBA).toList();
                   break;
                case "Raspar a cabeça + barba":

                    break;
                case"Raspar a cabeça":
                    plan.setQtdCoupons(plan.getQtdCoupons() -1);
                    tasks = tasks.stream().filter(task ->
                            task != Task.RASPARCABECA).toList();
                    break;
                default:

            }
            plan.setQtdCoupons(plan.getQtdCoupons() -tasks.size());

        }
    }


}
