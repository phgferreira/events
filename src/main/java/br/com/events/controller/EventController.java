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
	
	@RequestMapping(value = "/eventForm", method = RequestMethod.GET)
	public String form() {
		return "event/form";
	}

	@RequestMapping(value = "/eventForm", method = RequestMethod.POST)
	public String save(@Validated Event e, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("msg", "Verifique os campos");
		} else {
			er.save(e);
			attributes.addFlashAttribute("msg", "Evento cadastrado com sucesso");
		}
		return "event/form";
	}
	
	@RequestMapping("/eventList")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("event/list");

		Iterable<Event> e = er.findAll();
		mv.addObject("events", e);

		return mv;
	}
	
	@RequestMapping(value = "/eventDetail{id}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView("event/detail");

		Event e = er.findById(id).get();
		mv.addObject("event", e);
		
		Iterable<Guest> g = gr.findByEvent(e);
		mv.addObject("guests", 	g);
		return mv;
	}

	
	@RequestMapping("/eventDelete")
	public String delete(Integer id) {

		Event e = er.findById(id).get();
		er.delete(e);

		return "redirect:/eventList";
	}

	
	// ################### GUEST ###################
	// O Formulário do convidado está dentro de datail do Evento
	@RequestMapping(value = "/eventDetail{id}", method = RequestMethod.POST)
	public String addGuest(@PathVariable("id") Integer id, @Validated Guest guest, BindingResult result, RedirectAttributes attributes) {
		
		// Carregamos o evento e jogando para o convidado
		Event e = er.findById(id).get();
		guest.setEvent(e);

		if (result.hasErrors()) {
			attributes.addFlashAttribute("msg", "ERRO: Verifique os campos");
		} else {
			// Salvamos o convidado com o Evento setado
			gr.save(guest);
			attributes.addFlashAttribute("msg", "Convidado inserido com sucesso");
		}

		// Volta para a página de detail
		return "redirect:/eventDetail{id}";
	}
	
	@RequestMapping("/removeGuest")
	public String removeGuest(String rg) {
		Guest g = gr.findByRg(rg);
		gr.delete(g);

		// Volta para a página de detail
		return "redirect:/eventDetail" + g.getEvent().getId();
		
	}
	
}
