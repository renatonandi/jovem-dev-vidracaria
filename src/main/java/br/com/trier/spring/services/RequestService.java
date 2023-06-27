package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.Request;

public interface RequestService {

    Request findById(Integer id);

    Request insert(Request request);

    Request update(Request request);

    void delete(Integer id);

    List<Request> listAll();

    List<Request> findByDescriptionContainingIgnoreCase(String description);

    List<Request> findByCustomer(Customer customer);

}
