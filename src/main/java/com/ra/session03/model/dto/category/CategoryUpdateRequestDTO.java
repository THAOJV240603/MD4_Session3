package com.ra.session03.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryUpdateRequestDTO {
    private Long id;
    public String categoryName;
    public Boolean categoryStatus;
}
