package com.gl.ticketTracker.controller;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.gl.ticketTracker.dto.TicketDto;
import com.gl.ticketTracker.service.TicketService;

@Controller
public class TicketController {
	private TicketService ticketService;

	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@GetMapping("/admin/tickets")

	public String tickets(Model model) {
		List<TicketDto> tickets = ticketService.findAllTickets();
		model.addAttribute("tickets", tickets); 
		return "/admin/tickets";
	}

	
	@GetMapping("admin/tickets/newTicket")
	public String newTicketForm(Model model) {
		TicketDto ticketDto = new TicketDto();
		model.addAttribute("ticket", ticketDto);
		return "admin/create_ticket";
	}

	
	@PostMapping("/admin/tickets")
	public String createTicket(@Valid @ModelAttribute("ticket") TicketDto ticketDto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("ticket", ticketDto);
			return "admin/create_ticket";
		}
	    ticketDto.setUrl(getUrl(ticketDto.getTitle()));
		ticketService.createTicket(ticketDto);
		return "redirect:/admin/tickets";
	}

	private static String getUrl(String ticketTitle) {

		String title = ticketTitle.trim().toLowerCase();
		String url = title.replaceAll("\\s+", "-");
		url = url.replaceAll("[A-Za-z0-9]", "=");
		return url;
	}

	
	@GetMapping("/admin/tickets/{ticketId}/edit")
	public String editTicketForm(@PathVariable("ticketId") Long ticketId, Model model) {

		TicketDto ticketDto = ticketService.findTickerById(ticketId);
		model.addAttribute("ticket", ticketDto);
		return "admin/edit_ticket";

	}

	
	@PostMapping("/admin/tickets/{ticketId}")
	public String updateTicket(@PathVariable("ticketId") long ticketId,
			@Valid @ModelAttribute("ticket") TicketDto ticket, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("ticket", ticket);
			return "admin/edit_ticket";
		}
		ticket.setId(ticketId);
		ticketService.updateTicket(ticket);
		return "redirect:/admin/tickets";
	}

	
	@GetMapping("/admin/tickets/{ticketId}/delete")

	public String deleteTicket(@PathVariable("ticketId") Long ticketId) {
		ticketService.deleteTicket(ticketId);
		return "redirect:/admin/tickets";
	}


	@GetMapping("/admin/ticket/{ticketUrl}/view")
	public String viewPost(@PathVariable("ticketUrl") String ticketUrl, Model model) {
		TicketDto ticketDto = ticketService.findTicketByUrl(ticketUrl);
		model.addAttribute("ticket",ticketDto);
		return "admin/view_ticket";
	}
	
	
	@GetMapping("/admin/tickets/search")
	public String searchTickets(@RequestParam(value="query") String query, Model model) {
		
		List<TicketDto> tickets = ticketService.searchTickets(query);
		model.addAttribute("tickets", tickets);
		return "admin/tickets";
		
	}
}
