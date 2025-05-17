package com.luna.luna_project.services;


import com.luna.luna_project.dtos.*;
import com.luna.luna_project.dtos.ChargeRequestDTO;
import com.luna.luna_project.enums.Plans;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.OneStepCardMapper;
import com.luna.luna_project.mapper.OneStepLinkMapper;
import com.luna.luna_project.models.*;
import com.luna.luna_project.repositories.EstablishmentRepository;
import com.luna.luna_project.repositories.OneStepCardRepository;
import com.luna.luna_project.repositories.OneStepLinkRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Transactional
    public OneStepDTO saveOneStep(@Valid OneStepDTO request, String cnpj) {
        Long idEstablish = establishmentRepository.findIdByCnpj(cnpj);

        if (idEstablish == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estabelecimento com CNPJ " + cnpj + " não encontrado.");
        }


        PlanDTO planSaved = planService.savePlan(request);

        if (planSaved == null) {

            throw new ResponseStatusException(HttpStatus.CONFLICT, "O cliente já tem um plano.");
        }

        Plans chargeRequestDTO = request.getChargeRequest();

        OneStepDTO oneStepMapp = new OneStepDTO();
        oneStepMapp.setPlan(planSaved);
        oneStepMapp.setChargeRequest(chargeRequestDTO);

        oneStepMapp.setIdEstablish(idEstablish);

        OneStepCardSubscription oneConvert = oneStepCardMapper.oneStepDTOtoOneStep(oneStepMapp);


        oneStepCardRepository.save(oneConvert);

        return oneStepMapp;
    }





    public OneStepLinkDTO saveOneStepLink(@Valid OneStepDTO request){
        OneStepLink oneStep = PlanEFI.createOneStepLink(request);

        OneStepLinkDTO oneStepMapp = oneStepLinkMapper.oneSetToOneStepDTO(oneStep);
        OneStepLink oneConvert = oneStepLinkMapper.oneStepDTOtoOneStep(oneStepMapp);


        chargeService.saveCharge(oneStep);

        OneStepLink saveOneStep = oneStepLinkRepository.save(oneConvert);

        return oneStepLinkMapper.oneSetToOneStepDTO(saveOneStep);
    }
}
