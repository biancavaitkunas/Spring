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
import br.com.trier.spring.domain.Speedway;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class SpeedwayServiceTest extends BaseTests{
	
	@Autowired
	SpeedwayService service;
	
	@Autowired
	CountryService countryService;
	
	@Test
	@DisplayName("Teste inserir pista")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void insertSpeedwayTest() {
		Speedway speedway = new Speedway(1, "Pista Teste", 2000, countryService.findById(1));
		service.insert(speedway);
		assertThat(speedway).isNotNull();
		assertEquals(2000, speedway.getSize());
		
	}
	
	@Test
	@DisplayName("Teste buscar po ID")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	void findByIdTest() {
		var speedway = service.findById(1);
		assertEquals(1, speedway.getId());
		assertEquals("Pista 1", speedway.getName());
		assertEquals("Brasil", speedway.getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste buscar pista por ID inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	void findByNonExistingIdTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.insert(service.findById(3)));
		assertEquals("Pista 3 não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar pista")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	void updatePilotoTest() {
		Speedway speedway = new Speedway (1, "Pista Teste", 4000, countryService.findById(1));
		service.update(speedway);
		assertThat(speedway).isNotNull();
		assertEquals("Pista Teste", speedway.getName());
	}
	
	@Test
	@DisplayName("Teste deletar piloto")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	void deleteTest() {
		service.delete(1);
		List<Speedway> list = service.listAll();
		assertEquals(1, list.size());	
	}
	
	@Test
	@DisplayName("Teste buscar pista por tamanho")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	void findBySizeTest() {
		List<Speedway> lista = service.findBySizeBetween(1000, 2700);
		assertThat(lista).isNotNull();
		assertEquals(1, lista.size());
		
	}

}
