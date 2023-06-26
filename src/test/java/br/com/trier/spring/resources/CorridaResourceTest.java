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
import br.com.trier.spring.domain.Race;
import br.com.trier.spring.domain.dto.RaceDTO;
import br.com.trier.spring.domain.dto.PilotDTO;
import br.com.trier.spring.services.ChampionshipService;
import br.com.trier.spring.services.SpeedwayService;
import br.com.trier.spring.utils.DateUtils;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/corrida.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CorridaResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	@Autowired
	SpeedwayService pistaService;
	@Autowired
	ChampionshipService campeonatoService;
	
	private ResponseEntity<PilotDTO> getPiloto(String url) {
		return rest.getForEntity(url, PilotDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<PilotDTO>> getPilotos(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<PilotDTO>>() {
		});
		
	}
	
	@Test
	@DisplayName("Buscar por id")
	public void testGetOk() {
		ResponseEntity<PilotDTO> response = getPiloto("/piloto/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		PilotDTO piloto = response.getBody();
		assertEquals(1, piloto.getId());
	}
	
	@Test
	@DisplayName("Cadastrar corrida")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void testCreateCorrida() {
		Race dto = new Race(1, DateUtils.strToZonedDateTime("21/08/2004"), pistaService.findById(1), campeonatoService.findById(1));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RaceDTO> requestEntity = new HttpEntity<>(dto.toDTO(), headers);
		ResponseEntity<RaceDTO> responseEntity = rest.exchange("/corrida", HttpMethod.POST, requestEntity, RaceDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		RaceDTO corrida = responseEntity.getBody();
		assertEquals(1, corrida.getId());
	}
	

}
