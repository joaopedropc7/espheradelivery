package br.com.esphera.delivery.service;

import br.com.esphera.delivery.controller.MotoboyController;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.MotoboyRecord;
import br.com.esphera.delivery.models.MotoboysModel;
import br.com.esphera.delivery.repository.MotoboyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
        motoboysModel.add(linkTo(methodOn(MotoboyController.class).findMotoboyById(motoboysModel.getId())).withSelfRel());
        System.out.println(motoboysModel);
        return motoboysModel;
    }

    public Page<MotoboysModel> getMotoboysByNameAndCompanyId(String name, Integer companyId, Pageable pageable){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        Page<MotoboysModel> motoboysModel = motoboyRepository.findMotoboysModelByNameMotoboy(name, pageable, companyModel);
        motoboysModel.forEach(motoboysModel1 -> motoboysModel1.add(linkTo(methodOn(MotoboyController.class).findMotoboyById(motoboysModel1.getId())).withSelfRel()));
        return motoboysModel;
    }

    public MotoboysModel findMotoboyById(Integer motoboyId){
        MotoboysModel motoboysModel = motoboyRepository.findById(motoboyId).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado motoboy com o id " + motoboyId));
        motoboysModel.add(linkTo(methodOn(MotoboyController.class).findMotoboyById(motoboyId)).withSelfRel());
        return motoboysModel;
    }

    public Page<MotoboysModel> findAllMotoboysByCompanyId(Integer companyId, Pageable pageable){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        Page<MotoboysModel> motoboysModel = motoboyRepository.findAllByCompanyModel(companyModel, pageable);
        motoboysModel.forEach(motoboysModel1 -> motoboysModel1.add(linkTo(methodOn(MotoboyController.class).findMotoboyById(motoboysModel1.getId())).withSelfRel()));
        return motoboysModel;
    }

    public MotoboysModel putMotoboy(Integer motoboyId, MotoboyRecord dto, Integer companyId){
        MotoboysModel motoboysModel = motoboyRepository.findById(motoboyId).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado motoboy com o id " + motoboyId));
        verifyBelongCompany(motoboysModel, companyId);
        motoboysModel.setNameMotoboy(dto.nameMotoboy());
        motoboysModel.setEmail(dto.email());
        motoboysModel.setNumber(dto.number());
        motoboyRepository.save(motoboysModel);
        motoboysModel.add(linkTo(methodOn(MotoboyController.class).findMotoboyById(motoboyId)).withSelfRel());
        return motoboysModel;
    }

    public void inactiveMotoboy(Integer motoboyId, Integer companyId){
        MotoboysModel motoboysModel = motoboyRepository.findById(motoboyId).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado motoboy com o id " + motoboyId));
        verifyBelongCompany(motoboysModel, companyId);
        motoboysModel.setInactive(true);
        motoboyRepository.save(motoboysModel);
    }

    public void activeMotoboy(Integer motoboyId, Integer companyId){
        MotoboysModel motoboysModel = motoboyRepository.findById(motoboyId).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado motoboy com o id " + motoboyId));
        verifyBelongCompany(motoboysModel, companyId);
        motoboysModel.setInactive(false);
        motoboyRepository.save(motoboysModel);
    }

    public void deleteMotoboy(Integer motoboyId, Integer companyId){
        MotoboysModel motoboysModel = findMotoboyById(motoboyId);
        verifyBelongCompany(motoboysModel, companyId);
        motoboyRepository.delete(motoboysModel);
    }

    public void incrementDeliveryQuantity(MotoboysModel motoboysModel){
        motoboysModel.setQuantityDelivered(motoboysModel.getQuantityDelivered() + 1);
        motoboyRepository.save(motoboysModel);
    }

    public void decrementDeliveryQuantity(MotoboysModel motoboysModel){
        motoboysModel.setQuantityDelivered(motoboysModel.getQuantityDelivered() - 1);
        motoboyRepository.save(motoboysModel);
    }

    public void verifyBelongCompany(MotoboysModel  motoboysModel, Integer companyId){
        if(!motoboysModel.getCompanyModel().getId().equals(companyId)){
            throw new ResourceNotFoundException("Este não pertence a esta empresa!");
        }
    }
}
