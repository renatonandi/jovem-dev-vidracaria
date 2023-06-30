package br.com.trier.spring.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
	
	private Integer id;

	private String name;

	private Integer addressId;
	
	private String cityName;

	private Integer discountId;

}
