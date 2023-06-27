package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.ProductRequest;
import br.com.trier.spring.models.Request;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer>{
    
    List<ProductRequest> findByRequest(Request request);
    
    List<ProductRequest> findByProduct(Product product);
    
    List<ProductRequest> findBySize(Double size);
    
}
