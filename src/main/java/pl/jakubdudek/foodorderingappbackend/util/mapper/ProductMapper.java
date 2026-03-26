package pl.jakubdudek.foodorderingappbackend.util.mapper;

import pl.jakubdudek.foodorderingappbackend.model.dto.request.ProductRequest;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.ProductDto;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.VariantDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Category;
import pl.jakubdudek.foodorderingappbackend.model.entity.Product;
import pl.jakubdudek.foodorderingappbackend.model.entity.Variant;

import java.util.List;

import static pl.jakubdudek.foodorderingappbackend.util.mapper.CategoryMapper.mapCategoryToDto;

public class ProductMapper {
    public static ProductDto mapProductToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                mapCategoryToDto(product.getCategory()),
                product.getVariants().stream().map(ProductMapper::mapVariantToDto).toList()
        );
    }

    public static List<ProductDto> mapProductsToDto(List<Product> products) {
        return products.stream().map(ProductMapper::mapProductToDto).toList();
    }

    private static VariantDto mapVariantToDto(Variant variant) {
        return new VariantDto(variant.getId(), variant.getName(), variant.getPrice());
    }

    public static Product mapRequestToProduct(ProductRequest request) {
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

    public static List<Product> mapRequestsToProducts(List<ProductRequest> requests) {
        return requests.stream().map(ProductMapper::mapRequestToProduct).toList();
    }
}
