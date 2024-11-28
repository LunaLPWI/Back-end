package com.luna.luna_project.services;


import com.luna.luna_project.dtos.*;
import com.luna.luna_project.dtos.ChargeRequestDTO;
import com.luna.luna_project.enums.Plans;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.OneStepCardMapper;
import com.luna.luna_project.mapper.OneStepLinkMapper;
import com.luna.luna_project.models.*;
import com.luna.luna_project.repositories.OneStepCardRepository;
import com.luna.luna_project.repositories.OneStepLinkRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OneStepService {

    @Autowired
    private OneStepCardRepository oneStepCardRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OneStepCardMapper oneStepCardMapper;
    @Autowired
    private PlanService planService;
    @Autowired
    private ChargeService chargeService;
    @Autowired
    private OneStepLinkMapper oneStepLinkMapper;
    @Autowired
    private OneStepLinkRepository oneStepLinkRepository;

    public OneStepDTO saveOneStep(@Valid OneStepDTO request, String paymentToken, String cpf) {
        Client client = clientService.searchClientByCpf(cpf);

        PlanDTO planSaved = planService.savePlan(request);

        Plans chargeRequestDTO = request.getChargeRequest();

        chargeService.saveCharge(request, planSaved);

        OneStepCardSubscription oneStep = PlanEFI.createOneStep(planSaved, paymentToken, chargeRequestDTO, client);

        OneStepDTO oneStepMapp = oneStepCardMapper.oneSetToOneStepDTO(oneStep);

        oneStepMapp.setChargeRequest(chargeRequestDTO);
        oneStepMapp.setPlan(planSaved);


        OneStepCardSubscription oneConvert = oneStepCardMapper.oneStepDTOtoOneStep(oneStepMapp);
        OneStepCardSubscription saveOneStep = oneStepCardRepository.save(oneConvert);

        return oneStepMapp;
    }


    public OneStepLinkDTO saveOneStepLink(@Valid OneStepDTO request){
        OneStepLink oneStep = PlanEFI.createOneStepLink(request);

        OneStepLinkDTO oneStepMapp = oneStepLinkMapper.oneSetToOneStepDTO(oneStep);
        OneStepLink oneConvert = oneStepLinkMapper.oneStepDTOtoOneStep(oneStepMapp);

        OneStepLink saveOneStep = oneStepLinkRepository.save(oneConvert);

        return oneStepLinkMapper.oneSetToOneStepDTO(saveOneStep);
    }
}
