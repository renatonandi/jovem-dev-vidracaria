package br.com.trier.spring.models;

import br.com.trier.spring.models.DTO.RequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "pedido")
public class Request {
    
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;
    
    @NotNull
    @Column(name = "descricao_pedido")
    private String description;
    
    @ManyToOne
    private Customer customer;
    
    
    public Request(RequestDTO dto, Customer customer) {
    	this(dto.getId(), dto.getDescription(), customer);
    }
    
    public RequestDTO toDTO() {
    	return new RequestDTO(id, description, customer.getId(), customer.getName());
    }

}
