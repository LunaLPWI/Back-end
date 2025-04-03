package com.luna.luna_project.services;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.models.AddressCoord;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.repositories.AddressRepository;
import com.luna.luna_project.repositories.ClientRepository;
import com.luna.luna_project.repositories.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ViaCepService viaCepService;


    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }
    @Transactional
    public Establishment saveEstablishment(Establishment establishment, AddressDTO address) throws Exception {
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
        return establishmentRepository.save(establishment);
    }

    public List<Establishment> getAllEstablishments(double lat, double lng) {
        return establishmentRepository.findEstablishmentsByLocationNative(lat, lng, 5.0);
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

    public Client registerEmployee(Long idEmployee, Long idEstablishment) {
        Optional<Client> clientOpt = clientRepository.findById(idEmployee);
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(idEstablishment);
        if(clientOpt.isEmpty() || establishmentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não há estabelecimento ou Funcionário com estes ids");
        }
        if (clientOpt.get().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Este usuário não é um funcionário");
        }
        if(clientOpt.get().getEstablishment()!=null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Este ja é um funcionário de um estabelecimento");
        }
        if(clientOpt.get().getEstablishment().getCnpj() == establishmentOpt.get().getCnpj()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Este estabilecimento não pertence a este usuário");
        }
        clientOpt.get().setEstablishment(establishmentOpt.get());
        return clientRepository.save(clientOpt.get());
    }

    public Establishment changeInfo(Establishment establishment) {
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(establishment.getId());
        if (establishmentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não há estabelecimentos com esse id");
        }

        return establishmentRepository.save(establishmentOpt.get());
    }





}
