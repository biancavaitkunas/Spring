package br.com.trier.spring.services.impl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Country;
import br.com.trier.spring.repositories.CountryRepository;
import br.com.trier.spring.services.CountryService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;

@Service
public class CountryServiceImpl implements CountryService{
	
	@Autowired
	CountryRepository repository;

	@Override
	public Country insert(Country country) {
		findByName(country);
		return repository.save(country);
	}

	@Override
	public List<Country> listAll() {
		return repository.findAll();
	}

	@Override
	public Country findById(Integer id) {
		Optional<Country> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFound("País %s não encontrado".formatted(id)));
	}

	@Override
	public Country update(Country country) {
		findByName(country);
		return repository.save(country);
	}

	@Override
	public void delete(Integer id) {
		Country country = findById(id);
		if (country != null) {
			repository.delete(country);
		}
	}
	
	private void findByName(Country obj) {
		Country country = repository.findByName(obj.getName());
		if (country != null && country.getId() != obj.getId()) {
			throw new IntegrityViolation("País %s já existe".formatted(obj.getName()));
		}
	}
	
	@Override
	public List<Country> findByNameStartingWithIgnoreCase(String name) {
		List<Country> list = repository.findByNameStartingWithIgnoreCase(name);
		if (list.size() == 0) {
			throw new ObjectNotFound("Não há país que começe com a letra %s".formatted(list));
		}
		return list;
	}

}
