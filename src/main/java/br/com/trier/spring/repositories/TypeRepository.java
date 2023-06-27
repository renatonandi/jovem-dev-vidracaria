package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer>{
    
    List<Type> findByDescriptionContainingIgnoreCase(String description);
    
    Type findByDescription(String description);

}
