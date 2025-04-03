package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.dtos.establishment.EstablichmentRequestDTO;
import com.luna.luna_project.dtos.establishment.EstablichmentResponseDTO;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.services.EstablishmentService;
import com.luna.luna_project.services.GeoCodeGoogle;
import com.luna.luna_project.services.OneStepService;
import com.luna.luna_project.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class EstablishmentMapper {

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    PlanMapper planMapper;
    @Autowired
    private PlanService planService;
    @Autowired
    private OneStepService oneStepService;





    public Establishment establichmentRequestToEstablishment(EstablichmentRequestDTO establichmentRequestDTO) {
        // Criação da instância de Address se presente
        Address address = addressMapper.addressDTOtoAddress(establichmentRequestDTO.getAddressDTO());
        Establishment establishment = new Establishment();
        establishment.setName(establichmentRequestDTO.getName());
        PlanDTO plandto = establichmentRequestDTO.getPlanDTO();
        planMapper.planDTOtoPlan(plandto);
        establishment.setCnpj(establichmentRequestDTO.getCnpj());

        return establishment;
    }

    public EstablichmentResponseDTO establichmentToEstablshmentResponse(Establishment establichment){
        // Criação do AddressDTO se presente


        // Criação do PlanDTO se presente
        PlanDTO planDTO = planMapper.planToPlanDTO(establichment.getPlan());

        // Agora cria e retorna o EstablichmentResponseDTO
        EstablichmentResponseDTO responseDTO = new EstablichmentResponseDTO();
        responseDTO.setId(establichment.getId());
        responseDTO.setName(establichment.getName());// Atribui o AddressDTO convertido
        responseDTO.setPlanDTO(planDTO);  // Atribui o PlanDTO convertido
        responseDTO.setCnpj(establichment.getCnpj());

        return responseDTO;
    }

}
