package br.com.trier.spring.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import java.util.List;
import java.security.Key;

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
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.spring.Application;
import br.com.trier.spring.config.jwt.LoginDTO;
import br.com.trier.spring.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/usuario.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;//possui prot http, cria estrutura de pagina
	
	/*String chaveSecreta = "oratoroeuaroupadoreideromaarainhamatouorato";

	private String gerarTokenJwt() {
		Key chaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	    Date agora = new Date();
	    Date expiracao = new Date(agora.getTime() + 3600000);
	    
	    String token = Jwts.builder()
	            .setSubject("Email")
	            .setIssuedAt(agora)
	            .setExpiration(expiracao)
	            .signWith(SignatureAlgorithm.HS512, chaveSecreta)
	            .compact();
	    
	    return token;
	}*/
	
	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity, String.class);
		
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}

	private ResponseEntity<UserDTO> getUser(String url) {//converte p dto - n é necessario poderia adicionar o rest.getForEntity(url, UserDTO.class);
		return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders("pedro@gmail.com", "pedro123")), UserDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(
				url, HttpMethod.GET, 
				new HttpEntity<>(getHeaders("pedro@gmail.com", "pedro123")), 
				new ParameterizedTypeReference<List<UserDTO>>() {}
			);
		
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/usuario/3");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();//corpo da requisicao, oq aparece p usuario
		assertEquals("Pedro", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuario/6");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

	}
	
	@Test
	@DisplayName("Buscar por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void findByNameTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuario/nome/pedro");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}
	
	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "Nome", "Email", "Senha", "ADMIN");
		HttpHeaders headers = getHeaders("pedro@gmail.com", "pedro123");//se logar em um usuario com acesso
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuario", HttpMethod.POST, requestEntity, UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("Nome", user.getName());
	}
	
	@Test
	@DisplayName("Alterar usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testAlterUser() {
		UserDTO dto = new UserDTO(null, "Nome", "Email", "Senha", "ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> response = this.rest.exchange("/usuario/1", HttpMethod.PUT, requestEntity, UserDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals(1, user.getId());
		assertEquals("Nome", user.getName());
	}
	
	@Test
	@DisplayName("Excluir usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testDeleteUser() {
		HttpHeaders headers = getHeaders("pedro@gmail.com", "pedro123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/users/4", 
				HttpMethod.DELETE,  
				requestEntity, 
				Void.class
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Excluir usuário inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testDeleteNonExistUser() {
		HttpHeaders headers = getHeaders("pedro@gmail.com", "pedro123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/users/100", 
				HttpMethod.DELETE,  
				requestEntity, 
				Void.class
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}
