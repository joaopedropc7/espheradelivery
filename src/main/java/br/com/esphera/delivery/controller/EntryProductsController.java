package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import br.com.esphera.delivery.models.ProductEntryModel;
import br.com.esphera.delivery.service.ProductEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entryproducts")
public class EntryProductsController {

    @Autowired
    private ProductEntryService entryService;

    @PostMapping
    public ResponseEntity<ProductEntryModel> createEntryProduct(@RequestBody ProductEntryRecord data){
        ProductEntryModel entryModel = entryService.createEntryProduct(data);
        return ResponseEntity.ok(entryModel);
    }

    @GetMapping
    public ResponseEntity<List<ProductEntryModel>> getAllEntrys(){
        List<ProductEntryModel> entryModels = entryService.findAll();
        return ResponseEntity.ok(entryModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntryModel> getEntryById(@PathVariable Integer id){
        ProductEntryModel entryModel = entryService.findById(id);
        return ResponseEntity.ok(entryModel);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity cancelEntryById(@PathVariable Integer id){
        entryService.cancelEntryById(id);
        return ResponseEntity.ok().build();
    }

}
