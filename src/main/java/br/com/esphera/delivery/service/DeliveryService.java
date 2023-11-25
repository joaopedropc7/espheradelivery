package br.com.esphera.delivery.service;

import br.com.esphera.delivery.ApiMaps.MapsAPi;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.*;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.DTOS.DeliveryConsultValueDTO;
import br.com.esphera.delivery.models.DTOS.DeliveryRecord;
import br.com.esphera.delivery.models.DTOS.DistanceDurationDTO;
import br.com.esphera.delivery.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeliveryService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private ConfigsCompanyService configsCompanyService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MotoboyService motoboyService;


    public DeliveryModel createDelivery(CompanyModel companyModel, DeliveryRecord deliveryRecord){

        AddressModel addressModel = addressService.createAddress(deliveryRecord.addressRecord());
        String localId = addressService.getPlaceIdApiMaps(deliveryRecord.addressRecord());
        DistanceDurationDTO distanceDurationDTO = addressService.getDistanceDelivery(companyModel ,localId);
        ConfigsCompanyModel configsCompany = configsCompanyService.getConfigByCompany(companyModel.getId());
        Double valueDelivery = configsCompany.getValueKmDelivery() * distanceDurationDTO.distance();
        DeliveryModel deliveryModel = new DeliveryModel(deliveryRecord, addressModel, distanceDurationDTO, valueDelivery);
        deliveryRepository.save(deliveryModel);
        return deliveryModel;
    }

    public void setDeliveryInRoute(DeliveryModel deliveryModel, Integer motoboyId){
        MotoboysModel motoboysModel = motoboyService.findMotoboyById(motoboyId);
        deliveryModel.setDateDeliveryStart(LocalDateTime.now());
        deliveryModel.setMotoboysModel(motoboysModel);
        deliveryRepository.save(deliveryModel);
    }

    public void setDeliveryFinished(DeliveryModel deliveryModel){
        deliveryModel.setDateDeliveryFinished(LocalDateTime.now());
    }

    public DeliveryModel findDeliveyById(Integer deliveryId){
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new ResourceNotFoundException("Nenhuma entrega encontrada com este ID!"));
    }

    public DeliveryConsultValueDTO consultValueDelivery(AddressRecord addressRecord, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        ConfigsCompanyModel configsCompanyModel = configsCompanyService.getConfigByCompany(companyModel.getId());
        String placeId = addressService.getPlaceIdApiMaps(addressRecord);
        DistanceDurationDTO responseDTO = addressService.getDistanceDelivery(companyModel ,placeId);
        Double value = responseDTO.distance() * configsCompanyModel.getValueKmDelivery();
        DeliveryConsultValueDTO deliveryConsultValueDTO = new DeliveryConsultValueDTO(responseDTO.distance(), responseDTO.duration(), value);
        return deliveryConsultValueDTO;
    }

    public void cancelDelivery(DeliveryModel deliveryModel){
        deliveryModel.setCancelled(true);
        deliveryRepository.save(deliveryModel);
    }

}
