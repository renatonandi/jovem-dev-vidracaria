package br.com.trier.spring.models;

import br.com.trier.spring.models.DTO.CustomerDTO;
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
@Entity(name = "cliente")
public class Customer {
    
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_cliente")
    private Integer id;
    
    @NotNull
    @Column(name = "nome_cliente")
    private String name;
    
    @ManyToOne
    private Address address;
    
    @ManyToOne
    private Discount discount;
    
    public Customer(CustomerDTO dto, Address address, Discount discount) {
    	this(dto.getId(), dto.getName(), address, discount);
    }
    
    public CustomerDTO toDTO() {
    	return new CustomerDTO(id, name, address.getId(), address.getCity().getName(), discount.getId());
    }

}
