package br.com.events.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Guest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1470323988594309952L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column @NotBlank
	private String name;
	
	@Column(unique = true) @NotBlank
	private String rg;
	
	@ManyToOne
	private Event event;
	
	@Override
	public String toString() {
		return "Guest [id=" + id + ", name=" + name + ", rg=" + rg + ", event=" + event + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
}
