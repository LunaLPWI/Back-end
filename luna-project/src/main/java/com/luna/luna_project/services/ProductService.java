package com.luna.luna_project.services;


import com.luna.luna_project.dtos.product.ProductResponseDTO;
import com.luna.luna_project.mapper.ProductMapper;
import com.luna.luna_project.models.ProductStock;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.models.Stack;
import com.luna.luna_project.repositories.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductStockRepository productStockRepository;
    @Autowired
    private ProductMapper productMapper;

    private Stack<ProductStock> stack = new Stack<ProductStock>();

    public ProductResponseDTO addProduct(ProductStock product) {
        product.setId(null);
        productStockRepository.save(product);
        return productMapper.productToProductResponseDTO(product);
    }

    public List<ProductStock> getAllProducts() {
        return productStockRepository.findAll();
    }


    public ProductStock changePrice(Double price, Long productId) {
        Optional<ProductStock> product = productStockRepository.findById(productId);
        if (price<0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor não pode ser negativo");
        }
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found by id");
        }
        product.get().setPrice(price);
        productStockRepository.save(product.get());
        return product.get();
    }

    public ProductStock updateQtd(Integer qtd, Long productId) {
        Optional<ProductStock> product = productStockRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found by id");
        }
        if(qtd<0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantidade de produtos não pode ser negativa");
        }
        product.get().setAmount(qtd);
        productStockRepository.save(product.get());
        return product.get();
    }


    public void deleteProductByid(Long id) {
       Optional<ProductStock> productStock =  productStockRepository.findById(id);
       if (productStock.isEmpty()){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found by id");
       }
       productStockRepository.deleteById(id);
       productStock.get().setId(null);
       stack.push(productStock.get());
    }


    public ProductStock undoProduct() {
        if (stack.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não há nenhum agendamento a ser restaurado");
        }
        ProductStock productStock = stack.pop();
        return productStockRepository.save(productStock);
    }

}
