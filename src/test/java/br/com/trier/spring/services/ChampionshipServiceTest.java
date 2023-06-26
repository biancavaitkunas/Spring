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
import br.com.trier.spring.domain.Championship;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import jakarta.transaction.Transactional;

@Transactional
public class ChampionshipServiceTest extends BaseTests{
	
	@Autowired
	ChampionshipService service;
	
	@Test
	@DisplayName("Teste inserir campeonato")
	void insertChampionshipTest() {
		Championship championship = new Championship(null, "Campeonato 4", 2022);
		championship = service.insert(championship);
		championship = service.findById(1);
		assertThat(championship).isNotNull();
		assertEquals("Campeonato 4", championship.getDescription());
		assertEquals(2022, championship.getYear());
	}
	
	@Test
	@DisplayName("Teste listar campeonatos")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void listAllTest() {
		List<Championship> lista = service.listAll();
		assertThat(lista).isNotNull();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste listar campeonatos vazio")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum campeonato encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar campeonato")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void updateChampionshipTest() {
		var camp = new Championship(1, "Campeonato novo", 2015);
		service.update(camp);
		assertThat(camp).isNotNull();
		assertEquals(1, camp.getId());
		assertEquals(2015, camp.getYear());
		assertEquals("Campeonato novo", camp.getDescription());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void deleteChampionshipTest() {
		service.delete(2);
		List<Championship> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste validar ano")
	void validateYearTest() {
		Championship camp = new Championship(null, "Valida", 2026);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(camp));
		assertEquals("Ano 2026 é inválido para o campeonato", exception.getMessage());

	}
	
	@Test
	@DisplayName("Teste pesquisar por descrição começando com")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByDescriptionStartingWithTest() {
		List<Championship> lista = service.findByDescriptionStartingWithIgnoreCase("C");
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste pesquisar por descrição começando com não encontrado")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByDescriptionStartingWithNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByDescriptionStartingWithIgnoreCase("A"));
		assertEquals("Nenhum campeonato inicia com A", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste pesquisar por intervalo de ano")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void anoBetweenChampionshipTest() {
		List<Championship> lista = service.findByYearBetween(2000, 2018);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste pesquisar por intervalo de ano não encontrado")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void anoBetweenChampionshipNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByYearBetween(2010, 2011));
		assertEquals("Nenhum campeonato encontrado neste período", exception.getMessage());
	}
	

}
