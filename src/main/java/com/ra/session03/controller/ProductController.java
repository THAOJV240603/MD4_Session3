package com.ra.session03.controller;

import com.ra.session03.model.dto.product.ProductRequestDTO;
import com.ra.session03.model.dto.product.ProductResponseDTO;
import com.ra.session03.model.dto.product.ProductUpdateRequestDTO;
import com.ra.session03.service.product.ProductService;
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
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping
//    public ResponseEntity<?> index() {
//        List<ProductResponseDTO> responseDTOS = productService.findAll();
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
        Page<ProductResponseDTO> productResponseDTOPage = productService.paginate(pageable);
        return new ResponseEntity<>(productResponseDTOPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO  = productService.create(productRequestDTO);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ProductResponseDTO responseDTO = productService.findById(id);
        if (responseDTO != null) {
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Không tìm thấy sản phẩm", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO) {
        productUpdateRequestDTO.setId(id);
        ProductResponseDTO responseDTO = productService.update(productUpdateRequestDTO);
        if (responseDTO == null) {
            return new ResponseEntity<>("Sản phẩm không tồn tại", HttpStatus.OK);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        //return new ResponseEntity<>("Đã xóa sản phẩm", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Tìm kiếm
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "keyword") String keyword) {
        List<ProductResponseDTO> responseDTOS = productService.searchByProductName(keyword);
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }
}
