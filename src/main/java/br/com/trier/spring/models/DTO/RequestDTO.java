package br.com.trier.spring.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestDTO {
	
	private Integer id;

	private String description;

	private Integer customerId;
	
	private String customerName;

}
