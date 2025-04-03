package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.establishment.EstablichmentRequestDTO;
import com.luna.luna_project.dtos.establishment.EstablichmentResponseDTO;
import com.luna.luna_project.mapper.AddressMapper;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.mapper.EstablishmentMapper;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.services.EstablishmentService;
import com.luna.luna_project.services.GeoCodeGoogle;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;
    @Autowired
    private EstablishmentMapper establishmentMapper;
    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private AddressMapper addressMapper;


    @Autowired
    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    // Endpoint para salvar um estabelecimento
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<EstablichmentResponseDTO> saveEstablishment(@Valid @RequestBody EstablichmentRequestDTO establishmentRequest) throws Exception {
        GeoCodeGoogle geoCodeGoogle = new GeoCodeGoogle();

        Establishment savedEstablishment = establishmentService.saveEstablishment(establishmentMapper.establichmentRequestToEstablishment(establishmentRequest), establishmentRequest.getAddressDTO());
        EstablichmentResponseDTO establishmentResponseDTO = establishmentMapper.establichmentToEstablshmentResponse(savedEstablishment);
        establishmentResponseDTO.setAddressDTO(geoCodeGoogle.getEnderecoFromCoordenadas(savedEstablishment.getLat(), savedEstablishment.getLng()));

        return new ResponseEntity<>(establishmentResponseDTO, HttpStatus.CREATED);
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
    public ResponseEntity<List<EstablichmentResponseDTO>> getNearbyEstablishments(@RequestParam double lat, @RequestParam double logn) {
        GeoCodeGoogle geoCodeGoogle = new GeoCodeGoogle();
        List<EstablichmentResponseDTO> establishmentList = establishmentService
                .getAllEstablishments(lat, logn)
                .stream()
                .map(establishment -> {
                    EstablichmentResponseDTO responseDTO = establishmentMapper.establichmentToEstablshmentResponse(establishment);
                    try {
                        responseDTO.setAddressDTO(geoCodeGoogle.getEnderecoFromCoordenadas(establishment.getLat(), establishment.getLng()));
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao obter endereço do estabelecimento", e);
                    }
                    return responseDTO;
                })
                .toList();

        return ResponseEntity.ok(establishmentList);
    }

    // Endpoint para alterar informações do estabelecimento
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<EstablichmentResponseDTO> changeEstablishmentInfo(@PathVariable Long id, @Valid @RequestBody EstablichmentRequestDTO establishmentRequest) throws Exception {
        Establishment savedEstablishment = establishmentMapper.establichmentRequestToEstablishment(establishmentRequest);
        Establishment establishment = establishmentService.saveEstablishment(savedEstablishment, establishmentRequest.getAddressDTO());
        establishment.setId(id);
        Establishment updatedEstablishment = establishmentService.changeInfo(establishment);
        return new ResponseEntity<>(establishmentMapper.establichmentToEstablshmentResponse(updatedEstablishment), HttpStatus.OK);
    }


}
