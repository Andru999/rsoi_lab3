package ru.gorynkin.flightservice.mapper

import ru.gorynkin.flight_service_api.FlightResponse
import ru.gorynkin.flightservice.domain.AirportEntity
import ru.gorynkin.flightservice.domain.FlightEntity

fun FlightEntity.toFlightResponse(fromAirport: AirportEntity, toAirport: AirportEntity) = FlightResponse(
    flightNumber = flightNumber,
    fromAirport = "${fromAirport.city} ${fromAirport.name}",
    toAirport = "${toAirport.city} ${toAirport.name}",
    date = dateTime,
    price = price
)