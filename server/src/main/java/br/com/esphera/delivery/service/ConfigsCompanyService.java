package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ConfigsCompanyModel;
import br.com.esphera.delivery.models.DTOS.ConfigsRecord;
import br.com.esphera.delivery.repository.ConfigsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ConfigsCompanyService {

    @Autowired
    private ConfigsRepository configsRepository;

    @Autowired
    private CompanyService companyService;

    public void createConfigForCompany(CompanyModel companyModel){
        ConfigsCompanyModel configsCompanyModel = new ConfigsCompanyModel(companyModel);
        configsRepository.save(configsCompanyModel);
    }

    public ConfigsCompanyModel getConfigByCompany(Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        return configsRepository.findConfigsCompanyModelByCompanyModel(companyModel);
    }

    public ConfigsCompanyModel getConfigById(Integer idConfig){
        return configsRepository.findById(idConfig).orElseThrow(() -> new ResourceNotFoundException("Nenhuma configuração encontrada para o ID" + idConfig));
    }

    public ConfigsCompanyModel updateConfigByCompanyId(Integer companyId, ConfigsRecord configsRecord){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        ConfigsCompanyModel configs = configsRepository.findConfigsCompanyModelByCompanyModel(companyModel);
        configs.setHourOpenEstablishment(LocalTime.parse(configsRecord.hourOpenEstablishment()));
        configs.setHourCloseEstablishment(LocalTime.parse(configsRecord.hourCloseEstablishment()));
        configs.setMinimumOrderValue(configsRecord.minimumOrder());
        configs.setMaximumDistanceDelivery(configsRecord.maximumDistanceDelivery());
        configs.setValueKmDelivery(configsRecord.valueKmDelivery());
        configs.setMaximumDiscountPercent(configsRecord.maximumDiscountPercent());
        configsRepository.save(configs);
        return configs;
    }

    public Double getValueDelivery(Integer companyId){
        ConfigsCompanyModel configsCompanyModel = getConfigByCompany(companyId);
        return configsCompanyModel.getValueKmDelivery();
    }


}
