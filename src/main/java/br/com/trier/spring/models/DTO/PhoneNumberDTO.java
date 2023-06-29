package br.com.trier.spring.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PhoneNumberDTO {

	private Integer id;

	private String number;

	private Integer customerId;

	private String customerName;


}
