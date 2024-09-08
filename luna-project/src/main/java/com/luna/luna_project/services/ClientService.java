package com.luna.luna_project.services;

import com.luna.luna_project.dtos.ClientDTO;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.repositories.ClientRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    public ClientDTO saveClient(@Valid ClientDTO clientDTO) {
        Client client = clientMapper.clientDTOtoClient(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.clientToClientDTO(savedClient);
    }

    public List<ClientDTO> searchClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::clientToClientDTO)
                .collect(Collectors.toList());
    }

    public Boolean existsCpf(String cpf){
        return clientRepository.existsByCpf(cpf);
    }

    public ClientDTO searchClientByCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf);
        return clientMapper.clientToClientDTO(client);
    }

    @Transactional
    public void deleteClient(String cpf){
        Client client = clientRepository.findByCpf(cpf);
        if (client != null) {
            clientRepository.delete(client);
        } else {
            throw new ValidationException("Cliente com CPF " + cpf + " não encontrado.");
        }
    }
}