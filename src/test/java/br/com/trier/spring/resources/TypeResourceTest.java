package br.com.trier.spring.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.Application;
import br.com.trier.spring.config.jwt.LoginDTO;
import br.com.trier.spring.models.Type;
import br.com.trier.spring.models.DTO.UserDTO;
import br.com.trier.spring.resources.exceptions.StandardError;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TypeResourceTest {

	@Autowired
	private TestRestTemplate rest;

	@Test
	@DisplayName("Obter Token")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void getToken() {
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<UserDTO>> response = rest.exchange("/usuarios", HttpMethod.GET,
				new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<UserDTO>>() {
				});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
    @DisplayName("Cadastrar tipo")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
    public void insertTypeTest() {
        Type type = new Type(null, "Bronze");
        LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
                String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        String token = responseEntity.getBody();
        System.out.println("****************" + token);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        ResponseEntity<Type> response = rest.exchange("/tipo", HttpMethod.POST, new HttpEntity<>(type, headers),
                Type.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getDescription(), "Bronze");
    }

	@Test
	@DisplayName("Cadastrar tipo com descrição duplicada")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void insertTypeDuplicatedDescriptionTest() {
		Type type = new Type(null, "Bronze");
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<Type> response = rest.exchange("/tipo", HttpMethod.POST, new HttpEntity<>(type, headers),
				Type.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
    @DisplayName("Alterar tipo")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
    public void updateTypeTest() {
        Type type = new Type(1, "BronzeNovo");
        LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
                String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        String token = responseEntity.getBody();
        System.out.println("****************" + token);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        ResponseEntity<Type> response = rest.exchange("/tipo/1", HttpMethod.PUT, new HttpEntity<>(type, headers),
        		Type.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getDescription(), "BronzeNovo");
    }

	@Test
	@DisplayName("Alterar tipo passando descrição duplicada")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void updateTypeDuplicatedDescriptionTest() {
		Type type = new Type(2, "Bronze");
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<Type> response = rest.exchange("/tipo/2", HttpMethod.PUT, new HttpEntity<>(type, headers),
				Type.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	@DisplayName("Alterar tipo inexistente")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void updateTypeNonExistTest() {
		Type type = new Type(10, "Bronze");
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<Type> response = rest.exchange("/tipo/10", HttpMethod.PUT, new HttpEntity<>(type, headers),
				Type.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
    @DisplayName("Deletar tipo")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
    public void deleteTypeTest() {
        LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
                String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        String token = responseEntity.getBody();
        System.out.println("****************" + token);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        ResponseEntity<Type> response = rest.exchange("/tipo/1", HttpMethod.DELETE, new HttpEntity<>(null, headers),
        		Type.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

	@Test
	@DisplayName("Deletar tipo inexistente")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void deleteTypeNonExistTest() {
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<Type> response = rest.exchange("/usuarios/10", HttpMethod.DELETE, new HttpEntity<>(null, headers),
				Type.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Listar todos")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void listAllTest() {
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<Type>> response = rest.exchange("/tipo", HttpMethod.GET, new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<List<Type>>() {
				});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().size(), 3);
		assertEquals(response.getBody().get(0).getDescription(), "Bronze");
	}

	@Test
	@DisplayName("Buscar por id")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void findByIdTest() {
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<Type> response = rest.exchange("/tipo/1", HttpMethod.GET, new HttpEntity<>(null, headers),
				Type.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getDescription(), "Bronze");
	}
	
	@Test
    @DisplayName("Buscar por id inexistente")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
    public void findByIdNonExistTest() {
        LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
                String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        String token = responseEntity.getBody();
        System.out.println("****************" + token);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        ResponseEntity<Type> response = rest.exchange("/tipo/10", HttpMethod.GET,
                new HttpEntity<>(null, headers), Type.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
	
	@Test
	@DisplayName("Buscar por descrição")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void findByDescriptionTest() {
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<Type>> response = rest.exchange("/tipo/descricao/bronze", HttpMethod.GET, new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<List<Type>>() {
				});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().size(), 1);
		assertEquals(response.getBody().get(0).getDescription(), "Bronze");
	}

	@Test
	@DisplayName("Buscar por descrição incorreta")
	@Sql({ "classpath:/resources/sqls/clear_table.sql" })
	@Sql({ "classpath:/resources/sqls/type.sql" })
	@Sql({ "classpath:/resources/sqls/user.sql" })
	public void findByDescriptionWrongTest() {
		LoginDTO loginDTO = new LoginDTO("email1@gmail.com", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<StandardError> response = rest.exchange("/tipo/descricao/W", HttpMethod.GET, new HttpEntity<>(null, headers),
				StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	

}
