package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>{
    
    List<Request> findByDescriptionContainingIgnoreCase(String description);
    
    List<Request> findByCustomer(Customer customer);

}
