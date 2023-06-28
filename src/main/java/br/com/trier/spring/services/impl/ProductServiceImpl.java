package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.Type;
import br.com.trier.spring.repositories.ProductRepository;
import br.com.trier.spring.services.ProductService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private ProductRepository repository;

    @Override
    public Product findById(Integer id) {
        Optional<Product> product = repository.findById(id);
        return product.orElseThrow(() -> new ObjectNotFound("O produto %s não existe".formatted(id)));
    }

    @Override
    public Product insert(Product product) {
        return repository.save(product);
    }

    @Override
    public Product update(Product product) {
        findById(product.getId());
        return repository.save(product);
    }

    @Override
    public void delete(Integer id) {
        Product product = findById(id);
        if (product != null) {
            repository.delete(product);
        }
    }

    @Override
    public List<Product> listAll() {
        List<Product> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum produto cadastrado");
        }
        return list;
    }

    @Override
    public List<Product> findByNameContainingIgnoreCase(String name) {
        List<Product> list = repository.findByNameContainingIgnoreCase(name);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum produto encontrado para esse nome %s".formatted(name));
        }
        return list;
    }

    @Override
    public List<Product> findByType(Type type) {
        List<Product> list = repository.findByType(type);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum produto encontrado para esse tipo %s".formatted(type.getDescription()));
        }
        return list;
    }

}
