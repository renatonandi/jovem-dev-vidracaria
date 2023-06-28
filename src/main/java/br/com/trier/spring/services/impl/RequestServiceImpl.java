package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.Request;
import br.com.trier.spring.repositories.RequestRepository;
import br.com.trier.spring.services.RequestService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class RequestServiceImpl implements RequestService{
    
    @Autowired
    private RequestRepository repository;

    @Override
    public Request findById(Integer id) {
        Optional<Request> request = repository.findById(id);
        return request.orElseThrow(() -> new ObjectNotFound("O pedido %s não existe".formatted(id)));
    }

    @Override
    public Request insert(Request request) {
        return repository.save(request);
    }

    @Override
    public Request update(Request request) {
        findById(request.getId());
        return repository.save(request);
    }

    @Override
    public void delete(Integer id) {
        Request request = findById(id);
        if (request != null) {
            repository.delete(request);
        }
        
    }

    @Override
    public List<Request> listAll() {
        List<Request> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum pedido cadastrado");
        }
        return list;
    }

    @Override
    public List<Request> findByDescriptionContainingIgnoreCase(String description) {
        List<Request> list = repository.findByDescriptionContainingIgnoreCase(description);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum pedido encontrado para essa descrição %s".formatted(description));
        }
        return list;
    }

    @Override
    public List<Request> findByCustomer(Customer customer) {
        List<Request> list = repository.findByCustomer(customer);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum pedido encontrado para esse cliente %s".formatted(customer.getName()));
        }
        return list;
    }

}
