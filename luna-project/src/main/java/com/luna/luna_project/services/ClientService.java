package com.luna.luna_project.services;

import com.luna.luna_project.configurations.jwt.GerenciadorTokenJwt;
import com.luna.luna_project.dtos.ResetPasswordDTO;
import com.luna.luna_project.dtos.addresses.AddressDTO;
import com.luna.luna_project.dtos.client.ClientLoginDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.client.ClientTokenDTO;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.mapper.ClientMapper;
import com.luna.luna_project.repositories.ClientRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Comparator;
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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public Client saveClient(ClientRequestDTO clientDTO, AddressDTO addressDTO) {
        if (existsCpf(clientDTO.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"CPF já cadastrado");
        }
        if (existsEmail(clientDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email já cadastrado");
        }
        Address address = viaCepService.saveAddress(addressDTO);
        Client client = clientMapper.clientRequestDTOtoClient(clientDTO);
        String encryptedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encryptedPassword);
        client.setAddress(address);

        return clientRepository.save(client);
    }

    public List<ClientResponseDTO> searchClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::clientToClientDTOResponse)
                .collect(Collectors.toList());
    }

    public List<Client> searchEmployees(String role) {
        return clientRepository.findByRolesContaining(role);
    }


    public static Client pesquisaBinaria(Client[] lista, String nomeAlvo, int esquerda, int direita) {
        if (esquerda > direita) {
            return null;
        }

        int meio = esquerda + (direita - esquerda) / 2;

        if (lista[meio].getName().equals(nomeAlvo)) {
            return lista[meio];
        } else if (lista[meio].getName().compareTo(nomeAlvo) < 0) {
            return pesquisaBinaria(lista, nomeAlvo, meio + 1, direita);
        } else {
            return pesquisaBinaria(lista, nomeAlvo, esquerda, meio - 1);
        }
    }

    public Boolean existsCpf(String cpf) {
        return clientRepository.existsByCpf(cpf);
    }

    public Boolean existsEmail(String email) {

        return clientRepository.existsByEmail(email);
    }

    public Client searchClientByCpf(String cpf) {
        Optional<Client> clientOptional = clientRepository.findByCpf(cpf);
        if (clientOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        return clientOptional.get();
    }

    public Client searchClientByEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);

        if(clientOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Cliente como o email:"+email+"não existe na base de dados");
        }
        return clientRepository.findByEmail(email).get();
    }

    public ClientResponseDTO searchClientByEmailAndSenha(String email, String senha) {
        Optional <Client> clientOptional = clientRepository.findByEmailAndPassword(email, senha);

        if (clientOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Email e senha não correspondentes a nenhuma conta");
        }

        return clientMapper.clientToClientDTOResponse(clientOptional.get());
    }


    public Client searchClientById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "não há cliente com esse id");
        }
        return clientOptional.get();
    }

    @Transactional
    public void deleteClient(String cpf) {
        Optional <Client> clientOptional = clientRepository.findByCpf(cpf);
        if (clientOptional.isPresent()) {
            clientRepository.delete(clientOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente com CPF " + cpf + " não encontrado.");
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
                if (clients.get(j - 1).getName().compareTo(clients.get(j).getName()) > 0) {
                    ClientResponseDTO temp = clients.get(j);
                    clients.set(j, clients.get(j - 1));
                    clients.set(j - 1, temp);
                }
            }
        }
    }

    public Client searchByUsername(String nome) {
        List<Client> clientList = clientRepository.findAll();
        Client[] clientVetor = clientList.toArray(new Client[0]);
        Arrays.sort(clientVetor, Comparator.comparing(Client::getName));

        Client client = pesquisaBinaria(clientVetor, nome, 0, clientVetor.length - 1);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Client not found");
        }

        return client;
    }


    public ClientTokenDTO authenticate(ClientLoginDTO clientLoginDTO) {
        Client client = searchClientByEmail(clientLoginDTO.getEmail());
        if (client == null) {
            throw new ResponseStatusException(404, "Email do usuário não cadastrado", null);
        }

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                clientLoginDTO.getEmail(), clientLoginDTO.getPassword(), client.getAuthorities());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return clientMapper.clientToClientDTO(client, token);
    }


    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<Client> clientOptional = clientRepository.findByEmail(resetPasswordDTO.getEmail());
        if (clientOptional.isEmpty()) {
            throw new ValidationException("Usuário com e-mail " + resetPasswordDTO.getEmail() + " não encontrado.");
        }

        Client client = clientOptional.get();
        String encryptedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
        client.setPassword(encryptedPassword);
        clientRepository.save(client);
    }


}
