package com.ra.session03.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name", length = 100, nullable = false, unique = true)
    private String categoryName;
    @Column(name = "category_status")
    private Boolean categoryStatus;
    //Mỗi danh mục có nhiều sản phẩm
    @OneToMany(mappedBy = "category")
    //
    @JsonIgnore
    private Set<Product> products;
}
