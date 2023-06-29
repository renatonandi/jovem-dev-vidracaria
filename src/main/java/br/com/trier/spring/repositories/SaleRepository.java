package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.Sale;
import br.com.trier.spring.models.Request;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>{
    
    List<Sale> findByRequest(Request request);
    
    List<Sale> findByProduct(Product product);
    
    List<Sale> findBySize(Double size);
    
}
