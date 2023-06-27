package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.ProductRequest;
import br.com.trier.spring.models.Request;

public interface ProductRequestService {

    ProductRequest findById(Integer id);

    ProductRequest insert(ProductRequest productRequest);

    ProductRequest update(ProductRequest productRequest);

    void delete(Integer id);

    List<ProductRequest> listAll();

    List<ProductRequest> findByRequest(Request request);

    List<ProductRequest> findByProduct(Product product);

    List<ProductRequest> findBySize(Double size);

}
