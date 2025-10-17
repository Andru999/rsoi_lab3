package ru.gorynkin.ticketservice.service

import ru.gorynkin.flight_service_api.FlightResponse

interface FlightService {
    suspend fun getByFlightNumber(flightNumber: String): FlightResponse
}