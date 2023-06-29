package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.Sale;
import br.com.trier.spring.models.Request;

public interface SaleService {

    Sale findById(Integer id);

    Sale insert(Sale productRequest);

    Sale update(Sale productRequest);

    void delete(Integer id);

    List<Sale> listAll();

    List<Sale> findByRequest(Request request);

    List<Sale> findByProduct(Product product);

    List<Sale> findBySize(Double size);

}
