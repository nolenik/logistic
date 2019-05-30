package com.uit.logistic.Client;

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

import com.uit.logistic.Exception.ObjectNotFoundException;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

	@Autowired
	ClientRepository clientRepository;
	
	//Getting all clients (Функція повертає всіх клієнтів в базі даних)
	@GetMapping("/")
	public ResponseEntity<List<Client>> getAll() {
		List<Client> list = clientRepository.findAll();
		return new ResponseEntity<List<Client>>(list,HttpStatus.OK);
	}
	
	//Getting client with id (Функція повертає клієнта з конкретним id)
	@GetMapping("/{id}")
	public ResponseEntity<Client> getById(@PathVariable int id) {
		if (!clientRepository.existsById(id))
            throw new ObjectNotFoundException("Client with id="+id+" not found");
		Client client = clientRepository.findById(id).get();
		return new ResponseEntity<Client>(client,HttpStatus.OK);
	}
	
	//Adding client to table (Функція створює нового клієнта)
	@PostMapping("/")
	public ResponseEntity<Client> addClient(@Valid @RequestBody Client client) {
		clientRepository.save(client);
		return new ResponseEntity<Client>(client,
				HttpStatus.CREATED);
	}
	
	//Editing client with id (Зміна характеристик клієнта з конкретним id)
	@PutMapping("/{id}")
	public ResponseEntity<Client> editClient(@RequestBody Client client,
			@PathVariable int id) {
		if (!clientRepository.existsById(id))
            throw new ObjectNotFoundException("Client with id="+id+" not found");
		Client tmp = clientRepository.getOne(id);
		tmp.setName(client.getName());
		tmp.setPhone(client.getPhone());
		clientRepository.save(tmp);
		return new ResponseEntity<Client>(tmp,HttpStatus.OK);
	}
	
	//Deleting client with id (Видалення клієнта з таблиці з конкретним id)
	@DeleteMapping("/{id}")
	public ResponseEntity<Client> deleteClient(@PathVariable int id) {
		if (!clientRepository.existsById(id))
            throw new ObjectNotFoundException("Client with id="+id+" not found");
		clientRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
