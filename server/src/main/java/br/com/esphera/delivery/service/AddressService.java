package br.com.esphera.delivery.service;

import br.com.esphera.delivery.ApiMaps.MapsAPi;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response.ResponseAddressValidationMapsAPI;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response.ResponseDistanceAPI;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.AddressModel;
import br.com.esphera.delivery.models.DTOS.DistanceDurationDTO;
import br.com.esphera.delivery.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MapsAPi mapsAPi;


    public AddressModel createAddress(AddressRecord dto){
        AddressModel address = new AddressModel(dto);
        addressRepository.save(address);
        return address;
    }

    public String getPlaceIdApiMaps(AddressRecord addressRecord){
        try{
            ResponseAddressValidationMapsAPI response = mapsAPi.validateAddress(addressRecord);
            System.out.println(response.result().geocode().placeId());
            return response.result().geocode().placeId();
        }
        catch (Exception e){
            throw new ResourceNotFoundException("Erro ao validar endereco!");
        }
    }

    public AddressModel getAddressById(Integer addressId){
        AddressModel address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Nenhum endereço encontrado com este id!"));
        return address;
    }


    public DistanceDurationDTO getDistanceDelivery(CompanyModel companyModel, String localIdDestine){
        ResponseDistanceAPI response = mapsAPi.getDistanceRouteDelivery(companyModel.getIdLocalCompanyMaps(), localIdDestine);
        Integer distance = response.rows().get(0).elements().get(0).distance().value();
        Double distanceKm = (double) (distance / 1000);
        Integer duration = response.rows().get(0).elements().get(0).duration().value();
        Integer durationInMinutes = (duration / 60) / 60;
        DistanceDurationDTO distanceDurationDTO = new DistanceDurationDTO(durationInMinutes, distanceKm);
        return distanceDurationDTO;
    }

}
