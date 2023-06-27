package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.City;

public interface CityService {

    City findById(Integer id);

    City insert(City city);

    City update(City city);

    void delete(Integer id);

    List<City> listAll();

    List<City> findByNameContainingIgnoreCase(String name);

    List<City> findByNameContainingIgnoreCaseAndUfIgnoreCase(String name, String uf);
    
    List<City> findByUf(String uf);

}
