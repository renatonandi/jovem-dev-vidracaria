package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Address;
import br.com.trier.spring.models.City;

public interface AddressService {

    Address findById(Integer id);

    Address insert(Address address);

    Address update(Address address);

    void delete(Integer id);

    List<Address> listAll();

    List<Address> findByStreetContainingIgnoreCase(String street);

    List<Address> findByNeighborhoodContainingIgnoreCase(String neighborhood);

    List<Address> findByNeighborhoodContainingIgnoreCaseAndStreetContainingIgnoreCase(String neighborhood, String street);

    List<Address> findByCity(City city);
    
}
