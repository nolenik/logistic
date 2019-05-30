package com.uit.logistic.Human;

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
@RequestMapping("/human")
public class HumanController {
      @Autowired
      HumanRepository humanRepository;
      
      //Getting all humans (Функція повертає всіх людей в базі даних)
      @GetMapping("/")
      public ResponseEntity<List<Human>> getAll() {
    	  List<Human> list = humanRepository.findAll();
    	  return new ResponseEntity<List<Human>>(list, HttpStatus.OK);
      }
      
    //Getting human with id (Функція повертає людину з конкретним id)
      @GetMapping("/{id}")
      public ResponseEntity<Human> getById(@PathVariable int id) {
    	  if (!humanRepository.existsById(id))
              throw new ObjectNotFoundException("Human with id="+id+" not found");
    	  Human human = humanRepository.findById(id).get();
    	  return new ResponseEntity<Human>(human,HttpStatus.OK);
      }
      
      //Adding human to table (Функція створює нову людину)
      @PostMapping("/")
      public ResponseEntity<Human> addHuman(@Valid @RequestBody Human human) {
    	  humanRepository.save(human);
    	  return new ResponseEntity<Human>(human,HttpStatus.CREATED);
      }
      
    //Editing human with id (Зміна характеристик людини з конкретним id)
      @PutMapping("/{id}")
      public ResponseEntity<Human> editHuman(@PathVariable int id,
    		  @RequestBody Human human) {
    	  if (!humanRepository.existsById(id))
              throw new ObjectNotFoundException("Human with id="+id+" not found");
    	  Human tmp = humanRepository.getOne(id);
    	  if(human.getName()!=null)
    		  tmp.setName(human.getName());
    	  if(human.getPosition()!=null)
    		  tmp.setPosition(human.getPosition());
    	  humanRepository.save(tmp);
    	  return new ResponseEntity<Human>(tmp,HttpStatus.OK);
      }
      
      //Deleting human with id (Видалення людини з таблиці з конкретним id)
      @DeleteMapping("/{id}")
      public ResponseEntity<Human> deleteHuman(@PathVariable int id) {
    	  if (!humanRepository.existsById(id))
              throw new ObjectNotFoundException("Human with id="+id+" not found");
    	  humanRepository.deleteById(id);
    	  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
}
