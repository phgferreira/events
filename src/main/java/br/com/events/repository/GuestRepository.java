package br.com.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.events.model.Guest;

public interface GuestRepository extends CrudRepository<Guest, Integer> {

}
