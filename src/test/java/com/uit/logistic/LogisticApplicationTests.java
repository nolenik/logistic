package com.uit.logistic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.uit.logistic.Car.CarRepository;
import com.uit.logistic.Human.HumanRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogisticApplicationTests {
	
    @Autowired
	private MockMvc mockMvc;
	
    @Autowired 
    CarRepository carRepository;
    @Autowired
    HumanRepository humanRepository;
    
	@Test
	public void testCarController() throws Exception {
		
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/car/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\":\"Test\", \"model\":\"Test\"}"))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.content()
				.json("{\"name\":\"Test\", \"model\":\"Test\"}"));
		
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/car/")
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.content()
				.json("[{\"id\":"+carRepository.findAll().get(0).getId()+
				",\"name\":\"Test\",\"model\":\"Test\"}]"));
		
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/car/{id}",
				carRepository.findAll().get(0).getId())
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_UTF8));
	
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/car/{id}",
				carRepository.findAll().get(0).getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\":\"Test1\"}"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.content()
				.json("{\"id\":"+carRepository.findAll().get(0).getId()+
				",\"name\":\"Test1\",\"model\":\"Test\"}"));
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/car/{id}",
				carRepository.findAll().get(0).getId())
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
    
	
	@Test
	public void testHumanController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/human/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\":\"Test\", \"position\":\"Test\"}"))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.content()
				.json("{\"name\":\"Test\", \"position\":\"Test\"}"));
		
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/human/")
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.content()
				.json("[{\"id\":"+humanRepository.findAll().get(0).getId()+
				",\"name\":\"Test\",\"position\":\"Test\"}]"));
		
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/human/{id}",
				humanRepository.findAll().get(0).getId())
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.content()
				.json("{\"id\":"+humanRepository.findAll().get(0).getId()+
				",\"name\":\"Test\",\"position\":\"Test\"}"));
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/human/{id}",
				humanRepository.findAll().get(0).getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\":\"Test1\"}"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.content()
				.json("{\"id\":"+humanRepository.findAll().get(0).getId()+
				",\"name\":\"Test1\",\"position\":\"Test\"}"));
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/human/{id}",
				humanRepository.findAll().get(0).getId())
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
