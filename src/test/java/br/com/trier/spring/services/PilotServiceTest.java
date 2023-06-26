package br.com.trier.spring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.domain.Pilot;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class PilotServiceTest extends BaseTests{
	
	@Autowired
	PilotService service;
	
	@Autowired
	CountryService paisService;
	
	@Autowired
	TeamService equipeService;
	
	@Test
	@DisplayName("Teste inserir piloto")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/team.sql"})
	void insertTest() {
		Pilot piloto = new Pilot (1, "Piloto 1", paisService.findById(1), equipeService.findById(1));
		service.save(piloto);
		assertThat(piloto).isNotNull();
		assertEquals("Piloto 1", piloto.getName());
		
	}
	
	@Test
	@DisplayName("Teste buscar piloto por ID")
	@Sql({"classpath:/resources/sqls/team.sql"})
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	void findByIdTest() {
		var piloto = service.findById(1);
		assertEquals(1, piloto.getId());
	}
	
	@Test
	@DisplayName("Teste buscar piloto por ID inexistente")
	@Sql({"classpath:/resources/sqls/team.sql"})
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	void findByNonExistingIdTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.save(service.findById(2)));
		assertEquals("Piloto 2 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar piloto")
	@Sql({"classpath:/resources/sqls/team.sql"})
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	void updatePilotoTest() {
		Pilot piloto = new Pilot (1, "Piloto Teste", paisService.findById(1), equipeService.findById(1));
		service.update(piloto);
		assertThat(piloto).isNotNull();
		assertEquals("Piloto Teste", piloto.getName());
	}
	
	@Test
	@DisplayName("Teste deletar piloto")
	@Sql({"classpath:/resources/sqls/team.sql"})
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	void deleteTest() {
		service.delete(1);
		List<Pilot> list = service.listAll();
		assertEquals(0, list.size());	
	}

}
