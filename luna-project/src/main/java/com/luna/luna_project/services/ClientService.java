package com.luna.luna_project.services;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.ClientDTO;
import com.luna.luna_project.exceptions.InvalidCepException;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.repositories.ClientRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ViaCepService viaCepService;

    public ClientDTO saveClient(ClientDTO clientDTO, AddressDTO addressDTO) {
        if (existsCpf(clientDTO.cpf())) {
            throw new RuntimeException("CPF já existe.");
        }
        if (!viaCepService.isCepValid(clientDTO.address().getCep())) {
            throw new InvalidCepException("CEP não existe ou está inválido.");
        }
        Address address = viaCepService.saveAddress(addressDTO);
        Client client = clientMapper.clientDTOtoClient(clientDTO);
        client.setAddress(address);
        Client savedClient = clientRepository.save(client);

        return clientMapper.clientToClientDTO(savedClient);
    }

    public List<ClientDTO> searchClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::clientToClientDTO)
                .collect(Collectors.toList());
    }

    public Boolean existsCpf(String cpf) {
        return clientRepository.existsByCpf(cpf);
    }

    public ClientDTO searchClientByCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf);
        return clientMapper.clientToClientDTO(client);
    }

    public ClientDTO searchClientById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            return clientMapper.clientToClientDTO(clientOptional.get());
        } else {
            return null; // Or throw an exception if preferred
        }
    }

    @Transactional
    public void deleteClient(String cpf) {
        Client client = clientRepository.findByCpf(cpf);
        if (client != null) {
            clientRepository.delete(client);
        } else {
            throw new ValidationException("Cliente com CPF " + cpf + " não encontrado.");
        }
    }
}
