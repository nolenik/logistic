package com.uit.logistic.Crew;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uit.logistic.Human.Human;

@Entity
@Table(name = "crew")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Crew {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToMany (fetch = FetchType.LAZY, cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
		})
    @JoinTable(name = "human_crew", joinColumns= {@JoinColumn(name="crewId")}, 
	inverseJoinColumns= {@JoinColumn(name="humanId")})
	Set<Human> humans;
	
	public Crew(Set<Human> hum) {
		this.humans=hum;
	}
	public Crew() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<Human> getHumans() {
		return humans;
	}

	
	public void setHumans(Set<Human> humans) {
		this.humans = humans;
	}
	
	
}
