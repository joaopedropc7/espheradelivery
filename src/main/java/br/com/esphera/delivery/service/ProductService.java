package br.com.esphera.delivery.service;

import br.com.esphera.delivery.controller.ProductController;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.*;
import br.com.esphera.delivery.models.DTOS.ProductRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.ProductResponseDTO;
import br.com.esphera.delivery.repository.CategoryRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import br.com.esphera.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CompanyService companyService;


    public ProductModel createProduct(ProductRecord dto, Integer companyId){
        Integer lastIdLocalInsert = productRepository.findMaxIdLocalByCompany(companyId);
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        CategoryModel categoryModel = categoryService.findById(dto.idCategory());
        ProductModel product = new ProductModel(dto, categoryModel, companyModel, lastIdLocalInsert);
        productRepository.save(product);
        return product;
    }

    public List<ProductModel> findProductsByCategory(Integer companyId, Integer categoryId){
        CategoryModel category = categoryService.findById(categoryId);
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        System.out.println(category.getCategoryName());
        System.out.println(companyModel.getNameContact());
        List<ProductModel> products = productRepository.findProductModelsByCompanyModelAndCategoryModel(companyModel, category);
        return products;
    }

    public List<ProductModel> findAllProducts(Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        List<ProductModel> products = productRepository.finddProductByComapanyId(companyModel);
        return products;
    }

    public ProductModel findById(Integer id){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        return product;
    }

    public ProductModel updateProduct(Integer productId, ProductRecord dto, Integer companyId){
        ProductModel product = findById(productId);
        verifyProductBelongsCompany(product, companyId);
        CategoryModel category = categoryService.findById(dto.idCategory());

        product.setName(dto.name());
        product.setCategoryModel(category);
        product.setDescription(dto.description());
        product.setCostValue(dto.costValue());
        product.setValueSell(dto.valueSell());
        productRepository.save(product);
        return product;
    }

    public void inactiveProduct(Integer productId, Integer companyId){
        ProductModel product = findById(productId);
        verifyProductBelongsCompany(product, companyId);
        if(product.getQuantity() > 0){
            throw new ResourceNotFoundException("Não é possível inativar um produto com estoque!");
        }
        product.setInactive(true);
        productRepository.save(product);
    }

    public void activeProduct(Integer productId, Integer companyId){
        ProductModel product = findById(productId);
        verifyProductBelongsCompany(product, companyId);
        product.setInactive(false);
        productRepository.save(product);
    }

    public void deleteProduct(Integer productId, Integer companyId){
        ProductModel productModel = findById(productId);
        verifyProductBelongsCompany(productModel, companyId);
        if(productModel.getQuantity() > 0 && productModel.getSales() > 0 && productModel.getValueBuyTotal() > 0 && productModel.getValueSellTotal() > 0){
            throw new ResourceNotFoundException("Não é possível deletar um produto que haja movimentacoes, tente inativar o mesmo!");
        }
        productRepository.delete(productModel);
    }

    public void sellProduct(ProductModel product, Integer productQuantity){
        product.setQuantity(product.getQuantity() - productQuantity);
        product.setSales(product.getSales() + productQuantity);
        product.setValueSellTotal(product.getValueSellTotal() + (product.getValueSell() * productQuantity));
        productRepository.save(product);
    }

    public void revertSellProduct(ProductModel product, Integer productQuantity){
        product.setQuantity(product.getQuantity() + productQuantity);
        product.setSales(product.getSales() - productQuantity);
        product.setValueSellTotal(product.getValueSellTotal() - (product.getValueSell() * productQuantity));
        productRepository.save(product);
    }

    public void addQuantityInProduct(List<ProductEntryItemModel> productsList){
        productsList.forEach(product -> {
            ProductModel productModel = product.getProduct();
            productModel.setQuantity(productModel.getQuantity() + product.getQuantity());
            productModel.setValueBuyTotal(productModel.getValueBuyTotal() + (product.getPriceBuy() * product.getQuantity()));
            productRepository.save(productModel);
        });
    }

    public void cancelEntryById(List<ProductEntryItemModel> products) {
        products.forEach(product -> {
            ProductModel productModel = product.getProduct();
            productModel.setQuantity(productModel.getQuantity() - product.getQuantity());
            productModel.setValueBuyTotal(productModel.getValueBuyTotal() - (product.getPriceBuy() * product.getQuantity()));
            productRepository.save(productModel);
        });
    }

    public void setImageProduct(Integer productId, FileEntity fileEntity, Integer companyId){
        ProductModel product = findById(productId);
        verifyProductBelongsCompany(product, companyId);
        product.setImage(fileEntity);
        productRepository.save(product);
    }

    public FileEntity getImageProduct(Integer productId, Integer companyId){
        ProductModel product = findById(productId);
        verifyProductBelongsCompany(product, companyId);
        return product.getImage();
    }

    public ProductModel findProductByIdLocalByCompanyAndCompanyModel(Integer idLocalByCompany, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        return productRepository.findProductModelByIdLocalByCompanyAndCompanyModel(idLocalByCompany, companyModel);
    }

    public void verifyProductBelongsCompany(ProductModel productModel, Integer companyId){
        if(!productModel.getCompanyModel().getId().equals(companyId)){
            throw new ResourceNotFoundException("Produto não pertence a esta empresa!");
        }
    }
}
