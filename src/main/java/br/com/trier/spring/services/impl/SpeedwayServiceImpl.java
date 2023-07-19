package br.com.trier.spring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Country;
import br.com.trier.spring.domain.Speedway;
import br.com.trier.spring.repositories.SpeedwayRepository;
import br.com.trier.spring.services.SpeedwayService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;

@Service
public class SpeedwayServiceImpl implements SpeedwayService{
	
	@Autowired
	SpeedwayRepository repository;
	
	private void validatePista(Speedway speedway) {
		if (speedway == null) {
			throw new IntegrityViolation("Pista não pode ser nula");
		}
		if (speedway.getCountry() == null) {
			throw new IntegrityViolation("País não pode ser nula");
		}
		
		if(speedway.getSize() == null || speedway.getSize() <= 0) {
			throw new IntegrityViolation("Tamanho inválido");
		}
	}

	@Override
	public Speedway insert(Speedway pista) {
		validatePista(pista);
		return repository.save(pista); 
	}

	@Override
	public List<Speedway> listAll() {
		return repository.findAll();
	}

	@Override
	public Speedway findById(Integer id) {
		return repository.findById(id).orElseThrow(()-> new ObjectNotFound("Pista %s não encontrada".formatted(id)));
	}

	@Override
	public Speedway update(Speedway speedway) {
		findById(speedway.getId());
		validatePista(speedway);
		return repository.save(speedway);
	}

	@Override
	public void delete(Integer id) {
		Speedway pista = findById(id);
		repository.delete(pista);
	}

	@Override
	public List<Speedway> findBySizeBetween(Integer inicialSize, Integer finalSize) {
		List<Speedway> lista = repository.findBySizeBetween(inicialSize, finalSize);
		if(lista.size() == 0) {
			throw new ObjectNotFound("Nenhuma pista com os tamanhos informados");
		}
		return lista;
	}

	@Override
	public List<Speedway> findByCountryOrderBySizeDesc(Country idCountry) {
		List<Speedway> lista = repository.findByCountryOrderBySizeDesc(idCountry);
		if(lista.size() == 0) {
			throw new ObjectNotFound("Nenhuma pista no país".formatted(idCountry));
		}
		return lista;
	}

	@Override
	public List<Speedway> findByName(String name) {
		List<Speedway> list = repository.findByName(name);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhuma pista %s encontrada".formatted(name));
		}
		return list;
	}

}
