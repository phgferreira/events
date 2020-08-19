package br.com.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String save(@Validated Event e, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("msg", "Verifique os campos");
			return "redirect:/registerEvent";
		} else {
			er.save(e);
			attributes.addFlashAttribute("msg", "Evento cadastrado com sucesso");
			return "redirect:/registerEvent";
		}
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
		mv.addObject("guests", 	g);
		return mv;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String detailEventAddGuest(@PathVariable("id") Integer id, @Validated Guest guest, BindingResult result, RedirectAttributes attributes) {
		
		// Buscamos o evento no banco de dados
		Event e = er.findById(id).get();
		// Atribui o evento ao convidado
		guest.setEvent(e);

		if (result.hasErrors()) {
			attributes.addFlashAttribute("msg", "ERRO: Verifique os campos");
		} else {
			gr.save(guest);
			attributes.addFlashAttribute("msg", "Convidado inserido com sucesso");
		}

		return "redirect:/{id}";
	}
	
	@RequestMapping("/deleteEvent")
	public String deleteEvent(Integer id) {
		Event e = er.findById(id).get();
		er.delete(e);
		return "redirect:/listEvents";
	}
	
	@RequestMapping("/deleteGuest")
	public String deleteGuest(String rg) {
		Guest g = gr.findByRg(rg);
		gr.delete(g);
		return "redirect:/" + g.getEvent().getId();
		
	}
	
}
