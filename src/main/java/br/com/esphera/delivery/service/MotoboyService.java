package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.MotoboyRecord;
import br.com.esphera.delivery.models.MotoboysModel;
import br.com.esphera.delivery.repository.MotoboyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoboyService {

    @Autowired
    private MotoboyRepository motoboyRepository;

    @Autowired
    private CompanyService companyService;

    public MotoboysModel createMotoboy(MotoboyRecord dto, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        MotoboysModel motoboysModel = new MotoboysModel(dto, companyModel);
        motoboyRepository.save(motoboysModel);
        return motoboysModel;
    }

    public MotoboysModel findMotoboyById(Integer motoboyId){
        MotoboysModel motoboysModel = motoboyRepository.findById(motoboyId).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado motoboy com o id " + motoboyId));
        return motoboysModel;
    }

    public List<MotoboysModel> findAllMotoboysByCompanyId(Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        List<MotoboysModel> motoboysModel = motoboyRepository.findAllByCompanyModel(companyModel);
        return motoboysModel;
    }

    public MotoboysModel putMotoboy(Integer motoboyId, MotoboyRecord dto){
        MotoboysModel motoboysModel = motoboyRepository.findById(motoboyId).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado motoboy com o id " + motoboyId));
        motoboysModel.setNameMotoboy(dto.nameMotoboy());
        motoboysModel.setEmail(dto.email());
        motoboysModel.setNumber(dto.number());
        motoboyRepository.save(motoboysModel);
        return motoboysModel;
    }

    public void inactiveMotoboy(Integer motoboyId){
        MotoboysModel motoboysModel = motoboyRepository.findById(motoboyId).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado motoboy com o id " + motoboyId));
        motoboysModel.setInactive(true);
        motoboyRepository.save(motoboysModel);
    }

    public void activeMotoboy(Integer motoboyId){
        MotoboysModel motoboysModel = motoboyRepository.findById(motoboyId).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado motoboy com o id " + motoboyId));
        motoboysModel.setInactive(false);
        motoboyRepository.save(motoboysModel);
    }

}
