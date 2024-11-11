package com.ra.session03.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequestDTO {
    private String productName;
    private Double price;
    private String image;
    //private Category category;
    private Long categoryId;
}
