package br.com.esphera.delivery.service;

import br.com.esphera.delivery.ApiMaps.MapsAPi;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response.ResponseAddressValidationMapsAPI;
import br.com.esphera.delivery.controller.CompanyController;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.CompanyRecord;
import br.com.esphera.delivery.models.DTOS.CompanyUpdateRecord;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.AddressModel;
import br.com.esphera.delivery.models.FileEntity;
import br.com.esphera.delivery.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class  CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MapsAPi mapsAPi;

    public CompanyModel createCompany(CompanyRecord companyDTO){
        ResponseAddressValidationMapsAPI responseAddressValidation = mapsAPi.validateAddress(companyDTO.addressRecord());
        AddressModel addressModel = addressService.createAddress(companyDTO.addressRecord());
        CompanyModel companyModel = new CompanyModel(companyDTO, addressModel, responseAddressValidation.result().geocode().placeId());
        companyRepository.save(companyModel);
        companyModel.add(linkTo(methodOn(CompanyController.class).getCompanyById(companyModel.getId())).withSelfRel());
        return companyModel;
    }

    public Page<CompanyModel> findCompaniesByNomeFantasia(String nomeFantasia, Pageable pageable){
        Page<CompanyModel> companyModels = companyRepository.findCompanyModelByNomeFantasia(nomeFantasia, pageable);
        companyModels.forEach(companyModel -> {
            companyModel.add(linkTo(methodOn(CompanyController.class).getCompanyById(companyModel.getId())).withSelfRel());
        });
        return companyModels;
    }

    public Page<CompanyModel> getAllCompanies(Pageable pageable){
        Page<CompanyModel> companyModels = companyRepository.findAll(pageable);
        companyModels.forEach(companyModel -> {
            companyModel.add(linkTo(methodOn(CompanyController.class).getCompanyById(companyModel.getId())).withSelfRel());
        });
        return companyModels;
    }

    public CompanyModel getCompanyById(Integer id){
        CompanyModel companyModel = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhuma empresa encontrada com este ID!"));
        companyModel.add(linkTo(methodOn(CompanyController.class).getCompanyById(companyModel.getId())).withSelfRel());
        return companyModel;
    }

    public CompanyModel putCompany(Integer companyId, CompanyUpdateRecord companyRecord){
        CompanyModel companyModel = getCompanyById(companyId);
        companyModel.setNomeFantasia(companyRecord.nomeFantasia());
        companyModel.setRazaoSocial(companyRecord.razaoSocial());
        companyModel.setNumberCompany1(companyRecord.numberCompany1());
        companyModel.setNumberCompany2(companyRecord.numberCompany2());
        companyModel.setEmailCompany(companyRecord.emailCompany());
        companyRepository.save(companyModel);
        companyModel.add(linkTo(methodOn(CompanyController.class).getCompanyById(companyModel.getId())).withSelfRel());
        return companyModel;
    }

    public CompanyModel putCompanyAddress(Integer companyId, AddressRecord addressRecord){
        CompanyModel companyModel = getCompanyById(companyId);
        AddressModel addressModel = addressService.putAddress(companyModel, addressRecord);
        companyModel.setEnderecoModel(addressModel);
        String placeId = addressService.getPlaceIdApiMaps(addressRecord);
        companyModel.setIdLocalCompanyMaps(placeId);
        companyRepository.save(companyModel);
        companyModel.add(linkTo(methodOn(CompanyController.class).getCompanyById(companyModel.getId())).withSelfRel());
        return companyModel;
    }

    public void inactiveCompany(Integer companyId){
        CompanyModel companyModel = getCompanyById(companyId);
        companyModel.setInactive(true);
        companyRepository.save(companyModel);
    }

    public void incrementValueGenerated(CompanyModel companyModel, Double value){
        companyModel.setValueGenerated(companyModel.getValueGenerated() + value);
        companyRepository.save(companyModel);
    }

    public void reverseValueGenerated(CompanyModel companyModel, Double value){
        companyModel.setValueGenerated(companyModel.getValueGenerated() - value);
        companyRepository.save(companyModel);
    }

    public void setBannerImageCompany(FileEntity fileEntity, Integer companyId){
        CompanyModel companyModel = getCompanyById(companyId);
        companyModel.setBannerImage(fileEntity);
        companyRepository.save(companyModel);
    }

    public void setLogoImageCompany(FileEntity fileEntity, Integer companyId){
        CompanyModel companyModel = getCompanyById(companyId);
        companyModel.setLogoImage(fileEntity);
        companyRepository.save(companyModel);
    }


    public FileEntity getBannerImageCompany(Integer companyId){
        CompanyModel companyModel = getCompanyById(companyId);
        FileEntity fileEntity = companyModel.getBannerImage();
        if (fileEntity == null) throw new ResourceNotFoundException("Nenhuma imagem encontrada para esta empresa!");

        return fileEntity;
    }

    public FileEntity getLogoImageCompany(Integer companyId){
        System.out.println("CAIU AQ");
        CompanyModel companyModel = getCompanyById(companyId);
        FileEntity fileEntity = companyModel.getLogoImage();
        if (fileEntity == null) throw new ResourceNotFoundException("Nenhuma imagem encontrada para esta empresa!");
        return fileEntity;
    }

}
