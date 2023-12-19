package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.OptionalDTO;
import br.com.esphera.delivery.models.DTOS.responseDtos.OptionalResponseDTO;
import br.com.esphera.delivery.models.OptionalModel;
import br.com.esphera.delivery.repository.OptionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class OptionalService {

    @Autowired
    private OptionalRepository optionalRepository;
    @Autowired
    private CompanyService companyService;

    public OptionalResponseDTO createOptional(Integer companyId, OptionalDTO optionalDTO){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        OptionalModel optionalModel = new OptionalModel(optionalDTO, companyModel);
        optionalRepository.save(optionalModel);
        OptionalResponseDTO optionalResponseDTO = new OptionalResponseDTO(optionalModel);
        return optionalResponseDTO;
    }

    public Page<OptionalModel> getAllOptionalsByCompanyId(Integer companyId, Pageable pageable){
        Page<OptionalModel> optionals = optionalRepository.findAllByCompanyModel(companyService.getCompanyById(companyId), pageable);
        return optionals;
    }

    public OptionalModel getOptionalById(Integer optionalId){
        OptionalModel optionalModel = optionalRepository.findById(optionalId).orElseThrow(() -> new ResourceNotFoundException("Opcional não encontrado"));
        return optionalModel;
    }

    public OptionalResponseDTO updateOptional(Integer optionalId, OptionalDTO optionalDTO){
        OptionalModel optionalModel = optionalRepository.findById(optionalId).orElseThrow(() -> new ResourceNotFoundException("Opcional não encontrado"));
        optionalModel.setName(optionalDTO.name());
        optionalModel.setDescription(optionalDTO.description());
        optionalModel.setPrice(optionalDTO.price());
        optionalRepository.save(optionalModel);
        OptionalResponseDTO optionalResponseDTO = new OptionalResponseDTO(optionalModel);
        return optionalResponseDTO;
    }


}
