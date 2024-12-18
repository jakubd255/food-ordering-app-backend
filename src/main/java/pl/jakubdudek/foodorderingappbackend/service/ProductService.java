package pl.jakubdudek.foodorderingappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jakubdudek.foodorderingappbackend.model.dto.request.ProductRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.ProductDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Category;
import pl.jakubdudek.foodorderingappbackend.model.entity.Product;
import pl.jakubdudek.foodorderingappbackend.model.entity.Variant;
import pl.jakubdudek.foodorderingappbackend.repository.ProductRepository;
import pl.jakubdudek.foodorderingappbackend.util.DtoMapper;
import pl.jakubdudek.foodorderingappbackend.util.FileManager;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final DtoMapper dtoMapper;
    private final FileManager fileManager;

    public ProductDto addProduct(ProductRequest request) {
        Product product = mapRequestToProduct(request);
        return dtoMapper.mapProductToDto(productRepository.save(product));
    }

    public List<ProductDto> getAllProducts(Integer categoryId) {
        List<Product> products;
        if(categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        }
        else {
            products = productRepository.findAll();
        }
        return products.stream().map(dtoMapper::mapProductToDto).toList();
    }

    public ProductDto getProductById(Integer id) {
        Product product = findProductById(id);
        return dtoMapper.mapProductToDto(product);
    }

    public ProductDto updateProductById(Integer id, ProductRequest request) {
        Product product = findProductById(id);
        updateProductFields(product, request);
        return dtoMapper.mapProductToDto(productRepository.save(product));
    }

    public String updateProductImageById(Integer id, MultipartFile image) {
        String path = fileManager.uploadFile(image);

        Product product = findProductById(id);
        product.setImage(path);
        productRepository.save(product);

        return path;
    }

    public void deleteProductById(Integer id) {
        productRepository.deleteById(id);
    }

    private Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    private Product mapRequestToProduct(ProductRequest request) {
        Category category = Category.builder()
                .id(request.getCategoryId())
                .build();

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .build();

        if(request.getVariants() != null) {
            List<Variant> variants = request.getVariants().stream().map(r -> {
                return Variant.builder()
                        .name(r.getName())
                        .price(r.getPrice())
                        .product(product)
                        .build();
            }).toList();
            product.setVariants(variants);
        }

        return product;
    }

    private void updateProductFields(Product product, ProductRequest request) {
        if(request.getName() != null) {
            product.setName(request.getName());
        }
        if(request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if(request.getCategoryId() != null) {
            Category category = Category.builder()
                    .id(request.getCategoryId())
                    .build();
            product.setCategory(category);
        }
        if(request.getVariants() != null && !request.getVariants().isEmpty()) {
            List<Variant> variants = request.getVariants().stream().map(r -> {
                return Variant.builder()
                        .name(r.getName())
                        .price(r.getPrice())
                        .product(product)
                        .build();
            }).toList();

            product.getVariants().clear();
            product.getVariants().addAll(variants);
        }
    }
}
