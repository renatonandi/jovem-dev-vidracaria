package br.com.trier.spring.models.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductByTypeAndSizeDTO {
    
    private String descriptionType;
    
    private Integer productSize;
    
    private List<SaleDTO> products;

}
