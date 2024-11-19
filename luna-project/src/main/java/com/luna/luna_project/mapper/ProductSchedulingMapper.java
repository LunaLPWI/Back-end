package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.product.ProductSchedulingRequestDTO;
import com.luna.luna_project.models.ProductScheduling;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSchedulingMapper {
    ProductScheduling ProductSchedulingRequestDTOToProductScheduling(ProductSchedulingRequestDTO productSchedulingRequestDTO);
}
