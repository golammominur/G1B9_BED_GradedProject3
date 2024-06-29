package com.gl.ticketTracker.serviceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.gl.ticketTracker.dto.TicketDto;
import com.gl.ticketTracker.entity.Ticket;
import com.gl.ticketTracker.mapper.TicketMapper;
import com.gl.ticketTracker.repository.TicketRepository;
import com.gl.ticketTracker.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	
	private TicketRepository ticketRepository;

	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Override
	public List<TicketDto> findAllTickets() {

		List<Ticket> tickets = ticketRepository.findAll();
		
	
		
		return tickets.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toList());
	}

	@Override
	public void createTicket(TicketDto ticketDto) {
		
		Ticket ticket =  TicketMapper.mapToTicket(ticketDto);
		ticketRepository.save(ticket);
		
	}

	@Override
	public TicketDto findTickerById(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId).get();
		return TicketMapper.mapToTicketDto(ticket);
	}

	@Override
	public void updateTicket(TicketDto ticketDto) {
		 Ticket ticket = TicketMapper.mapToTicket(ticketDto);
		 ticketRepository.save(ticket);
		
	}

	@Override
	public void deleteTicket(Long ticketId) {
		ticketRepository.deleteById(ticketId);
		
	}

	@Override
	public TicketDto findTicketByUrl(String ticketUrl) {
		Ticket ticket = ticketRepository.findByUrl(ticketUrl).get();
		return TicketMapper.mapToTicketDto(ticket);
	}

	@Override
	public List<TicketDto> searchTickets(String query) {
		List<Ticket> tickets = ticketRepository.searchTickets(query);
		return tickets.stream()
				.map(TicketMapper :: mapToTicketDto)
				.collect(Collectors.toList());
	}

}
