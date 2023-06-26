package br.com.trier.spring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Race;
import br.com.trier.spring.domain.Pilot;
import br.com.trier.spring.domain.PilotRace;
import br.com.trier.spring.repositories.PilotRaceRepository;
import br.com.trier.spring.services.PilotRaceService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;

@Service
public class PilotRaceServiceImpl implements PilotRaceService{
	
	@Autowired
	PilotRaceRepository repository;
	
	private void checkPilotRace(PilotRace pilotoCorrida) {
		if(pilotoCorrida.getPilot() == null) {
			throw new IntegrityViolation("Piloto não pode ser nulo");
		}
		if(pilotoCorrida.getRace() == null) {
			throw new IntegrityViolation("Corrida não pode ser nula");
		}
		if(pilotoCorrida.getPlacing() == null) {
			throw new IntegrityViolation("Colocação não pode ser nula");
		}
		
	}

	@Override
	public PilotRace insert(PilotRace pilotoCorrida) {
		checkPilotRace(pilotoCorrida);
		return repository.save(pilotoCorrida);
	}

	@Override
	public List<PilotRace> listAll() {
		return repository.findAll();
	}

	@Override
	public PilotRace findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("PilotoCorrida %s não encontrado".formatted(id)));
	}

	@Override
	public PilotRace update(PilotRace pilotoCorrida) {
		findById(pilotoCorrida.getId());
		checkPilotRace(pilotoCorrida);
		return repository.save(pilotoCorrida);
	}

	@Override
	public void delete(Integer id) {
		PilotRace pilotoCorrida = findById(id);
		repository.delete(pilotoCorrida);
	}

	@Override
	public List<PilotRace> findByPilot(Pilot piloto) {
		if (repository.findByPilot(piloto).size() == 0) {
			throw new ObjectNotFound("Nenhum PilotoCorrida para o piloto %s".formatted(piloto));
		}
		return repository.findByPilot(piloto);
	}

	@Override
	public List<PilotRace> findByRace(Race corrida) {
		if (repository.findByRace(corrida).size() == 0) {
			throw new ObjectNotFound("Nenhum PilotoCorrida para a corrida %s".formatted(corrida));
		}
		return repository.findByRace(corrida);

	}

	@Override
	public PilotRace findByPlacing(Integer placing) {
		return repository.findByPlacing(placing);
	}


	@Override
	public List<PilotRace> findByRaceOrderByPlacing(Race corrida) {
		if (repository.findByRaceOrderByPlacing(corrida).size() == 0) {
			throw new ObjectNotFound("Nenhum PilotoCorrida para a corrida %s".formatted(corrida));
		}
		return repository.findByRace(corrida);
	}

	@Override
	public List<PilotRace> findByPilotAndRace(Pilot piloto, Race corrida) {
		if (repository.findByPilotAndRace(piloto, corrida).size() == 0) {
			throw new ObjectNotFound("Nenhum PilotoCorrida para o piloto e corrida requeridos");
		}
		return repository.findByPilotAndRace(piloto, corrida);
	}

	

}
