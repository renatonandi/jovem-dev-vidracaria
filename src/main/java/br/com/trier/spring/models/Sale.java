package br.com.trier.spring.models;

import br.com.trier.spring.models.DTO.SaleDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity(name = "produto_pedido")
public class Sale {
    
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto_pedido")
    private Integer id;
    
    @Column(name = "tamanho_produto")
    private Double size;
    
    @ManyToOne
    private Request request;
    
    @ManyToOne
    private Product product;
    
    
    public Sale(SaleDTO dto, Request request, Product product) {
    	this(dto.getId(), dto.getSize(), request, product);
    }
    
    public SaleDTO toDTO() {
    	return new SaleDTO(id, size, request.getId(), request.getDescription(), request.getCustomer().getId(), request.getCustomer().getName(), product.getId());
    }
    
}
