package com.ra.session03.controller;

import com.ra.session03.model.dto.category.CategoryRequestDTO;
import com.ra.session03.model.dto.category.CategoryResponseDTO;
import com.ra.session03.model.dto.category.CategoryUpdateRequestDTO;
import com.ra.session03.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping
//    public ResponseEntity<?> index() {
//        List<CategoryResponseDTO> responseDTOS = categoryService.findAll();
//        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
//    }

    //Danh sách có phân trang
    @GetMapping
    public ResponseEntity<?> index(@RequestParam(defaultValue = "0", name = "page") int page,
                                   @RequestParam(defaultValue = "3", name = "limit") int limit,
                                   @RequestParam(defaultValue = "ASC", name = "order") String sort,
                                   @RequestParam(defaultValue = "id", name = "sortBy") String sortBy
                                ) {
        Pageable pageable;
        if(sort.equalsIgnoreCase("ASC")) {
            pageable = PageRequest.of(page, limit, Sort.by(sortBy).ascending());
        }else{
            pageable = PageRequest.of(page, limit, Sort.by(sortBy).descending());
        }
        Page<CategoryResponseDTO> categoryResponseDTOPage = categoryService.paginate(pageable);
        return new ResponseEntity<>(categoryResponseDTOPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.create(categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CategoryResponseDTO responseDTO = categoryService.findById(id);
        if (responseDTO != null) {
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy danh mục", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryUpdateRequestDTO categoryUpdateRequestDTO) {
        categoryUpdateRequestDTO.setId(id);
        CategoryResponseDTO responseDTO = categoryService.update(categoryUpdateRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        //return new ResponseEntity<>("Đã xóa danh mục",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
