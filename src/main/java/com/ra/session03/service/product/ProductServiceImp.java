package com.ra.session03.service.product;

import com.ra.session03.model.dto.product.ProductRequestDTO;
import com.ra.session03.model.dto.product.ProductResponseDTO;
import com.ra.session03.model.dto.product.ProductUpdateRequestDTO;
import com.ra.session03.model.entity.Category;
import com.ra.session03.model.entity.Product;
import com.ra.session03.repository.CategoryRepository;
import com.ra.session03.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> responseDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setId(product.getId());
            responseDTO.setProductName(product.getProductName());
            responseDTO.setPrice(product.getPrice());
            responseDTO.setImage(product.getImage());
            responseDTO.setCategoryName(product.getCategory().getCategoryName());
            responseDTOS.add(responseDTO);
        }
        return responseDTOS;
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).orElse(null);
        Product product = Product.builder()
                .productName(productRequestDTO.getProductName())
                .price(productRequestDTO.getPrice())
                .image(productRequestDTO.getImage())
                .category(category)
                .build();
        Product productNew = productRepository.save(product);

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                .id(productNew.getId())
                .productName(productNew.getProductName())
                .price(productNew.getPrice())
                .image(productNew.getImage())
                .categoryName(productNew.getCategory().getCategoryName())
                .build();
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO findById(long id) {
        Product product = productRepository.findById(id).orElse(null);
        //convert entity --> DTO
        if (product != null) {
            ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                    .id(product.getId())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .image(product.getImage())
                    .categoryName(product.getCategory().getCategoryName())
                    .build();
            return productResponseDTO;
        }
        return null;
    }

    @Override
    public ProductResponseDTO update(ProductUpdateRequestDTO productUpdateRequestDTO) {
        //Product product = productRepository.findById(id).orElse(null);
        Category category = categoryRepository.findById(productUpdateRequestDTO.getCategoryId()).orElse(null);
        Product product = Product.builder()
                .id(productUpdateRequestDTO.getId())
                .productName(productUpdateRequestDTO.getProductName())
                .price(productUpdateRequestDTO.getPrice())
                .image(productUpdateRequestDTO.getImage())
                .category(category)
                .build();
        Product productNew = productRepository.save(product);

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                .id(productNew.getId())
                .productName(productNew.getProductName())
                .price(productNew.getPrice())
                .image(productNew.getImage())
                .categoryName(productNew.getCategory().getCategoryName())
                .build();
        return productResponseDTO;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    //Phân trang
    @Override
    public Page<ProductResponseDTO> paginate(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        //Java 8
        Page<ProductResponseDTO> productResponseDTOS = products.map(product -> {
            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setId(product.getId());
            responseDTO.setProductName(product.getProductName());
            responseDTO.setPrice(product.getPrice());
            responseDTO.setImage(product.getImage());
            responseDTO.setCategoryName(product.getCategory().getCategoryName());
            return responseDTO;
        });
        return productResponseDTOS;
    }

    //Tìm kiếm
    @Override
    public List<ProductResponseDTO> searchByProductName(String keyword) {
        List<Product> products = productRepository.searchByName(keyword);
        //Java 8
        List<ProductResponseDTO> responseDTOS = products.stream().map(product -> {
            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setId(product.getId());
            responseDTO.setProductName(product.getProductName());
            responseDTO.setPrice(product.getPrice());
            responseDTO.setImage(product.getImage());
            responseDTO.setCategoryName(product.getCategory().getCategoryName());
            return responseDTO;
        }).toList();
        return responseDTOS;
    }

    //Tìm kiếm đúng chính xác với từ khóa
//    @Override
//    public List<ProductResponseDTO> searchByProductName(String keyword) {
//        List<Product> products = productRepository.findProductByProductName(keyword);
//        //Java 8
//        List<ProductResponseDTO> responseDTOS = products.stream().map(product -> {
//            ProductResponseDTO responseDTO = new ProductResponseDTO();
//            responseDTO.setId(product.getId());
//            responseDTO.setProductName(product.getProductName());
//            responseDTO.setPrice(product.getPrice());
//            responseDTO.setImage(product.getImage());
//            responseDTO.setCategoryName(product.getCategory().getCategoryName());
//            return responseDTO;
//        }).toList();
//        return responseDTOS;
//    }
}
