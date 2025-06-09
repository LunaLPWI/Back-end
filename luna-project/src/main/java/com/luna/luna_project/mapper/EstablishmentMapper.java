package com.luna.luna_project.mapper;


import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.dtos.establishment.EstablishPlanRequestDTO;
import com.luna.luna_project.dtos.establishment.EstablishmentRequestDTO;
import com.luna.luna_project.dtos.establishment.EstablishmentResponseDTO;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.services.OneStepService;
import com.luna.luna_project.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class EstablishmentMapper {

    @Autowired
    AddressMapper addressMapper;


    @Autowired
    OneStepCardMapper oneStepCardMapper;
    @Autowired
    PlanMapper planMapper;
    @Autowired
    private PlanService planService;
    @Autowired
    private OneStepService oneStepService;




    public Establishment establishmentRequestToEstablishmentPlan(EstablishPlanRequestDTO establishPlanRequestDTO) {
        Establishment establishment = new Establishment();
        OneStepDTO oneStepDTO = establishPlanRequestDTO.getOneStepDTO();
        oneStepCardMapper.oneStepDTOtoOneStep(oneStepDTO);
        establishment.setCnpj(establishPlanRequestDTO.getCnpj());


        return establishment;
    }


    public Establishment establishmentRequestToEstablishment(EstablishmentRequestDTO establishmentRequestDTO) {
        // Criação da instância de Address se presente
        Establishment establishment = new Establishment();
        establishment.setName(establishmentRequestDTO.getName());
//        OneStepDTO oneStepDTO = establishmentRequestDTO.getOneStepDTO();
//        oneStepCardMapper.oneStepDTOtoOneStep(oneStepDTO);
        establishment.setCnpj(establishmentRequestDTO.getCnpj());
        establishment.setCloseHour(establishmentRequestDTO.getCloseHour());
        establishment.setOpenHour(establishmentRequestDTO.getOpenHour());


        return establishment;
    }


    public  EstablishmentResponseDTO establishmentToEstablshmentResponse(Establishment establichment){

        // Criação do AddressDTO se presente


        // Criação do PlanDTO se presente
        PlanDTO planDTO = planMapper.planToPlanDTO(establichment.getPlan());

        // Agora cria e retorna o EstablichmentResponseDTO

        EstablishmentResponseDTO responseDTO = new EstablishmentResponseDTO();
        responseDTO.setId(establichment.getId());
        responseDTO.setName(establichment.getName());
        responseDTO.setCnpj(establichment.getCnpj());
        responseDTO.setOpenHour(establichment.getOpenHour());
        responseDTO.setCloseHour(establichment.getCloseHour());
        responseDTO.setFavorite(establichment.getFavorite());
        responseDTO.setLat(establichment.getLat());
        responseDTO.setLng(establichment.getLng());
        responseDTO.setAddressDTO(addressMapper.addressToAddressDTO(establichment.getAddress()));
        responseDTO.setFavorite(establichment.getFavorite());


        return responseDTO;
    }

}
