package br.com.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.events.model.Event;
import br.com.events.model.Guest;
import br.com.events.repository.EventRepository;
import br.com.events.repository.GuestRepository;

@Controller
public class EventController {

	@Autowired
	private EventRepository er;
	
	@Autowired
	private GuestRepository gr;
	
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
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView detailEvent(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView("event/detailEvent");

		// Carrega o evento
		Event e = er.findById(id).get();
		mv.addObject("event", e);
		
		// Carrega os convidados do evento
		Iterable<Guest> g = gr.findByEvent(e);
		mv.addObject("guests", g);
		return mv;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String detailEventAddGuest(@PathVariable("id") Integer id, Guest guest) {
		// Buscamos o evento no banco de dados
		Event e = er.findById(id).get();
		// Inserimos o evento no convidado
		guest.setEvent(e);
		// Salvamos o convidado com o evento relacionado
		gr.save(guest);
		return "redirect:/{id}";
	}
	
	
}
