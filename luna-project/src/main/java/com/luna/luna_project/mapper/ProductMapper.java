package com.luna.luna_project.mapper;


import com.luna.luna_project.dtos.product.ProductRequestDTO;
import com.luna.luna_project.dtos.product.ProductResponseDTO;
import com.luna.luna_project.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO productToProductResponseDTO(Product product);
    Product productRequestToEntity(ProductRequestDTO productRequestDTO);
}
