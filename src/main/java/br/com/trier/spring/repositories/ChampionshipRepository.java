package br.com.trier.spring.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Championship;
import br.com.trier.spring.domain.User;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Integer>{

	List <Championship> findByDescriptionStartingWithIgnoreCase(String description);
	List<Championship> findByYear(Integer year);
	List<Championship> findByDescriptionContainingIgnoreCase(String description);
	List<Championship> findByYearBetween(Integer inicialYear, Integer finalYear);

}