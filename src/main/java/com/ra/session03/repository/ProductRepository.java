package com.ra.session03.repository;

import com.ra.session03.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Tìm kiếm
    @Query("select p from Product p where p.productName like %:keyword%")
    List<Product> searchByName(String keyword);

    //Tìm kiếm đúng chính xác với từ khóa
    //List<Product> findProductByProductName(String keyword);
}
