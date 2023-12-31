package com.estore.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String productName;

    private String barCode;

    private String productDescription;

    private String productCode;

    private Long brandId;
}
