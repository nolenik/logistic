package com.uit.logistic.Car;

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
@RequestMapping(path = "/car")
public class CarController {
    @Autowired
    CarRepository carRepository;
    
    //Getting all cars (Функція повертає всі автомобілі в базі даних)
    @GetMapping("/")
    public ResponseEntity<List<Car>> getAll() {
         List<Car> list = carRepository.findAll();
         return new ResponseEntity<List<Car>>(list, HttpStatus.OK);
    }
    
    //Getting car with id (Функція повертає автомобіль з конкретним id)
    @GetMapping("/{id}")
    public ResponseEntity<Car> getById (@PathVariable int id) throws Exception {
        if (!carRepository.existsById(id))
            throw new ObjectNotFoundException("Car with id="+id+" not found");
    	Car car = carRepository.findById(id).get();
    	return new ResponseEntity<Car>(car, HttpStatus.OK);
    }
    
    //Adding car to table (Функція створює новий автомобіль)
    @PostMapping("/")
    public ResponseEntity<Car> addCar (@Valid @RequestBody Car car) {
    	carRepository.save(car);
    	return new ResponseEntity<Car>(car, HttpStatus.CREATED);
    }
    
    //Editing car with id (Зміна характеристик автомобіля з конкретним id)
    @PutMapping("/{id}")
    public ResponseEntity<Car> editCar(@RequestBody Car car, @PathVariable int id) {
    	if (!carRepository.existsById(id))
            throw new ObjectNotFoundException("Car with id="+id+" not found");
    	Car newCar = carRepository.getOne(id);
    	if (car.getModel()!=null)
    		newCar.setModel(car.getModel());
    	if (car.getName()!=null)
    		newCar.setName(car.getName());
    	carRepository.save(newCar);
    	return new ResponseEntity<Car>(newCar,HttpStatus.OK);
    }
    
    //Deleting car with id (Видалення автомобіля з таблиці з конкретним id)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable int id) throws Exception {
    	if (!carRepository.existsById(id))
            throw new ObjectNotFoundException("Car with id="+id+" not found");
  	    carRepository.deleteById(id);
	    return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    	
    }
}
