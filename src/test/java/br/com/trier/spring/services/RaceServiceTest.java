package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.domain.Race;
import br.com.trier.spring.domain.Speedway;
import br.com.trier.spring.utils.DateUtils;
import jakarta.transaction.Transactional;

@Transactional
public class RaceServiceTest extends BaseTests{
	
	@Autowired
	RaceService service;
	@Autowired
	SpeedwayService speedwayService;
	@Autowired
	ChampionshipService championshipService;
	
	@Test
	@DisplayName("Teste buscar por ID")
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/race.sql"})
	void findByIdTest() {
		var corrida = service.findById(1);
		assertEquals(1, corrida.getId());
	}
	
	@Test
	@DisplayName("Teste inserir corrida")
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void insertTest() {
		Race race = new Race(1, DateUtils.strToZonedDateTime("21/08/2004"), speedwayService.findById(1), championshipService.findById(1));
		service.save(race);
		assertEquals(1, race.getId());
	}
	
	@Test
	@DisplayName("Teste buscar por data")
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByDateTest() {
		List<Race> lista = service.findByDate(DateUtils.strToZonedDateTime("21/08/2004"));
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar corrida")
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/race.sql"})
	void deleteTest() {
		service.delete(1);
		List<Race> list = service.listAll();
		assertEquals(1, list.size());	
	}

}
