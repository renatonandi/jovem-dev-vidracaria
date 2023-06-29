package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{
    
    List<City> findByNameContainingIgnoreCase(String name);
    
    List<City> findByNameContainingIgnoreCaseAndUfIgnoreCase(String name, String uf);
    
    List<City> findByUfIgnoreCase(String uf);
    
    City findByNameIgnoreCase(String name);
    
}
