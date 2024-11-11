package com.ra.session03.model.dto.category;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponseDTO {
    private Long id;
    public String categoryName;
    public Boolean categoryStatus;
}
