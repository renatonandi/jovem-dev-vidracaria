package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.Buy;
import br.com.trier.spring.models.ProductRequest;

public interface BuyService {

    Buy findById(Integer id);

    Buy insert(Buy buy);

    Buy update(Buy buy);

    void delete(Integer id);

    List<Buy> listAll();

    List<Buy> findByAmount(Double amount);

    List<Buy> findByAmountBetween(Double firstAmount, Double lastAmount);

    List<Buy> findByProductRequestContaining(ProductRequest productRequest);

}
