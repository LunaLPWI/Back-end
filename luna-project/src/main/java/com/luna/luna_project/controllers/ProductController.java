package com.luna.luna_project.controllers;


import com.luna.luna_project.dtos.product.ProductRequestDTO;
import com.luna.luna_project.dtos.product.ProductResponseDTO;
import com.luna.luna_project.mapper.ProductMapper;
import com.luna.luna_project.models.Product;
import com.luna.luna_project.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;


    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts(){
        List<Product> products = productService.getAllProducts();

        return  ResponseEntity.ok(products.stream().map(
                productMapper::productToProductResponseDTO).toList());
    }

    @PutMapping("/change-price")
    public ResponseEntity<ProductResponseDTO> changePrice(@RequestParam @Valid Double price, @RequestParam @Valid Long productId){
        ProductResponseDTO productResponseDTO = productMapper.productToProductResponseDTO(productService.changePrice(price, productId));
        return ResponseEntity.ok(productResponseDTO);
    }

    @PutMapping("/change-quantity")
    public ResponseEntity<ProductResponseDTO> changeQuantity(@RequestParam @Valid Integer qtd, @RequestParam @Valid Long productId){
        ProductResponseDTO productResponseDTO = productMapper.productToProductResponseDTO(productService.updateQtd(qtd, productId));
        return  ResponseEntity.ok(productResponseDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Valid Long id){
        productService.deleteProductByid(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        Product product = productMapper.productRequestToEntity(productRequestDTO);
        ProductResponseDTO productResponseDTO = productService.addProduct(product);
        return ResponseEntity.ok(productResponseDTO);
    }
}
