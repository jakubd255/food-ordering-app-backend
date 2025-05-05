package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.ProductRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.ProductDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Product;
import pl.jakubdudek.foodorderingappbackend.repository.ProductRepository;
import pl.jakubdudek.foodorderingappbackend.util.FileManager;
import pl.jakubdudek.foodorderingappbackend.util.mapper.ProductMapper;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FileManager fileManager;

    @CacheEvict(value = "products", allEntries = true)
    public ProductDto addProduct(ProductRequest request) {
        Product product = ProductMapper.mapRequestToProduct(request);
        return ProductMapper.mapProductToDto(productRepository.save(product));
    }

    @CacheEvict(value = "products", allEntries = true)
    public List<ProductDto> addProducts(List<ProductRequest> request) {
        List<Product> products = productRepository.saveAll(ProductMapper.mapRequestsToProducts(request));
        return ProductMapper.mapProductsToDto(products);
    }

    @Cacheable(value = "products")
    public List<ProductDto> getAllProducts() {
        Sort sort = Sort.by(Sort.Order.asc("category.id"), Sort.Order.asc("name"));
        return ProductMapper.mapProductsToDto(productRepository.findAll(sort));
    }

    @Cacheable(value = "products", key = "'category_'+#categoryId")
    public List<ProductDto> getProductsByCategoryId(Integer categoryId) {
        return ProductMapper.mapProductsToDto(productRepository.findByCategoryId(categoryId));
    }

    @Cacheable(value = "products", key = "#id")
    public ProductDto getProductById(Integer id) {
        Product product = findProductById(id);
        return ProductMapper.mapProductToDto(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductDto updateProductById(Integer id, ProductRequest request) {
        Product product = findProductById(id);
        ProductMapper.updateProductFields(product, request);
        return ProductMapper.mapProductToDto(productRepository.save(product));
    }

    @CacheEvict(value = "products", allEntries = true)
    public String updateProductImageById(Integer id, MultipartFile image, Boolean deleteImage) throws IOException {
        Product product = findProductById(id);

        String path = null;

        //Delete main photo
        if(Boolean.TRUE.equals(deleteImage) && product.getImage() != null && !product.getImage().isEmpty()) {
            fileManager.deleteFile(product.getImage());
            product.setImage(null);
        }
        //Add or update image
        else if(image != null && !image.isEmpty()) {
            path = fileManager.uploadFile(image);
            //Delete old photo
            if(product.getImage() != null && !product.getImage().isEmpty()) {
                fileManager.deleteFile(product.getImage());
            }
            product.setImage(path);
        }

        productRepository.save(product);
        return path;
    }

    @CacheEvict(value = "products", allEntries = true)
    public void deleteProductById(Integer id) {
        productRepository.deleteById(id);
    }

    private Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }
}
