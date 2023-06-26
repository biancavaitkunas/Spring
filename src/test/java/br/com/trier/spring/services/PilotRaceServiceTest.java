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
import br.com.trier.spring.domain.PilotRace;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

public class PilotRaceServiceTest extends BaseTests{
	
	@Autowired
	PilotRaceService service;
	
	@Autowired
	PilotService pilotService;
	
	@Autowired
	RaceService raceService;
	
	@Test
	@DisplayName("Teste inserir piloto")
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	@Sql({"classpath:/resources/sqls/race.sql"})
	void insertTest() {
		PilotRace pilotRace = new PilotRace (1, 1, pilotService.findById(1), raceService.findById(1));
		service.insert(pilotRace);
		assertThat(pilotRace).isNotNull();
		assertEquals(1, pilotRace.getId());
		
	}
	
	@Test
	@DisplayName("Teste buscar piloto por ID")
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	@Sql({"classpath:/resources/sqls/race.sql"})
	@Sql({"classpath:/resources/sqls/pilotRace.sql"})
	void findByIdTest() {
		var piloto = service.findById(1);
		assertEquals(1, piloto.getId());
	}
	
	@Test
	@DisplayName("Teste buscar piloto por ID inexistente")
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	@Sql({"classpath:/resources/sqls/race.sql"})
	@Sql({"classpath:/resources/sqls/pilotRace.sql"})
	void findByNonExistingIdTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.insert(service.findById(1)));
		assertEquals("Piloto 1 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar piloto")
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	@Sql({"classpath:/resources/sqls/race.sql"})
	@Sql({"classpath:/resources/sqls/pilotRace.sql"})
	void updatePilotoTest() {
		PilotRace piloto = new PilotRace (1, 1, pilotService.findById(1), raceService.findById(1));
		service.update(piloto);
		assertThat(piloto).isNotNull();
		assertEquals(1, piloto.getId());
	}
	
	@Test
	@DisplayName("Teste deletar piloto")
	@Sql({"classpath:/resources/sqls/pilot.sql"})
	@Sql({"classpath:/resources/sqls/race.sql"})
	@Sql({"classpath:/resources/sqls/pilotRace.sql"})
	void deleteTest() {
		service.delete(1);
		List<PilotRace> list = service.listAll();
		assertEquals(0, list.size());	
	}

}


