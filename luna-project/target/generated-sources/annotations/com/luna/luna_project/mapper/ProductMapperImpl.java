package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.product.ProductRequestDTO;
import com.luna.luna_project.dtos.product.ProductResponseDTO;
import com.luna.luna_project.models.ProductStock;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:53-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDTO productToProductResponseDTO(ProductStock product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDTO.ProductResponseDTOBuilder productResponseDTO = ProductResponseDTO.builder();

        productResponseDTO.id( product.getId() );
        productResponseDTO.name( product.getName() );
        productResponseDTO.amount( product.getAmount() );
        productResponseDTO.price( product.getPrice() );
        productResponseDTO.mark( product.getMark() );
        productResponseDTO.category( product.getCategory() );

        return productResponseDTO.build();
    }

    @Override
    public ProductStock productRequestToEntity(ProductRequestDTO productRequestDTO) {
        if ( productRequestDTO == null ) {
            return null;
        }

        ProductStock.ProductStockBuilder productStock = ProductStock.builder();

        productStock.name( productRequestDTO.getName() );
        productStock.amount( productRequestDTO.getAmount() );
        productStock.price( productRequestDTO.getPrice() );
        productStock.mark( productRequestDTO.getMark() );
        productStock.category( productRequestDTO.getCategory() );

        return productStock.build();
    }
}
