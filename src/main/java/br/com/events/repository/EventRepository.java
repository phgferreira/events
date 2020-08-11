package br.com.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.events.model.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {

}
