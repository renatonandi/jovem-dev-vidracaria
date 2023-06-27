package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.PhoneNumber;

public interface PhoneNumberService {

    PhoneNumber findById(Integer id);

    PhoneNumber insert(PhoneNumber phoneNumber);

    PhoneNumber update(PhoneNumber phoneNumber);

    void delete(Integer id);

    List<PhoneNumber> listAll();

    PhoneNumber findByNumber(String number);

    List<PhoneNumber> findByCustomer(Customer customer);

}
