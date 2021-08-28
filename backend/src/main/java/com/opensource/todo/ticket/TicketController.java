package com.opensource.todo.ticket;

import com.opensource.todo.ticket.models.TicketForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("{id}")
    public ResponseEntity<?> readByTicketId(@PathVariable String id) {
        return new ResponseEntity<>(ticketService.getByTicketId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody @Valid TicketForm ticketForm) {
        return new ResponseEntity<>(ticketService.addTicket(ticketForm), HttpStatus.CREATED);
    }

}
