package br.com.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.events.model.Event;
import br.com.events.repository.EventRepository;

@Controller
public class EventController {

	@Autowired
	private EventRepository er;
	
	@RequestMapping(value = "/registerEvent", method = RequestMethod.GET)
	public String form() {
		return "event/registerEvent";
	}

	@RequestMapping(value = "/registerEvent", method = RequestMethod.POST)
	public String form(Event e) {
		
		er.save(e);
		
		return "redirect:/registerEvent";
	}
}
