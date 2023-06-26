package br.com.trier.spring.services;

import java.util.List;
import br.com.trier.spring.domain.Country;

public interface CountryService {
	
	Country insert (Country country);
	List<Country> listAll();
	Country findById(Integer id);
	Country update(Country country);
	void delete(Integer id);
	List <Country> findByNameStartingWithIgnoreCase(String name);

}
