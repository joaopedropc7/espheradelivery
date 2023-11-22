package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.CompanyRecord;
import br.com.esphera.delivery.models.DTOS.CompanyUpdateRecord;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyModel> createCompany(@RequestBody CompanyRecord companyRecord){
        CompanyModel companyModel = companyService.createCompany(companyRecord);
        return ResponseEntity.ok(companyModel);
    }

    @GetMapping
    public ResponseEntity<List<CompanyModel>> getAllCompanies(){
        List<CompanyModel> companyModelList = companyService.getAllCompanies();
        return ResponseEntity.ok(companyModelList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyModel> getCompanyById(@PathVariable(value = "id") Integer id){
        CompanyModel companyModel = companyService.getCompanyById(id);
        return ResponseEntity.ok(companyModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyModel> putCompany(@PathVariable(value = "id") Integer id, CompanyUpdateRecord companyUpdateRecord){
        CompanyModel companyModel = companyService.putCompany(id, companyUpdateRecord);
        return ResponseEntity.ok(companyModel);
    }

    @PutMapping("/adress/{id}")
    public ResponseEntity<CompanyModel> putCompanyAddress(@PathVariable(value = "id") Integer id, AddressRecord addressRecord){
        CompanyModel companyModel = companyService.putCompanyAddress(id, addressRecord);
        return ResponseEntity.ok(companyModel);
    }

    @PutMapping("/inactive/{id}")
    public ResponseEntity inactiveCompany(@PathVariable(value = "id") Integer id){
        companyService.inactiveCompany(id);
        return ResponseEntity.ok().build();
    }



}
