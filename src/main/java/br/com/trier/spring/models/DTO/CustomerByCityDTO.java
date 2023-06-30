package br.com.trier.spring.models.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerByCityDTO {
    
    private String cityName;
    
    private String ufCity;
    
    private Integer sizeListCustomer;
    
    private List<CustomerDTO> listCustomer;

}
