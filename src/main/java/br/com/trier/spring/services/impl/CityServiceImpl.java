package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.City;
import br.com.trier.spring.repositories.CityRepository;
import br.com.trier.spring.services.CityService;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class CityServiceImpl implements CityService{
    
    @Autowired
    private CityRepository repository;
    
    public void validateName(City city) {
        City busca = repository.findByNameIgnoreCase(city.getName());
        if (busca != null && busca.getId() != city.getId()) {
            throw new IntegrityViolation("Essa cidade já está cadastrada");
        }
    }

    @Override
    public City findById(Integer id) {
        Optional<City> city = repository.findById(id);
        return city.orElseThrow(() -> new ObjectNotFound("A cidade %s não existe".formatted(id)));
    }

    @Override
    public City insert(City city) {
        validateName(city);
        return repository.save(city);
    }

    @Override
    public City update(City city) {
        findById(city.getId());
        validateName(city);
        return repository.save(city);
    }

    @Override
    public void delete(Integer id) {
        City city = findById(id);
        if (city != null) {
            repository.delete(city);
        }
    }

    @Override
    public List<City> listAll() {
        List<City> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma cidade cadastrada");
        }
        return list;
    }

    @Override
    public List<City> findByNameContainingIgnoreCase(String name) {
        List<City> list = repository.findByNameContainingIgnoreCase(name);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma cidade encontrada para esse nome %s".formatted(name));
        }
        return list;
    }

    @Override
    public List<City> findByNameContainingIgnoreCaseAndUfIgnoreCase(String name, String uf) {
        List<City> list = repository.findByNameContainingIgnoreCaseAndUfIgnoreCase(name, uf);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma cidade encontrada para esse nome %s e para essa UF %s".formatted(name, uf));
        }
        return list;
    }

    @Override
    public List<City> findByUf(String uf) {
        List<City> list = repository.findByUfIgnoreCase(uf);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma cidade encontrada para essa UF %s".formatted(uf));
        }
        return list;
    }

}
