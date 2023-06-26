package br.com.trier.spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.spring.services.ChampionshipService;
import br.com.trier.spring.services.TeamService;
import br.com.trier.spring.services.CountryService;
import br.com.trier.spring.services.UserService;
import br.com.trier.spring.services.impl.ChampionshipServiceImpl;
import br.com.trier.spring.services.impl.TeamServiceImpl;
import br.com.trier.spring.services.impl.CountryServiceImpl;
import br.com.trier.spring.services.impl.UserServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public CountryService paisService() {
		return new CountryServiceImpl();
	}
	
	@Bean
	public TeamService equipeService() {
		return new TeamServiceImpl();
	}
	
	@Bean
	public ChampionshipService campeonatoService() {
		return new ChampionshipServiceImpl();
	}

}
