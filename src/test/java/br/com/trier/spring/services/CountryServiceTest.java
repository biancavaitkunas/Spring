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
import br.com.trier.spring.domain.Country;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import jakarta.transaction.Transactional;

@Transactional
public class CountryServiceTest extends BaseTests{
	
	@Autowired
	CountryService service;
	
	@Test
	@DisplayName("Teste buscar por ID")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByIdTest() {
		var pais = service.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());
		
	}
	
	@Test
	@DisplayName("Teste buscar país por ID inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNonExistingIdTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("País 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir país")
	void insertPaisTest() {
		Country pais = new Country(null, "Croácia");
		pais = service.insert(pais);
		service.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Croácia", pais.getName());
	}
	
	@Test
	@DisplayName("Teste inserir país que já existe ")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void insertExistingPaisTest() {
		Country pais = new Country(null, "Brasil");
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(pais));
		assertEquals("País Brasil já existe", exception.getMessage());
		List<Country>lista = service.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste listar país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void listPaisTest() {
		List<Country> lista = service.listAll();
		assertThat(lista).isNotNull();
		assertEquals(3, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste alterar país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void alterPaisTest() {
		var pais = new Country(1, "Argentina");
		service.update(pais);
		pais = service.findById(1);
		assertEquals("Argentina", pais.getName());
	}
	
	@Test
	@DisplayName("Teste alterar país duplicado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void alterExistingPaisTest() {
		var pais = new Country(2, "Brasil");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(pais));
		assertEquals("País Brasil já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void deletePaisTest() {
		service.delete(1);
		List<Country> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar país que não existe")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void deleteNonExistingPaisTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("País 10 não encontrado", exception.getMessage());
		List<Country> lista = service.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste procurar país que começa com")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findNameStartingWithTest() {
		List<Country> list = service.findByNameStartingWithIgnoreCase("E");
		assertEquals(1, list.size());
		assertEquals(2, list.get(0).getId());
	}

}
