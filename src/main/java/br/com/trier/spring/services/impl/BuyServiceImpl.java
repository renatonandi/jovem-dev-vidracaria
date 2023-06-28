package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.models.Buy;
import br.com.trier.spring.models.ProductRequest;
import br.com.trier.spring.repositories.BuyRepository;
import br.com.trier.spring.services.BuyService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@Service
public class BuyServiceImpl implements BuyService{

    @Autowired
    private BuyRepository repository;
    
    
    @Override
    public Buy findById(Integer id) {
        Optional<Buy> buy = repository.findById(id);
        return buy.orElseThrow(() -> new ObjectNotFound("A compra %s não existe".formatted(id)));
    }

    @Override
    public Buy insert(Buy buy) {
        return repository.save(buy);
    }

    @Override
    public Buy update(Buy buy) {
        findById(buy.getId());
        return repository.save(buy);
    }

    @Override
    public void delete(Integer id) {
        Buy buy = findById(id);
        if (buy != null) {
            repository.delete(buy);
        }
    }

    @Override
    public List<Buy> listAll() {
        List<Buy> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma compra cadastrada");
        }
        return list;
    }

    @Override
    public List<Buy> findByAmount(Double amount) {
        List<Buy> list = repository.findByAmount(amount);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma compra encontrada para esse valor %s".formatted(amount));
        }
        return list;
    }

    @Override
    public List<Buy> findByAmountBetween(Double firstAmount, Double lastAmount) {
        List<Buy> list = repository.findByAmountBetween(firstAmount, lastAmount);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma compra encotrada para os valores entre %s e %s".formatted(firstAmount, lastAmount));
        }
        return list;
    }

    @Override
    public List<Buy> findByProductRequestContaining(ProductRequest productRequest) {
        List<Buy> list = repository.findByProductRequestContaining(productRequest);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma compra encontrada para o pedido %s e para o produto %s".formatted(productRequest.getRequest().getId(), productRequest.getProduct().getName()));
        }
        return list;
    }
}
