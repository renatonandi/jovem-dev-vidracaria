package br.com.trier.spring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Address;
import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.Discount;
import br.com.trier.spring.repositories.CustomerRepository;
import br.com.trier.spring.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
    
    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Customer insert(Customer customer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Customer> listAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Customer> findByNameContainingIgnoreCase(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Customer> findByAddress(Address address) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Customer> findByDiscount(Discount discount) {
        // TODO Auto-generated method stub
        return null;
    }

}
