package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.Type;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByType(Type type);
    
}
