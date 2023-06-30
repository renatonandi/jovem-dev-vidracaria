package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Address;
import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.Discount;
import br.com.trier.spring.repositories.CustomerRepository;
import br.com.trier.spring.services.CustomerService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class CustomerServiceImpl implements CustomerService{
    
    @Autowired
    private CustomerRepository repository;
    
    @Override
    public Customer findById(Integer id) {
        Optional<Customer> customer = repository.findById(id);
        return customer.orElseThrow(() -> new ObjectNotFound("Cliente %s não existe".formatted(id)));
    }

    @Override
    public Customer insert(Customer customer) {
        return repository.save(customer);
    }
 
    @Override
    public Customer update(Customer customer) {
        findById(customer.getId());
        return repository.save(customer);
    }

    @Override
    public void delete(Integer id) {
        Customer customer = findById(id);
        if (customer != null) {
            repository.delete(customer);
        }
    }

    @Override
    public List<Customer> listAll() {
        List<Customer> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum cliente cadastrado");
        }
        return list;
    }

    @Override
    public List<Customer> findByNameContainingIgnoreCase(String name) {
        List<Customer> list = repository.findByNameContainingIgnoreCase(name);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum cliente encontrado para esse nome %s".formatted(name));
        }
        return list;
    }

    @Override
    public List<Customer> findByAddress(Address address) {
        List<Customer> list = repository.findByAddress(address);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum cliente encontrado para esse endereço: %s , %s e %s".formatted(address.getStreet(), address.getNeighborhood(), address.getCity().getName()));
        }
        return list;
    }

    @Override
    public List<Customer> findByDiscount(Discount discount) {
        List<Customer> list = repository.findByDiscount(discount);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum cliente encontrado para esse desconto %s".formatted(discount.getDiscount()));
        }
        return list;
    }

    @Override
    public List<Customer> findByNameContainingIgnoreCaseAndAddress(String name, Address address) {
        List<Customer> list = repository.findByNameContainingIgnoreCaseAndAddress(name, address);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum cliente encontrado para esse nome %s e endereço rua %s bairro %s".formatted(name, address.getStreet(), address.getNeighborhood()));
        }
        return list;
    }
}
