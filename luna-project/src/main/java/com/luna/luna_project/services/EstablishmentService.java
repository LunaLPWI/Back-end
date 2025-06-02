package com.luna.luna_project.services;

import com.luna.luna_project.dtos.AddressDTO;

import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.dtos.establishment.EstablishPlanRequestDTO;
import com.luna.luna_project.dtos.establishment.EstablishmentRequestDTO;
import com.luna.luna_project.dtos.establishment.EstablishmentResponseDTO;
import com.luna.luna_project.mapper.AddressMapper;
import com.luna.luna_project.mapper.EstablishmentMapper;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.models.AddressCoord;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Establishment;

import com.luna.luna_project.repositories.AddressRepository;
import com.luna.luna_project.repositories.AssessmentRepository;
import com.luna.luna_project.repositories.ClientRepository;
import com.luna.luna_project.repositories.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private EstablishmentMapper establishmentMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AssessmentRepository assessmentRepository;



    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }
    @Transactional
    public Establishment saveEstablishment(Establishment establishment, AddressDTO address, Long idClient) throws Exception {
        if (establishmentRepository.existsByCnpj(establishment.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"CNPJ já cadastrado");
        }
        if (!viaCepService.isCepValid(address.getCep())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"CEP invalido");
        }
        GeoCodeGoogle geoCodeGoogle = new GeoCodeGoogle();
        AddressCoord addressCoord = geoCodeGoogle.getCoordenadas(address.formatAddress());
        establishment.setLat(addressCoord.getLat());
        establishment.setLng(addressCoord.getLng());
        Address addressSaved =  addressMapper.addressDTOtoAddress(address);
        establishment.setAddress(addressSaved);

        establishment.setFavorite(false);
        establishment.setPlan(null);
        Establishment establishmentSaved = establishmentRepository.save(establishment);
        Client client = clientRepository.findClientById(idClient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado"));
        Set<Establishment> establishments = new HashSet<>(client.getEstablishments());
        establishments.add(establishmentSaved);
        client.setEstablishments(establishments);
        clientRepository.save(client);

        return establishmentSaved;
    }


    @Transactional
    public EstablishmentResponseDTO putEstablishPlan(EstablishPlanRequestDTO establishment, PlanDTO plan) {
        Establishment establishmentMapp = establishmentMapper.establichmentRequestToEstablishmentPlan(establishment);

        Establishment existEstablish = establishmentRepository.findByCnpj(establishmentMapp.getCnpj())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estabelecimento com CPNJ " + establishment.getCnpj() + " não encontrado."));

        existEstablish.setPlan(planMapper.planDTOtoPlan(plan));

        establishmentRepository.save(existEstablish);

        return establishmentMapper.establishmentToEstablshmentResponse(establishmentMapp);
    }


    public List<EstablishmentResponseDTO> getAllEstablishments(double lat, double lng) {
        List<Establishment> establishments = establishmentRepository.findEstablishmentsByLocationNative(lat, lng, 5.0);

        List<EstablishmentResponseDTO> responseList = establishments.stream()
                .map(establishmentMapper::establishmentToEstablshmentResponse)
                .toList();

        responseList.forEach(dto -> {
            Double avgRating = assessmentRepository.findAverageRatingByEstablishmentId(dto.getId());
            dto.setAvarageRating(avgRating != null ? avgRating : 0.0);
        });

        return responseList;
    }
    @Transactional
    public void delete(Long id) {
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(id);
        if (establishmentOpt.isPresent()) {
            establishmentRepository.delete(establishmentOpt.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não há estabelecimentos com esse id");
        }
    }

    public List<Establishment> searchByName(String name) {
        return establishmentRepository.findByNameContainingIgnoreCase(name);
    }

    public Client registerEmployee(Long idEmployee, Long idEstablishment) {
        Optional<Client> clientOpt = clientRepository.findById(idEmployee);
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(idEstablishment);
        if(clientOpt.isEmpty() || establishmentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não há estabelecimento ou Funcionário com estes ids");
        }
        if (clientOpt.get().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Este usuário não é um funcionário");
        }
        if(clientOpt.get().getEstablishments()!=null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Este ja é um funcionário de um estabelecimento");
        }
        if(clientOpt.get().getEstablishments().contains(establishmentOpt.get())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Este usuário já trabalha de neste estabelecimento");
        }
        Set<Establishment> establishments = new HashSet<>();
        establishments.add(establishmentOpt.get());
        clientOpt.get().setEstablishments(establishments);
        return clientRepository.save(clientOpt.get());
    }

    public Establishment changeInfo(Establishment establishment) {
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(establishment.getId());
        if (establishmentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não há estabelecimentos com esse id");
        }

        return establishmentRepository.save(establishmentOpt.get());
    }

    public Set<Establishment> searchByOwnerId(Long id) {

        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não há usuário com este id");
        }


        return clientOpt.get().getEstablishments();
    }
}
