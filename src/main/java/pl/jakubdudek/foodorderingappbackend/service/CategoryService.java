package pl.jakubdudek.foodorderingappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.CategoryDto;
import pl.jakubdudek.foodorderingappbackend.model.entity.Category;
import pl.jakubdudek.foodorderingappbackend.repository.CategoryRepository;
import pl.jakubdudek.foodorderingappbackend.util.mapper.CategoryMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Cacheable(value = "categories")
    public List<CategoryDto> getAllCategories() {
        return CategoryMapper.mapCategoriesToDto(categoryRepository.findAll());
    }

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryDto addCategory(String name) {
        Category category = CategoryMapper.mapRequestToCategory(name);
        return  CategoryMapper.mapCategoryToDto(categoryRepository.save(category));
    }

    @CacheEvict(value = "categories", allEntries = true)
    public List<CategoryDto> addCategories(List<String> names) {
        List<Category> categories = names.stream().map(CategoryMapper::mapRequestToCategory).toList();
        return CategoryMapper.mapCategoriesToDto(categoryRepository.saveAll(categories));
    }
}
