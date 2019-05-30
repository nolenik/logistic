package com.uit.logistic.Passage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uit.logistic.Car.Car;
import com.uit.logistic.Client.Client;
import com.uit.logistic.Crew.Crew;



@Entity
@Table (name="passage")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
class Passage {
     
	@Override
	public String toString() {
		return "Passage [id=" + id + ", car=" + car + ", crew=" + crew + ", client=" + client.toString() + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
    @ManyToOne
	@JoinColumn(name = "car")
	private Car car;
	
	@NotNull
    @ManyToOne
	@JoinColumn(name = "crew")
	private Crew crew;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "client")
	Client client;
	
	public Passage() {}

	public Passage(Car car,Crew crew, Client client) {
		this.car = car;
		this.crew = crew;
		this.client = client;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Crew getCrew() {
		return crew;
	}

	public void setCrew(Crew crew) {
		this.crew = crew;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
    
}
