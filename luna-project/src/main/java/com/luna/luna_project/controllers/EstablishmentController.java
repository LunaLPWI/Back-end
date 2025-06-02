package com.luna.luna_project.controllers;


import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.establishment.EstablishPlanRequestDTO;
import com.luna.luna_project.dtos.establishment.EstablishmentRequestDTO;
import com.luna.luna_project.dtos.establishment.EstablishmentResponseDTO;
import com.luna.luna_project.mapper.AddressMapper;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.mapper.EstablishmentMapper;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.services.EstablishmentService;
import com.luna.luna_project.services.GeoCodeGoogle;
import com.luna.luna_project.services.OneStepService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;
    @Autowired

    private ClientMapper clientMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private OneStepService oneStepService;
    @Autowired
    private EstablishmentMapper establishmentMapper;



    @Autowired
    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Establishment>> searchByName(@RequestParam String name) {
        List<Establishment> result = establishmentService.searchByName(name);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/owner/{id}")
    public ResponseEntity<List<EstablishmentResponseDTO>> getByOwnerId(@PathVariable Long id) {
        Set<Establishment> result = establishmentService.searchByOwnerId(id);
        List<EstablishmentResponseDTO> establishmentReponse = result.stream().
                map(establishmentMapper::establishmentToEstablshmentResponse).toList();
        return ResponseEntity.ok(establishmentReponse);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<EstablishmentResponseDTO> saveEstablishment(@RequestBody EstablishmentRequestDTO establishmentRequest) throws Exception {

        Establishment savedEstablishment = establishmentService.saveEstablishment(
                establishmentMapper.establishmentRequestToEstablishment(establishmentRequest),
                establishmentRequest.getAddressDTO(), establishmentRequest.getClientId()
        );

        if (savedEstablishment == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao salvar estabelecimento.");
        }
        EstablishmentResponseDTO establishmentResponseDTO = establishmentMapper.establishmentToEstablshmentResponse(savedEstablishment);

        establishmentResponseDTO.setAddressDTO(addressMapper.addressToAddressDTO(savedEstablishment.getAddress()));

        return new ResponseEntity<>(establishmentResponseDTO, HttpStatus.CREATED);
    }



    @PutMapping("/plan-for-establishment")
    public ResponseEntity<EstablishmentResponseDTO> savePlanEstablish(@RequestBody EstablishPlanRequestDTO establishmentRequest){
        OneStepDTO oneStepSaved = oneStepService.saveOneStep(establishmentRequest.getOneStepDTO(), establishmentRequest.getCnpj());
        oneStepService.saveOneStepLink(oneStepSaved);

        EstablishmentResponseDTO putEstablishPlan = establishmentService.putEstablishPlan(establishmentRequest,  oneStepSaved.getPlan());
        return new ResponseEntity<>(putEstablishPlan, HttpStatus.CREATED);
    }

    // Endpoint para excluir um estabelecimento
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable Long id) {
        establishmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para registrar um funcionário em um estabelecimento
    @Secured("ROLE_ADMIN")
    @PostMapping("/registerEmployee")
    public ResponseEntity<ClientResponseDTO> registerEmployee(@RequestParam Long idEmployee, @RequestParam Long idEstablishment) {
        ClientResponseDTO registeredClient = clientMapper.clientToClientDTOResponse(establishmentService.registerEmployee(idEmployee, idEstablishment));
        return new ResponseEntity<>(registeredClient, HttpStatus.OK);
    }

    @PostMapping("/nearbyestablishments")
    public ResponseEntity<List<EstablishmentResponseDTO>> getNearbyEstablishments(@RequestParam double lat, @RequestParam double lgn) {
        GeoCodeGoogle geoCodeGoogle = new GeoCodeGoogle();
        List<EstablishmentResponseDTO> establishmentList = establishmentService
                .getAllEstablishments(lat, lgn)
                .stream()
                .map(establishment -> {
                    try {
                        establishment.setAddressDTO(geoCodeGoogle.getEnderecoFromCoordenadas(establishment.getLat(), establishment.getLng()));
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao obter endereço do estabelecimento", e);
                    }
                    return establishment;
                })
                .toList();

        return ResponseEntity.ok(establishmentList);
    }

    // Endpoint para alterar informações do estabelecimento
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<EstablishmentResponseDTO> changeEstablishmentInfo(@PathVariable Long id, @Valid @RequestBody EstablishmentRequestDTO establishmentRequest, Long idClient) throws Exception {
        Establishment savedEstablishment = establishmentMapper.establishmentRequestToEstablishment(establishmentRequest);
        Establishment establishment = establishmentService.saveEstablishment(savedEstablishment, establishmentRequest.getAddressDTO(), idClient);
        establishment.setId(id);
        Establishment updatedEstablishment = establishmentService.changeInfo(establishment);
        return new ResponseEntity<>(establishmentMapper.establishmentToEstablshmentResponse(updatedEstablishment), HttpStatus.OK);
    }


}
