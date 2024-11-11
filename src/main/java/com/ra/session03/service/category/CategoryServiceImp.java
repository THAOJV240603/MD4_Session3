package com.ra.session03.service.category;

import com.ra.session03.model.dto.category.CategoryRequestDTO;
import com.ra.session03.model.dto.category.CategoryResponseDTO;
import com.ra.session03.model.dto.category.CategoryUpdateRequestDTO;
import com.ra.session03.model.entity.Category;
import com.ra.session03.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDTO> findAll() {
        //convert entity --> DTO
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDTO> responseDTOS = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponseDTO categoryItem = CategoryResponseDTO.builder()
                    .id(category.getId())
                    .categoryName(category.getCategoryName())
                    .categoryStatus(category.getCategoryStatus())
                    .build();
            responseDTOS.add(categoryItem);
        }
        return responseDTOS;
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        Category category = Category.builder()
                .categoryName(categoryRequestDTO.getCategoryName())
                .categoryStatus(categoryRequestDTO.getCategoryStatus())
                .build();
        Category categoryNew = categoryRepository.save(category);

        CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO.builder()
                .id(categoryNew.getId())
                .categoryName(categoryNew.getCategoryName())
                .categoryStatus(categoryNew.getCategoryStatus())
                .build();
        return categoryResponseDTO;
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        //convert entity --> DTO
        if (category != null) {
            CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO.builder()
                    .id(id)
                    .categoryName(category.getCategoryName())
                    .categoryStatus(category.getCategoryStatus())
                    .build();
            return categoryResponseDTO;
        }
        return null;
    }

    @Override
    public CategoryResponseDTO update(CategoryUpdateRequestDTO categoryUpdateRequestDTO) {
        Category category = Category.builder()
                .id(categoryUpdateRequestDTO.getId())
                .categoryName(categoryUpdateRequestDTO.getCategoryName())
                .categoryStatus(categoryUpdateRequestDTO.getCategoryStatus())
                .build();
        Category categoryNew = categoryRepository.save(category);

        CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO.builder()
                .id(categoryNew.getId())
                .categoryName(categoryNew.getCategoryName())
                .categoryStatus(categoryNew.getCategoryStatus())
                .build();
        return categoryResponseDTO;
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    //Phân trang
    @Override
    public Page<CategoryResponseDTO> paginate(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        //Java 8
        Page<CategoryResponseDTO> categoryResponseDTOS = categories.map(category -> {
            CategoryResponseDTO responseDTO = new CategoryResponseDTO();
            responseDTO.setId(category.getId());
            responseDTO.setCategoryName(category.getCategoryName());
            responseDTO.setCategoryStatus(category.getCategoryStatus());
            return responseDTO;
        });
        return categoryResponseDTOS;
    }
}
