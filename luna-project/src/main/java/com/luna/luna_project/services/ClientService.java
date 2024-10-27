package com.luna.luna_project.services;

import com.luna.luna_project.configurations.jwt.GerenciadorTokenJwt;
import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.client.ClientLoginDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.client.ClientTokenDTO;
import com.luna.luna_project.exceptions.InvalidCepException;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.repositories.ClientMapper;
import com.luna.luna_project.repositories.ClientRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
            throw new RuntimeException("CPF já existe.");
        }
        if (existsEmail(clientDTO.getEmail())) {
            throw new RuntimeException("Email já existe.");
        }
//        if (!viaCepService.isCepValid(clientDTO.getAddress().getCep())) {
//            throw new InvalidCepException("CEP não existe ou está inválido.");
//        }

        Address address = viaCepService.saveAddress(addressDTO);
        Client client = clientMapper.clientRequestDTOtoClient(clientDTO);
        String encryptedPassword  = passwordEncoder.encode(client.getPassword());
        client.setPassword(encryptedPassword);
        client.setAddress(address);

        System.out.println(address.getCidade());
        return clientRepository.save(client);
    }

    public List<ClientResponseDTO> searchClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::clientToClientDTOResponse)
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
        return clientMapper.clientToClientDTOResponse(client);
    }
    public Client searchClientByEmail(String email) {

        return clientRepository.findByEmail(email).get();
    }

    public ClientResponseDTO searchClientByEmailAndSenha(String email, String senha){
        Client client = clientRepository.findByEmailAndPassword(email, senha);
        return clientMapper.clientToClientDTOResponse(client);
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
    public Client searchByUsername (String nome){
        return clientRepository.findByNome(nome);
    }

    public ClientTokenDTO authenticate(ClientLoginDTO clientLoginDTO) {

        if(searchClientByEmail(clientLoginDTO.getEmail()) == null){
            throw new ResponseStatusException(404, "Email do usuário não cadastrado", null);
        }
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                clientLoginDTO.getEmail(), clientLoginDTO.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Client clientAuthentication = searchClientByEmail(clientLoginDTO.getEmail());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(clientAuthentication.getAddress().getCep());



        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return  clientMapper.clientToClientDTO(clientAuthentication, token);
    }


    public void resetPassword(String email, String newPassword) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if (clientOptional.isEmpty()) {
            throw new ValidationException("Usuário com e-mail " + email + " não encontrado.");
        }

        Client client = clientOptional.get();
        String encryptedPassword = passwordEncoder.encode(newPassword);
        client.setPassword(encryptedPassword);
        clientRepository.save(client);
    }


}
