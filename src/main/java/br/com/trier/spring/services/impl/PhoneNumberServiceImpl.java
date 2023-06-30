package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Customer;
import br.com.trier.spring.models.PhoneNumber;
import br.com.trier.spring.repositories.PhoneNumberRepository;
import br.com.trier.spring.services.PhoneNumberService;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService{
    
    @Autowired
    private PhoneNumberRepository repository;
    
    public void validPhoneNumber(PhoneNumber phoneNumber) {
        String onlyDigits = phoneNumber.getNumber().replaceAll("[^0-9]", "");
        if (onlyDigits.length() != 11) {
            throw new IntegrityViolation("Número de telefone inálido");            
        }
        PhoneNumber busca = repository.findByNumberContaining(phoneNumber.getNumber());
        if (busca != null && busca.getId() != phoneNumber.getId()) {
            throw new IntegrityViolation("Esse numero de telefone já está cadastrado");
        }
    }
    
    @Override
    public PhoneNumber findById(Integer id) {
        Optional<PhoneNumber> phoneNumber = repository.findById(id);
        return phoneNumber.orElseThrow(() -> new ObjectNotFound("O numero de telefone %s não existe".formatted(id)));
    }

    @Override
    public PhoneNumber insert(PhoneNumber phoneNumber) {
    	phoneNumber.formatedNumber(phoneNumber.getNumber());
        validPhoneNumber(phoneNumber);
        return repository.save(phoneNumber);
    }

    @Override
    public PhoneNumber update(PhoneNumber phoneNumber) {
        findById(phoneNumber.getId());
        return repository.save(phoneNumber);
    }

    @Override
    public void delete(Integer id) {
        PhoneNumber number = findById(id);
        if (number != null) {
            repository.delete(number);
        }
    }

    @Override
    public List<PhoneNumber> listAll() {
        List<PhoneNumber> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum numero de telefone cadastrado");
        }
        return list;
    }

    @Override
    public PhoneNumber findByNumber(String number) {
        PhoneNumber list = repository.findByNumberContaining(number);
        if (list == null) {
            throw new ObjectNotFound("Nenhum numero de telefone encontrado para esse numero %s".formatted(number));
        }
        return list;
    }

    @Override
    public List<PhoneNumber> findByCustomer(Customer customer) {
        List<PhoneNumber> list = repository.findByCustomer(customer);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum cliente cadastrado com esse numero");
        }
        return list;
    }

}
