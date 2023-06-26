package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Race;
import br.com.trier.spring.domain.Pilot;
import br.com.trier.spring.domain.PilotRace;

public interface PilotRaceService {
	
	PilotRace insert (PilotRace pilotRaceDTO);
	List<PilotRace> listAll();
	PilotRace findById(Integer id);
	PilotRace update(PilotRace pilotRaceDTO);
	void delete(Integer id);
	List<PilotRace> findByPilot(Pilot pilot);
	List<PilotRace> findByRace(Race race);
	PilotRace findByPlacing(Integer placing);
	List<PilotRace> findByRaceOrderByPlacing(Race race);
	List<PilotRace> findByPilotAndRace(Pilot pilot, Race race);

}
