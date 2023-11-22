package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.CompanyRecord;
import br.com.esphera.delivery.models.DTOS.CompanyUpdateRecord;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.AddressModel;
import br.com.esphera.delivery.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    public CompanyModel createCompany(CompanyRecord companyDTO){
        AddressModel addressModel = addressService.createAddress(companyDTO.addressRecord());
        CompanyModel companyModel = new CompanyModel(companyDTO, addressModel);
        companyRepository.save(companyModel);
        return companyModel;
    }

    public List<CompanyModel> getAllCompanies(){
        return companyRepository.findAll();
    }

    public CompanyModel getCompanyById(Integer id){
        return companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhuma empresa encontrada com este ID!"));
    }

    public CompanyModel putCompany(Integer id, CompanyUpdateRecord companyRecord){
        CompanyModel companyModel = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhuma empresa encontrada com este ID!"));
        companyModel.setNomeFantasia(companyRecord.nomeFantasia());
        companyModel.setRazaoSocial(companyRecord.razaoSocial());
        companyModel.setNumberCompany1(companyRecord.numberCompany1());
        companyModel.setNumberCompany2(companyRecord.numberCompany2());
        companyModel.setEmailCompany(companyRecord.emailCompany());
        companyRepository.save(companyModel);
        return companyModel;
    }

    public CompanyModel putCompanyAddress(Integer id, AddressRecord addressRecord){
        CompanyModel companyModel = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhuma empresa encontrada com este ID!"));
        AddressModel addressModel = new AddressModel(addressRecord);
        companyModel.setEnderecoModel(addressModel);
        companyRepository.save(companyModel);
        return companyModel;
    }

    public void inactiveCompany(Integer id){
        CompanyModel companyModel = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhuma empresa encontrada com este ID!"));
        companyModel.setInactive(true);
        companyRepository.save(companyModel);
    }

    public void incrementValueGenerated(Integer companyId, Double value){
        CompanyModel companyModel = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Nenhuma empresa encontrada com este ID!"));
        companyModel.setValueGenerated(companyModel.getValueGenerated() + value);
        companyRepository.save(companyModel);
    }
}
