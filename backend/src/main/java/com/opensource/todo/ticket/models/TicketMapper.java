package com.opensource.todo.ticket.models;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(target = "relatedProject", source = "relatedProject", ignore = true)
    @Mapping(target = "assignedTo", source = "assignedTo", ignore = true)
    Ticket ticketFormToTicket(TicketForm ticketForm);
}
