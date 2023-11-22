package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.AddressModel;
import br.com.esphera.delivery.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressModel createAddress(AddressRecord dto){
        AddressModel address = new AddressModel(dto);
        addressRepository.save(address);
        return address;
    }

    public AddressModel getAddressById(Integer addressId){
        AddressModel address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Nenhum endereço encontrado com este id!"));
        return address;
    }

    public AddressModel putAddress(Integer addressId, AddressRecord dto){
        AddressModel address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Nenhum endereço encontrado com este id!"));
        address.setLogradouro(dto.logradouro());
        address.setBairro(dto.bairro());
        address.setComplemento(dto.complemento());
        address.setCep(dto.cep());
        address.setLocalidade(dto.localidade());
        address.setUF(dto.UF());
        address.setNumberHouse(dto.numberHouse());
        addressRepository.save(address);
        return address;
    }
}
