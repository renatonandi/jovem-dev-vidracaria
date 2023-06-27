package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Discount;

public interface DiscountService {
    
    Discount findById(Integer id);

    Discount insert(Discount discount);

    Discount update(Discount discount);

    void delete(Integer id);

    List<Discount> listAll();
    
    List<Discount> findByDescription(String description);

}
