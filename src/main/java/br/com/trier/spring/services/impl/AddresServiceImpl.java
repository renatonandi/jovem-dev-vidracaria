package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Address;
import br.com.trier.spring.models.City;
import br.com.trier.spring.repositories.AddressRepository;
import br.com.trier.spring.services.AddressService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class AddresServiceImpl implements AddressService{

    @Autowired
    private AddressRepository repository;
    
    @Override
    public Address findById(Integer id) {
        Optional<Address> address = repository.findById(id);
        return address.orElseThrow(() -> new ObjectNotFound("O endereço %s não existe".formatted(id)));
    }

    @Override
    public Address insert(Address address) {
        return repository.save(address);
    }

    @Override
    public Address update(Address address) {
        findById(address.getId());
        return repository.save(address);
    }

    @Override
    public void delete(Integer id) {
        Address address = findById(id);
        if (address != null) {
            repository.delete(address);
        }
    }

    @Override
    public List<Address> listAll() {
        List<Address> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nem um endereço cadastrado");
        }
        return list;
    }

    @Override
    public List<Address> findByStreetContainingIgnoreCase(String street) {
        List<Address> list = repository.findByStreetContainingIgnoreCase(street);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum endereço encontrado para essa rua %s".formatted(street));
        }
        return list;
    }

    @Override
    public List<Address> findByNeighborhoodContainingIgnoreCase(String neighborhood) {
        List<Address> list = repository.findByNeighborhoodContainingIgnoreCase(neighborhood);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma endereço encontrado para esse bairro %s".formatted(neighborhood));
        }
        return list;
    }

    @Override
    public List<Address> findByNeighborhoodContainingIgnoreCaseAndStreetContainingIgnoreCase(String neighborhood,
            String street) {
        List<Address> list = repository.findByNeighborhoodContainingIgnoreCaseAndStreetContainingIgnoreCase(neighborhood, street);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum endereço encontrado para esse bairro %s e para essa rua %s".formatted(neighborhood, street));
        }
        return list;
    }

    @Override
    public List<Address> findByCityContaining(City city) {
        List<Address> list = repository.findByCityContaining(city);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum endereço encontrado para essa cidade %s".formatted(city.getName()));
        }
        return list;
    }

}
