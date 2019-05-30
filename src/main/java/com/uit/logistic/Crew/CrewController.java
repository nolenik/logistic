package com.uit.logistic.Crew;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uit.logistic.Exception.ObjectNotFoundException;
import com.uit.logistic.Human.Human;
import com.uit.logistic.Human.HumanRepository;

@RestController
@RequestMapping(path = "/crew")
public class CrewController {
    @Autowired
    CrewRepository crewRepository;
    @Autowired
    HumanRepository humanRepository;
    
  //Getting all crews (Функція повертає всі екіпажі в базі даних)
	@GetMapping("/")
	public ResponseEntity<List<Crew>> getAll() {
		List<Crew> list = crewRepository.findAll();
		return new ResponseEntity<List<Crew>>(list,HttpStatus.OK);
	}
	
	//Getting crew with id (Функція повертає екіпаж з конкретним id)
	@GetMapping("/{id}")
	public ResponseEntity<Crew> getById(@PathVariable int id) {
		if (!crewRepository.existsById(id))
            throw new ObjectNotFoundException("Crew with id="+id+" not found");
		Crew crew = crewRepository.findById(id).get();
		return new ResponseEntity<Crew>(crew, HttpStatus.OK);
	}
	
	//Adding crew to table (Функція створює новий екіпаж)
	@PostMapping("/")
	public ResponseEntity<Crew> addCrew(@RequestBody List<Integer> list) {
		Set<Human> hum = new HashSet<>();
		for (Integer i : list)
			hum.add(humanRepository.findById(i.intValue()).get());
		Crew crew = new Crew (hum);
		crewRepository.save(crew);
		return new ResponseEntity<Crew>(crew, HttpStatus.CREATED);
	}
	
	//Editing crew with id (Зміна характеристик рейсу з конкретним id)
	@PutMapping("/{id}")
	public ResponseEntity<Crew> editCrew(@RequestBody List<Integer> list,
			@PathVariable int id) {
		if (!crewRepository.existsById(id))
            throw new ObjectNotFoundException("Crew with id="+id+" not found");
		Crew tmp = crewRepository.getOne(id);
		Set<Human> hum = new HashSet<>();
		for (Integer i : list)
			hum.add(humanRepository.findById(i.intValue()).get());
		tmp.setHumans(hum);
		crewRepository.save(tmp);
		return new ResponseEntity<Crew>(tmp, HttpStatus.OK);
	}
	
	 //Deleting crew with id (Видалення рейсу з таблиці з конкретним id)
	@DeleteMapping("/{id}")
	public ResponseEntity<Crew> deleteCrew(@PathVariable int id) {
		if (!crewRepository.existsById(id))
            throw new ObjectNotFoundException("Crew with id="+id+" not found");
		crewRepository.deleteById(id);
		return new ResponseEntity<Crew>(HttpStatus.NO_CONTENT);
	}
}
