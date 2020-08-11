package br.com.events.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.events.model.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {

	Optional<Event> findById(Integer id);
	
}
