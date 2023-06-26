package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Country;
import br.com.trier.spring.domain.Speedway;

public interface SpeedwayService {
	
	Speedway insert (Speedway speedway);
	List<Speedway> listAll();
	Speedway findById(Integer id);
	Speedway update(Speedway speedway);
	void delete(Integer id);
	List<Speedway> findByName(String name);
	List<Speedway> findBySizeBetween(Integer inicialSize, Integer finalSize);
	List <Speedway> findByCountryOrderBySizeDesc(Country country);

}
