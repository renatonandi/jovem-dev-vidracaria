package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Product;
import br.com.trier.spring.models.ProductRequest;
import br.com.trier.spring.models.Request;
import br.com.trier.spring.repositories.ProductRequestRepository;
import br.com.trier.spring.services.ProductRequestService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class ProductRequestServiceImpl implements ProductRequestService{
    
    @Autowired
    private ProductRequestRepository repository;

    @Override
    public ProductRequest findById(Integer id) {
        Optional<ProductRequest> productRequest = repository.findById(id);
        return productRequest.orElseThrow(() -> new ObjectNotFound("O produto/pedido %s não existe".formatted(id)));
    }

    @Override
    public ProductRequest insert(ProductRequest productRequest) {
        return repository.save(productRequest);
    }

    @Override
    public ProductRequest update(ProductRequest productRequest) {
        findById(productRequest.getId());
        return repository.save(productRequest);
    }

    @Override
    public void delete(Integer id) {
        ProductRequest productRequest = findById(id);
        if (productRequest != null) {
            repository.delete(productRequest);
        }
        
    }

    @Override
    public List<ProductRequest> listAll() {
        List<ProductRequest> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum produto/pedido cadastrado");
        }
        return list;
    }

    @Override
    public List<ProductRequest> findByRequest(Request request) {
        List<ProductRequest> list = repository.findByRequest(request);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum produto/pedido encontrado para esse pedido");
        }
        return list;
    }

    @Override
    public List<ProductRequest> findByProduct(Product product) {
        List<ProductRequest> list = repository.findByProduct(product);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum produto/pedido encontrado para esse produto");
        }
        return list;
    }

    @Override
    public List<ProductRequest> findBySize(Double size) {
        List<ProductRequest> list = repository.findBySize(size);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum produto/pedido encontrado para esse tamanho");
        }
        return list;
    }

}
