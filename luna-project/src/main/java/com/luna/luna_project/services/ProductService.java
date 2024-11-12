package com.luna.luna_project.services;


import com.luna.luna_project.dtos.product.ProductResponseDTO;
import com.luna.luna_project.mapper.ProductMapper;
import com.luna.luna_project.models.Product;
import com.luna.luna_project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public ProductResponseDTO addProduct(Product product) {
        product.setId(null);
        productRepository.save(product);
        return productMapper.productToProductResponseDTO(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product changePrice(Double price, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (price<0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor não pode ser negativo");
        }
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found by id");
        }
        product.get().setPrice(price);
        productRepository.save(product.get());
        return product.get();
    }

    public Product updateQtd(Integer qtd, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found by id");
        }
        if(qtd<0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantidade de produtos não pode ser negativa");
        }
        product.get().setQtd(qtd);
        productRepository.save(product.get());
        return product.get();
    }


    public void deleteProductByid(Long id) {


//        if (!productRepository.existsById(id)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found by id");
//        }
        productRepository.deleteById(id);
    }

}
