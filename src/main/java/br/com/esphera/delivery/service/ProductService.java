package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.ProductRecord;
import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.ProductEntryItemModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.repository.CategoryRepository;
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
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        CategoryModel categoryModel = categoryService.findById(dto.idCategory());
        ProductModel product = new ProductModel(dto, categoryModel, companyModel);
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

    public ProductModel updateProduct(Integer id, ProductRecord dto){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        CategoryModel category = categoryService.findById(dto.idCategory());

        product.setName(dto.name());
        product.setCategoryModel(category);
        product.setDescription(dto.description());
        product.setImage(dto.image());
        product.setCostValue(dto.costValue());
        product.setValueSell(dto.valueSell());
        productRepository.save(product);
        return product;
    }

    public void inactiveProduct(Integer id){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        product.setInactive(true);
        productRepository.save(product);
    }

    public void activeProduct(Integer id){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        product.setInactive(false);
        productRepository.save(product);
    }

    public void sellProduct(Integer productId, Integer productQuantity){
        ProductModel product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        product.setQuantity(product.getQuantity() - productQuantity);
        product.setSales(product.getSales() + productQuantity);
        product.setValueSellTotal(product.getValueSellTotal() + (product.getValueSell() * productQuantity));
        productRepository.save(product);
    }

    public void revertSellProduct(Integer productId, Integer productQuantity){
        ProductModel product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        product.setQuantity(product.getQuantity() + productQuantity);
        product.setSales(product.getSales() - productQuantity);
        product.setValueSellTotal(product.getValueSellTotal() - (product.getValueSell() * productQuantity));
        productRepository.save(product);
    }

    public void addQuantityInProduct(List<ProductEntryItemModel> productsList){
        productsList.forEach(product -> {
            ProductModel productModel = productRepository.findById(product.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
            productModel.setQuantity(productModel.getQuantity() + product.getQuantity());
            productModel.setValueBuyTotal(productModel.getValueBuyTotal() + (product.getPriceBuy() * product.getQuantity()));
            productRepository.save(productModel);
        });
    }

    public void cancelEntryById(List<ProductEntryItemModel> products) {
        products.forEach(product -> {
            ProductModel productModel = productRepository.findById(product.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
            productModel.setQuantity(productModel.getQuantity() - product.getQuantity());
            productModel.setValueBuyTotal(productModel.getValueBuyTotal() - (product.getPriceBuy() * product.getQuantity()));
            productRepository.save(productModel);
        });
    }
}
