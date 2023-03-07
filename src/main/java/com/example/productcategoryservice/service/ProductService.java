package com.example.productcategoryservice.service;


import com.example.productcategoryservice.entity.Product;
import com.example.productcategoryservice.exception.Error;
import com.example.productcategoryservice.exception.EntityNotFoundException;
import com.example.productcategoryservice.repository.ProductRepository;
import com.example.productcategoryservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;


    public List<Product> getAllProducts() {
        List<Product> all = productRepository.findAll();
        //change currency AMD to USD
        if (!all.isEmpty()) {
            ResponseEntity<HashMap> currency = restTemplate.getForEntity("https://cb.am/latest.json.php?currency=USD", HashMap.class);
            HashMap<String, String> hashMap = currency.getBody();
            double usdCurrency = Double.parseDouble(hashMap.get("USD"));
            if (usdCurrency > 0) {
                for (Product product : all) {
                    double price = product.getPrice() / usdCurrency;
                    DecimalFormat df = new DecimalFormat("#.##");
                    product.setPrice(Double.parseDouble(df.format(price)));
                }
            }

        }
        return all;
    }

    public Optional<Product> findProductById(int id) {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new EntityNotFoundException(Error.DIRECTORY_NOT_FOUND);
        }
        return productById;
    }

    public Product findById(int id) {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new EntityNotFoundException(Error.DIRECTORY_NOT_FOUND);
        }
        return productById.get();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(int id, CurrentUser currentUser) {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isPresent() && currentUser.getUser().getId() == productById.get().getUser().getId()) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(Error.DIRECTORY_NOT_FOUND);
        }
    }

    public List<Product> getAllProductsByCatId(int id) {
        return productRepository.findAllByCategoryId(id);
    }


}
