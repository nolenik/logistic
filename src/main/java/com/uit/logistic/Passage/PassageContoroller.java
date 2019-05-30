package com.uit.logistic.Passage;

import java.util.List;


import javax.validation.Valid;

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

import com.uit.logistic.Car.Car;
import com.uit.logistic.Car.CarRepository;
import com.uit.logistic.Client.Client;
import com.uit.logistic.Client.ClientRepository;
import com.uit.logistic.Crew.Crew;
import com.uit.logistic.Crew.CrewRepository;
import com.uit.logistic.Exception.ObjectNotFoundException;



@RestController
@RequestMapping(path = "/passage")
public class PassageContoroller {


	@Autowired
	PassageRepository passageRepository;
	@Autowired
	CrewRepository crewRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	CarRepository carRepository;
	
	//Getting all passages (Функція повертає всі рейси в базі даних)
	@GetMapping("/")
	public ResponseEntity<List<Passage>> getAll() {
		List<Passage> list = passageRepository.findAll();
		return new ResponseEntity<List<Passage>>(list, HttpStatus.OK);
	}
	
	//Getting passage with id (Функція повертає рейс з конкретним id)
	@GetMapping("/{id}")
	public ResponseEntity<Passage> getById(@PathVariable int id) {
		if (!passageRepository.existsById(id))
            throw new ObjectNotFoundException("Passage with id="+id+" not found");
		Passage pass = passageRepository.findById(id).get();
		return new ResponseEntity<Passage>(pass, HttpStatus.OK);
	}
	
	//Adding passage to table (Функція створює новий рейс)
	@PostMapping("/")
	public ResponseEntity<Passage> addPassage(@Valid @RequestBody Passage passage) {
		passage.setCar(carRepository.findById( passage.getCar().getId() ).get());
		passage.setClient(clientRepository.findById( passage.getClient().getId() ).get());
		passage.setCrew(crewRepository.findById( passage.getCrew().getId() ).get());
		passageRepository.save(passage);
		return new ResponseEntity<Passage>(passage,
				HttpStatus.CREATED);
	}
	
	//Editing passage with id (Зміна характеристик рейсу з конкретним id)
	@PutMapping("/{id}")
	public ResponseEntity<Passage> editPassage(@RequestBody Passage passage,
			@PathVariable int id) {
		if (!passageRepository.existsById(id))
            throw new ObjectNotFoundException("Passage with id="+id+" not found");
		Passage tmp = passageRepository.getOne(id);
		if(passage.getCar()!=null)
			tmp.setCar(carRepository.findById( passage.getCar().getId() ).get());
		if(passage.getClient()!=null)
			tmp.setClient(clientRepository.findById( passage.getClient().getId() ).get());
		if(passage.getCrew()!=null)
			tmp.setCrew(crewRepository.findById( passage.getCrew().getId() ).get());
		passageRepository.save(tmp);
		return new ResponseEntity<Passage>(tmp, HttpStatus.OK);
	}
	
	//Deleting passage with id (Видалення рейсу з таблиці з конкретним id)
	@DeleteMapping("/{id}")
	public ResponseEntity<Passage> deletePassage(@PathVariable int id) {
		if (!passageRepository.existsById(id))
            throw new ObjectNotFoundException("Passage with id="+id+" not found");
		passageRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//Getting car of passage with id (Повертає автомобіль в конкретному рейсі)
	@GetMapping("/{id}/car")
	public ResponseEntity<Car> getCarOfPassage(@PathVariable int id) {
		if (!passageRepository.existsById(id))
            throw new ObjectNotFoundException("Passage with id="+id+" not found");
		return new ResponseEntity<Car>(passageRepository.findById(id).get().getCar(),
				HttpStatus.OK);
	}
	
	//Getting client of passage with id (Повертає клієнта в конкретному рейсі)
	@GetMapping("/{id}/client")
	public ResponseEntity<Client> getClientOfPassage(@PathVariable int id) {
		if (!passageRepository.existsById(id))
            throw new ObjectNotFoundException("Passage with id="+id+" not found");
		return new ResponseEntity<Client>(passageRepository.findById(id).get().getClient(),HttpStatus.OK);
	}
	
	//Getting crew of passage with id (Повертає екіпаж в конкретному рейсі)
	@GetMapping("/{id}/crew")
	public ResponseEntity<Crew> getCrewOfPassage(@PathVariable int id) {
		if (!passageRepository.existsById(id))
            throw new ObjectNotFoundException("Passage with id="+id+" not found");
		return new ResponseEntity<Crew>(passageRepository.findById(id).get().getCrew(),
				HttpStatus.OK);
	}
	
}
