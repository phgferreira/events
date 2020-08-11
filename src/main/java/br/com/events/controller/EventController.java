package br.com.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		// Depois de salvar redireciona para a mesma página
		return "redirect:/registerEvent";
	}
	
	@RequestMapping("/listEvents")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("index");
		// Retorna lista de eventos
		Iterable<Event> events = er.findAll();
		// events é o nome da lista na view
		mv.addObject("events", events);
		return mv;
	}
	
	@RequestMapping("/{id}")
	public ModelAndView	 detailEvent(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView("event/detailEvent");
		Event e = er.findById(id).get();
		mv.addObject("event", e);
		return mv;
	}
}
