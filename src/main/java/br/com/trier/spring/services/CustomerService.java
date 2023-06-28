package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Address;
import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.Discount;

public interface CustomerService {


    Customer insert(Customer customer);

    Customer update(Customer customer);

    void delete(Integer id);

    Customer findById(Integer id);

    List<Customer> listAll();

    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByAddress(Address address);

    List<Customer> findByDiscount(Discount discount);

}
