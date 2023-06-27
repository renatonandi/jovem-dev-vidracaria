package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Type;

public interface TypeService {
    
    Type findById(Integer id);

    Type insert(Type type);

    Type update(Type type);

    void delete(Integer id);

    List<Type> listAll();
    
    List<Type> findByDescriptionContainingIgnoreCase(String description);

}
