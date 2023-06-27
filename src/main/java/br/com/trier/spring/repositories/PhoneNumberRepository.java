package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.PhoneNumber;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer>{
    
    PhoneNumber findByNumber(String number);
    
    List<PhoneNumber> findByCustomer(Customer customer);

}
