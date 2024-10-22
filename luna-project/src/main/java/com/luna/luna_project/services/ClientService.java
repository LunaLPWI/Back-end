package com.luna.luna_project.services;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
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
    private ViaCepService viaCepService;


    public Client saveClient(ClientRequestDTO clientDTO, AddressDTO addressDTO) {
        if (existsCpf(clientDTO.getCpf())) {
            throw new RuntimeException("CPF já existe.");
        }
        if (existsEmail(clientDTO.getEmail())) {
            throw new RuntimeException("Email já existe.");
        }


        if (!viaCepService.isCepValid(clientDTO.getAddress().getCep())) {
            throw new InvalidCepException("CEP não existe ou está inválido.");
        }

        Address address = viaCepService.saveAddress(addressDTO);
        Client client = ClientMapper.clientRequestDTOtoClient(clientDTO);

        client.setAddress(address);
        System.out.println(addressDTO);
        System.out.println(client.getAddress());
        Client savedClient = clientRepository.save(client);
        savedClient.setAddress(address);

        return savedClient;
    }

    public List<ClientResponseDTO> searchClients() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::clientToClientDTOResponse)
                .collect(Collectors.toList());
    }

    public Boolean existsCpf(String cpf) {
        return clientRepository.existsByCpf(cpf);
    }
    public Boolean existsEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    public ClientResponseDTO searchClientByCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf);
        return ClientMapper.clientToClientDTOResponse(client);
    }
    public ClientResponseDTO searchClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        return ClientMapper.clientToClientDTOResponse(client);
    }

    public ClientResponseDTO searchClientByEmailAndSenha(String email, String senha){
        Client client = clientRepository.findByEmailAndPassword(email, senha);
        return ClientMapper.clientToClientDTOResponse(client);
    }


    public Client searchClientById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.orElse(null); // Retorna null se o cliente não for encontrado
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

    public List<ClientResponseDTO> sortClientsByName() {
        List<ClientResponseDTO> clients = searchClients();
        bubbleSort(clients);
        return clients;
    }

    public Client redefinePassword(Long id, String password) {
       Client client = searchClientById(id);
       client.setPassword(password);
       return clientRepository.save(client);
    }


    private void bubbleSort(List<ClientResponseDTO> clients) {
        int n = clients.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 1; j < n - i; j++) {
                if (clients.get(j - 1).getNome().compareTo(clients.get(j).getNome()) > 0) {
                    ClientResponseDTO temp = clients.get(j);
                    clients.set(j, clients.get(j - 1));
                    clients.set(j - 1, temp);
                }
            }
        }
    }

}
