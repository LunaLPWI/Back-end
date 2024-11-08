package com.luna.luna_project.services;
import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.exceptions.InvalidCepException;
import com.luna.luna_project.mapper.AddressMapper;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ViaCepService {
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;

    public AddressDTO searchAddressByCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, AddressDTO.class);
    }
    public void getAddressByCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(VIA_CEP_URL, cep);
        ResponseEntity<AddressDTO> response = restTemplate.getForEntity(url, AddressDTO.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || response.getBody().getComplemento() == null) {
            throw new InvalidCepException("CEP não existe ou está inválido.");
        }
        response.getBody();
    }

    public Address saveAddress(AddressDTO addressDTO) {
        if (addressDTO.getCep() == null) {
            throw new InvalidCepException("CEP não existe ou está inválido.");
        }
        AddressDTO addressFromCep = searchAddressByCep(addressDTO.getCep());

        Address addressConvert = addressMapper.addressDTOtoAddress(addressFromCep);

        addressConvert.setNumber(addressDTO.getNumber());
        addressConvert.setComplemento(addressDTO.getComplemento());

        return addressRepository.save(addressConvert);
    }


    public boolean isCepValid(String cep) {
        try {
            getAddressByCep(cep);
            return true;
        } catch (InvalidCepException e) {
            return false;
        }
    }
}
