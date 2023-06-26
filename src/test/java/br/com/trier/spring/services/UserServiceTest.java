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
import br.com.trier.spring.domain.User;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests {
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste buscar usuário por id")
	@Sql({"classpath:/resources/sqls/usuario.sql"})//se quiser exec mais de um sql faz um vetor com os arquivos
	void findByIdTest() {
		var u = userService.findById(1);
		assertThat(u).isNotNull();
		assertEquals(1, u.getId());
		assertEquals("Pedro", u.getName());
		assertEquals("pedro@gmail.com", u.getEmail());
		assertEquals("pedro123", u.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar usuário por id inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNonExistingIdTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	
	@Test
	@DisplayName("Listar todos os usuários")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertThat(lista).isNotNull();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());

	}
	
	@Test
	@DisplayName("Teste inserir usuário")
	void insertUserTest() {
		User u = new User(null, "Bianca", "bianca@gmail.com", "bianca123");
		userService.salvar(u);
		u = userService.findById(1);
		assertThat(u).isNotNull();
		assertEquals(1, u.getId());
		assertEquals("Bianca", u.getName());
		assertEquals("bianca@gmail.com", u.getEmail());
		assertEquals("bianca123", u.getPassword());

	}
	
	@Test
	@DisplayName("Teste alterar usuários")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void alterUserTest() {
		var u = new User(1, "altera", "altera@gmail.com", "altera");
		userService.update(u);
		u = userService.findById(1);
		assertThat(u).isNotNull();
		assertEquals(1, u.getId());
		assertEquals("altera", u.getName());
		assertEquals("altera@gmail.com", u.getEmail());
		assertEquals("altera", u.getPassword());

	}
	
	@Test
	@DisplayName("Teste alterar usuário inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void alterUserNaoExisteTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.delete(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
	}
	
	
	@Test
	@DisplayName("Teste deletar usuário")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteUserTest() {
		userService.delete(1);
		List<User> lista = userService.listAll();
		assertEquals(1, lista.size());

	}
	
	@Test
	@DisplayName("Teste deletar usuário inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteNonExistingUserTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.delete(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());

	}
	
	@Test
	@DisplayName("Teste usuário por nome que inicia com")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findUserByNameStartsWithTest() {
		List<User> lista = userService.findByNameStartingWithIgnoreCase("L");
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste salvar usuário com email já existente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertExistingEmailTest() {
		var user = new User (3, "Novo Usuraio", "larissa@gmail.com", "456");
		var exception = assertThrows(IntegrityViolation.class, () -> userService.salvar(user));
		assertEquals("Email larissa@gmail.com já existe", exception.getMessage());
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
	}

}
