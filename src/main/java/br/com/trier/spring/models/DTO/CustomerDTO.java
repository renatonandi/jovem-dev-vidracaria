package br.com.trier.spring.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerDTO {
	
	private Integer id;

	private String name;

	private Integer addressId;
	
	private String cityName;

	private Integer discountId;

}
