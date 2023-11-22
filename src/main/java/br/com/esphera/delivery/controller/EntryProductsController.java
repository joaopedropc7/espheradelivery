package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import br.com.esphera.delivery.models.ProductEntryItemModel;
import br.com.esphera.delivery.models.ProductEntryModel;
import br.com.esphera.delivery.service.ProductEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entry")
public class EntryProductsController {

    @Autowired
    private ProductEntryService entryService;

    @PostMapping("/{companyId}")
    public ResponseEntity<ProductEntryModel> createEntryProduct(@RequestBody ProductEntryRecord data, @PathVariable(value = "companyId") Integer companyId){
        ProductEntryModel entryModel = entryService.createEntryProduct(data, companyId);
        return ResponseEntity.ok(entryModel);
    }

    @GetMapping("/find/{companyId}")
    public ResponseEntity<List<ProductEntryModel>> getAllEntrys(@PathVariable(value = "companyId")Integer companyId){
        List<ProductEntryModel> entryModels = entryService.findByCompanyId(companyId);
        return ResponseEntity.ok(entryModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntryModel> getEntryById(@PathVariable(value = "id") Integer id){
        ProductEntryModel entryModel = entryService.findById(id);
        return ResponseEntity.ok(entryModel);
    }

    @GetMapping("/products/{companyId}/{entryId}")
    public ResponseEntity<List<ProductEntryItemModel>> getProductsInEntry(@PathVariable(value = "companyId")Integer companyId, @PathVariable(value = "entryId") Integer entryId){
        List<ProductEntryItemModel> productsInEntry = entryService.findAllProductsInEntry(companyId, entryId);
        return ResponseEntity.ok(productsInEntry);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity cancelEntryById(@PathVariable Integer id){
        entryService.cancelEntryById(id);
        return ResponseEntity.ok().build();
    }

}
