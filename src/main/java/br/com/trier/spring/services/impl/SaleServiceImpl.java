package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.Sale;
import br.com.trier.spring.models.Request;
import br.com.trier.spring.repositories.SaleRepository;
import br.com.trier.spring.services.SaleService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class SaleServiceImpl implements SaleService{
    
    @Autowired
    private SaleRepository repository;

    @Override
    public Sale findById(Integer id) {
        Optional<Sale> productRequest = repository.findById(id);
        return productRequest.orElseThrow(() -> new ObjectNotFound("A venda %s não existe".formatted(id)));
    }

    @Override
    public Sale insert(Sale productRequest) {
        return repository.save(productRequest);
    }

    @Override
    public Sale update(Sale productRequest) {
        findById(productRequest.getId());
        return repository.save(productRequest);
    }

    @Override
    public void delete(Integer id) {
        Sale productRequest = findById(id);
        if (productRequest != null) {
            repository.delete(productRequest);
        }
        
    }

    @Override
    public List<Sale> listAll() {
        List<Sale> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma venda cadastrado");
        }
        return list;
    }

    @Override
    public List<Sale> findByRequest(Request request) {
        List<Sale> list = repository.findByRequest(request);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma venda encontrado para esse pedido");
        }
        return list;
    }

    @Override
    public List<Sale> findByProduct(Product product) {
        List<Sale> list = repository.findByProduct(product);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma venda encontrado para esse produto");
        }
        return list;
    }

    @Override
    public List<Sale> findBySize(Double size) {
        List<Sale> list = repository.findBySize(size);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma venda encontrado para esse tamanho");
        }
        return list;
    }

}
