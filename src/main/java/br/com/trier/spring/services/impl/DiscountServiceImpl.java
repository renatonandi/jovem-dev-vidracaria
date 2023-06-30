package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Discount;
import br.com.trier.spring.repositories.DiscountRepository;
import br.com.trier.spring.services.DiscountService;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class DiscountServiceImpl implements DiscountService{
    
    @Autowired
    private DiscountRepository repository;
    
    public void validateValue(Discount discount) {
    	if (discount.getDiscount() <= 0) {
			throw new IntegrityViolation("Desconto inválido. Valor não pode ser menor ou igual a zero");
		}
    }

    @Override
    public Discount findById(Integer id) {
        Optional<Discount> discount = repository.findById(id);
        return discount.orElseThrow(() -> new ObjectNotFound("O desconto %s não existe".formatted(id)));
    }

    @Override
    public Discount insert(Discount discount) {
    	validateValue(discount);
        return repository.save(discount);
    }

    @Override
    public Discount update(Discount discount) {
        findById(discount.getId());
        validateValue(discount);
        return repository.save(discount);
    }

    @Override
    public void delete(Integer id) {
        Discount discount = findById(id);
        if (discount != null) {
            repository.delete(discount);
        }
    }

    @Override
    public List<Discount> listAll() {
        List<Discount> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum desconto cadastrado");
        }
        return list;
    }

    @Override
    public List<Discount> findByDiscount(Integer discount) {
        List<Discount> list = repository.findByDiscount(discount);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhum desconto encontrado para esse valor %s".formatted(discount));
        }
        return list;
    }

}
