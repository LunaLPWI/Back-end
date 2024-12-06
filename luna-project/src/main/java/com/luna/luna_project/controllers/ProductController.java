package com.luna.luna_project.controllers;


import com.luna.luna_project.dtos.product.ProductRequestDTO;
import com.luna.luna_project.dtos.product.ProductResponseDTO;
import com.luna.luna_project.mapper.ProductMapper;
import com.luna.luna_project.models.ProductStock;
import com.luna.luna_project.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts() {
        List<ProductStock> products = productService.getAllProducts();

        return ResponseEntity.ok(products.stream().map(
                productMapper::productToProductResponseDTO).toList());
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/change-price")
    public ResponseEntity<ProductResponseDTO> changePrice(@RequestParam @Valid Double price, @RequestParam @Valid Long productId) {
        ProductResponseDTO productResponseDTO = productMapper.productToProductResponseDTO(productService.changePrice(price, productId));
        return ResponseEntity.ok(productResponseDTO);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/change-quantity")
    public ResponseEntity<ProductResponseDTO> changeQuantity(@RequestParam @Valid Integer qtd, @RequestParam @Valid Long productId) {
        ProductResponseDTO productResponseDTO = productMapper.productToProductResponseDTO(productService.updateQtd(qtd, productId));
        return ResponseEntity.ok(productResponseDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Valid Long id) {
        productService.deleteProductByid(id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductStock product = productMapper.productRequestToEntity(productRequestDTO);
        ProductResponseDTO productResponseDTO = productService.addProduct(product);
        return ResponseEntity.ok(productResponseDTO);
    }
}
