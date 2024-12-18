package pl.jakubdudek.foodorderingappbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.CategoryDto;
import pl.jakubdudek.foodorderingappbackend.repository.CategoryRepository;
import pl.jakubdudek.foodorderingappbackend.util.DtoMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final DtoMapper dtoMapper;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(dtoMapper::mapCategoryToDto).toList();
    }
}
