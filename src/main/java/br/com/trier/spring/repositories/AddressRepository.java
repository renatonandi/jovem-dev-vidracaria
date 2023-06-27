package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.Address;
import br.com.trier.spring.models.City;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
    
    List<Address> findByStreetContainingIgnoreCase(String street);
    
    List<Address> findByNeighborhoodContainingIgnoreCase(String neighborhood);
    
    List<Address> findByNeighborhoodContainingIgnoreCaseAndStreetContainingIgnoreCase(String neighborhood, String street);
    
    List<Address> findByCityContaining(City city);

}
