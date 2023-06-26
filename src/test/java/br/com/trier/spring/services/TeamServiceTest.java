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
import br.com.trier.spring.domain.Team;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import jakarta.transaction.Transactional;

@Transactional
public class TeamServiceTest extends BaseTests{
	
	@Autowired
	TeamService service;
		
	
	@Test
	@DisplayName("Teste inserir equipe")
	void insertEquipeTest() {
		Team team = new Team(1, "Equipe 4");
		service.insert(team);
		team = service.findById(1);
		assertThat(service).isNotNull();	
		assertEquals(1, team.getId());
		assertEquals("Equipe 4", team.getName());
	}
	
	@Test
	@DisplayName("Teste inserir equipe que já existe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void insertExistingEquipeTest() {
		Team team = new Team(null, "Equipe 1");
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(team));
		assertEquals("Equipe Equipe 1 já existe", exception.getMessage());
		List<Team> list = service.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste listar equipes")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void listAllTest() {
		List<Team> list = service.listAll();
		assertThat(service).isNotNull();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste encontrar equipe por id")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void findByIdTest() {
		var team = service.findById(1);
		assertThat(team).isNotNull();
		assertEquals("Equipe 1", team.getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe por ID inexistente")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void findByNonExistingIdTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Equipe 10 não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar equipe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void updateEquipeTest() {
		var team = new Team(2, "Equipe 5");
		service.update(team);
		assertEquals(2, team.getId());
		assertEquals("Equipe 5", team.getName());
	}
	
	@Test
	@DisplayName("Teste deletar equipe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void deleteEquipeTest() {
		service.delete(1);
		List<Team> list = service.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar equipe que não existe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void deleteNonExistingEquipeTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("Equipe 10 não encontrada", exception.getMessage());
		List<Team> list = service.listAll();
		assertEquals(3, list.size());
		
	}
	
	@Test
	@DisplayName("Teste alterar equipe que já existe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void alterExistingEquipeTest() {
		Team team = new Team(1, "Equipe 2");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(team));
		assertEquals("Equipe Equipe 2 já existe", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste buscar equipe que começa com")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void findEquipeStartingWithTest() {
		List <Team> list = service.findByNameStartingWithIgnoreCase("E");
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar equipe que começa com ... que não existe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void findEquipeStartingWithNonExistingTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameStartingWithIgnoreCase("A"));
		assertEquals("Não há equipes que começem com a letra A", exception.getMessage());
		
	}
}
