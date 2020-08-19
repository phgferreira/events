package br.com.events.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924064674562340836L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank
	private String name;
	private String place;
	
	@NotBlank
	private String date;
	
	@NotBlank
	private String hour;
	
	@OneToMany(mappedBy = "event")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Guest> guests;
	
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}
}
