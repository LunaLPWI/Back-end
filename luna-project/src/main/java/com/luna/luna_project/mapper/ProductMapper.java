package com.luna.luna_project.mapper;


import com.luna.luna_project.dtos.product.ProductRequestDTO;
import com.luna.luna_project.dtos.product.ProductResponseDTO;
import com.luna.luna_project.models.ProductStock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO productToProductResponseDTO(ProductStock product);
    ProductStock productRequestToEntity(ProductRequestDTO productRequestDTO);
}
