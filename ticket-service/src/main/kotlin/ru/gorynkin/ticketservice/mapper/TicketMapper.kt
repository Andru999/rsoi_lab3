package ru.gorynkin.ticketservice.mapper

import ru.gorynkin.flight_service_api.FlightResponse
import ru.gorynkin.ticket_service_api.TicketResponse
import ru.gorynkin.ticketservice.domain.TicketEntity

fun TicketEntity.toTicketResponse(
    flightResponse: FlightResponse
): TicketResponse = TicketResponse(
    ticketUid = ticketUid,
    flightNumber = flightNumber,
    fromAirport = flightResponse.fromAirport,
    toAirport = flightResponse.toAirport,
    date = flightResponse.date,
    price = price,
    status = status
)
