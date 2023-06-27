package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Address;
import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.Discount;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    
    List<Customer> findByNameContainingIgnoreCase(String name);
    
    List<Customer> findByAddress(Address address);
    
    List<Customer> findByDiscount(Discount discount);

}
