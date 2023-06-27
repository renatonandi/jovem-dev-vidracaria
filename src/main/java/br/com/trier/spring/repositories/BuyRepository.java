package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Buy;
import br.com.trier.spring.models.ProductRequest;
import br.com.trier.spring.models.Request;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Integer>{
    
    List<Buy> findByAmount(Double amount);
    
    List<Buy> findByAmountBetween(Double firstAmount, Double lastAmount);
    
    List<Buy> findByProductRequestContaining(ProductRequest productRequest);

}
