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
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.spring.Application;
import br.com.trier.spring.domain.Pilot;
import br.com.trier.spring.domain.dto.RaceDTO;
import br.com.trier.spring.domain.dto.PilotDTO;
import br.com.trier.spring.services.TeamService;
import br.com.trier.spring.services.CountryService;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/piloto.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PilotoResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	@Autowired
	CountryService paisService;
	@Autowired
	TeamService equipeService;

	
	private ResponseEntity<RaceDTO> getCorrida(String url) {
		return rest.getForEntity(url, RaceDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<RaceDTO>> getCorridas(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RaceDTO>>() {
		});
		
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	public void testGetOk() {
		ResponseEntity<RaceDTO> response = getCorrida("/corrida/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		RaceDTO corrida = response.getBody();
		assertEquals(1, corrida.getId());
	}
	
	@Test
	@DisplayName("Cadastrar piloto")
	
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	public void testCreateCorrida() {
		Pilot dto = new Pilot(1, "Piloto 1", paisService.findById(1), equipeService.findById(1));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PilotDTO> requestEntity = new HttpEntity<>(dto.ToDTO(), headers);
		ResponseEntity<PilotDTO> responseEntity = rest.exchange("/piloto", HttpMethod.POST, requestEntity, PilotDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		PilotDTO piloto = responseEntity.getBody();
		assertEquals(1, piloto.getId());
	}
	
	@Test
	@DisplayName("Alterar piloto")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	public void testAlterPiloto() {
		Pilot dto = new Pilot(1, "Piloto Novo", paisService.findById(1), equipeService.findById(1));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PilotDTO> requestEntity = new HttpEntity<>(dto.ToDTO(), headers);
		ResponseEntity<PilotDTO> response = this.rest.exchange("/piloto/1", HttpMethod.PUT, requestEntity, PilotDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		PilotDTO piloto = response.getBody();
		assertEquals(1, piloto.getId());
		assertEquals("Piloto Novo", piloto.getName());
	}
	
	@Test
	@DisplayName("Deletar piloto")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void testDeletePiloto() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PilotDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Void> responseEntity = this.rest.exchange("/piloto/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

}
