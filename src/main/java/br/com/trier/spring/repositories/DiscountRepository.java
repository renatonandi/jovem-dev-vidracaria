package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer>{
    
    List<Discount> findByDescription(String description);
    
}
