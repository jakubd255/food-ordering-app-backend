package pl.jakubdudek.foodorderingappbackend.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import pl.jakubdudek.foodorderingappbackend.model.entity.Category;
import pl.jakubdudek.foodorderingappbackend.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MenuConfig {
    private final CategoryRepository categoryRepository;

    private void initCategories() {
        if(categoryRepository.count() == 0) {
            String[] names = {"Pizza", "Burgers", "Kebab", "Extras", "Sauces and oils", "Soft drinks"};

            List<Category> categories = new ArrayList<>(Arrays.asList(names))
                    .stream()
                    .map(c -> {
                        Category category = new Category();
                        category.setName(c);
                        return category;
                    })
                    .toList();

            categoryRepository.saveAll(categories);
        }
    }

    @PostConstruct
    public void initialize() {
        initCategories();
    }
}
