package br.com.trier.spring.models;

import br.com.trier.spring.models.DTO.PhoneNumberDTO;
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
@Entity(name = "numero_telefone")
public class PhoneNumber {

	@Id
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_numero_telefone")
	private Integer id;

	@NotNull
	@Column(name = "numero", unique = true)
	private String number;

	@ManyToOne
	private Customer customer;
	

	public String formatedNumber(String number) {
		String onlyDigits = number.replaceAll("[^0-9]", "");
		String cod = onlyDigits.substring(0, 2);
		String prefix = onlyDigits.substring(2, 7);
		String sulfix = onlyDigits.substring(7);
		String formatedNumber = String.format("(%s) %s-%s".formatted(cod, prefix, sulfix));
		this.number = formatedNumber;
		return this.number;
	}
	
	
	public PhoneNumber(PhoneNumberDTO dto, Customer customer) {
		this(dto.getId(), dto.getNumber(), customer);
	}
	
	public PhoneNumberDTO toDTO() {
		return new PhoneNumberDTO(id, number, customer.getId(), customer.getName());
	}

}
