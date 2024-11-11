package com.ra.session03.service.product;

import com.ra.session03.model.dto.product.ProductRequestDTO;
import com.ra.session03.model.dto.product.ProductResponseDTO;
import com.ra.session03.model.dto.product.ProductUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll();
    ProductResponseDTO create(ProductRequestDTO productRequestDTO);
    ProductResponseDTO findById(long id);
    ProductResponseDTO update(ProductUpdateRequestDTO productUpdateRequestDTO);
    void delete(Long id);

    //Phân trang
    Page<ProductResponseDTO> paginate(Pageable pageable);

    //Tìm kiếm
    List<ProductResponseDTO> searchByProductName(String keyword);

}
