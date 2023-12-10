package br.com.esphera.delivery.service;

import br.com.esphera.delivery.controller.EntryProductsController;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import br.com.esphera.delivery.models.ProductEntryItemModel;
import br.com.esphera.delivery.models.ProductEntryModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.repository.ProductEntryItemModelRepository;
import br.com.esphera.delivery.repository.ProductEntryRepository;
import br.com.esphera.delivery.validations.entry.ValidationEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductEntryService {

    @Autowired
    private ProductEntryRepository entryRepository;

    @Autowired
    private ProductEntryItemModelRepository productEntryItemModelRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private List<ValidationEntry> validationEntry;

    @Autowired
    private ProductEntryItemModelRepository entryItemModelRepository;

    @Autowired
    private CompanyService companyService;

    public ProductEntryModel createEntryProduct(ProductEntryRecord data, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        validationEntry.forEach(validacao -> validacao.valid(data));
        List<ProductModel> productsModel = new ArrayList<>();
        List<ProductEntryItemModel> productsEntryModel = new ArrayList<>();

        data.products().forEach(product -> {
            ProductModel productModel = productService.findById(product.idProduct());
            ProductEntryItemModel productEntryItemModel = new ProductEntryItemModel(productModel, product.quantity(), product.priceBuy(), companyModel);
            productsEntryModel.add(productEntryItemModel);
            productsModel.add(productModel);
        });
        ProductEntryModel entryModel = new ProductEntryModel(data, productsEntryModel, companyModel);
        entryRepository.save(entryModel);
        productService.addQuantityInProduct(productsEntryModel);
        productsEntryModel.forEach(productEntryItemModel -> {
            productEntryItemModel.setEntryModel(entryModel);
            productEntryItemModelRepository.save(productEntryItemModel);
            });
        entryModel.add(linkTo(methodOn(EntryProductsController.class).getEntryById(entryModel.getId())).withSelfRel());
        return entryModel;
    }

    public Page<ProductEntryModel> findByCompanyId(Integer companyId, Pageable pageable){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        Page<ProductEntryModel> entrys = entryRepository.findProductEntryModelsByCompanyModel(companyModel, pageable);
        entrys.forEach(entry -> {
            entry.add(linkTo(methodOn(EntryProductsController.class).getEntryById(entry.getId())).withSelfRel());
        });
        return entrys;
    }

    public ProductEntryModel findById(Integer id){
        ProductEntryModel productEntryModel = entryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado entradas com o id: " + id));
        productEntryModel.add(linkTo(methodOn(EntryProductsController.class).getEntryById(productEntryModel.getId())).withSelfRel());
        return productEntryModel;
    }

    public void cancelEntryById(Integer id, Integer companyId){
        ProductEntryModel productEntryModel = entryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado entradas com o id: " + id));
        verifyBelongCompany(companyId, productEntryModel);
        productEntryModel.setEntryCanceled(true);
        productService.cancelEntryById(productEntryModel.getProducts());
        entryRepository.deleteById(id);
    }

    public List<ProductEntryItemModel> findAllProductsInEntry(Integer companyId, Integer entryId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        ProductEntryModel productEntryModel = findById(entryId);
        List<ProductEntryItemModel> productsEntry = productEntryItemModelRepository.findProductEntryItemModelsByCompanyModelAndEntryModel(companyModel, productEntryModel);
        productsEntry.forEach(productEntryItemModel -> {
            productEntryItemModel.add(linkTo(methodOn(EntryProductsController.class).getEntryById(productEntryModel.getId())).withSelfRel());
        });
        return productsEntry;
    }


    public void verifyBelongCompany(Integer companyId, ProductEntryModel productEntryModel){
        if(!productEntryModel.getCompanyModel().getId().equals(companyId)){
            throw new ResourceNotFoundException("Esta entrada não pertence a esta empresa");
        }
    }

}
