package br.com.trier.spring.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
	
	private Integer id;

	private Double size;

	private Integer requestId;
	
	private String requestDescription;

	private Integer customerId;
	
	private String customerName;
	
	private Integer productId;
	
	private String nameProduct;
	
}
