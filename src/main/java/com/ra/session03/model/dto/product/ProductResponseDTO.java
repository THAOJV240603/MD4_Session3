package com.ra.session03.model.dto.product;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private Long id;
    private String productName;
    private Double price;
    private String image;
    //private Category category;
    private String categoryName;
}
