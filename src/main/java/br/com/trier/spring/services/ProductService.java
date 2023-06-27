package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.Type;

public interface ProductService {

    Product findById(Integer id);

    Product insert(Product product);

    Product update(Product product);

    void delete(Integer id);

    List<Product> listAll();

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByType(Type type);

}
