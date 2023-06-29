package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Type;
import br.com.trier.spring.repositories.TypeRepository;
import br.com.trier.spring.services.TypeService;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class TypeServiceImpl implements TypeService{
    
    @Autowired
    private TypeRepository repository;
    
    public void validateName(Type type) {
        Type busca = repository.findByDescriptionIgnoreCase(type.getDescription());
        if (busca != null && busca.getId() != type.getId()) {
            throw new IntegrityViolation("Esse tipo já está cadastrado");
        }
    }

    @Override
    public Type findById(Integer id) {
        Optional<Type> type = repository.findById(id);
        return type.orElseThrow(() -> new ObjectNotFound("O tipo %s não existe".formatted(id)));
    }

    @Override
    public Type insert(Type type) {
        validateName(type);
        return repository.save(type);
    }

    @Override
    public Type update(Type type) {
        findById(type.getId());
        validateName(type);
        return repository.save(type);
    }

    @Override
    public void delete(Integer id) {
        Type type = findById(id);
        if (type != null) {
            repository.delete(type);
        }
        
    }

    @Override
    public List<Type> listAll() {
        List<Type> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum tipo cadastrado");
        }
        return list;
    }

    @Override
    public List<Type> findByDescriptionContainingIgnoreCase(String description) {
        List<Type> list = repository.findByDescriptionContainingIgnoreCase(description);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum tipo encontrado para esse nome %s".formatted(description));
        }
        return list;
    }

}
