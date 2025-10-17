package ru.gorynkin.flightservice.service

import ru.gorynkin.flight_service_api.FlightResponse
import ru.gorynkin.flightservice.PageResponse

interface FlightService {
    suspend fun findFlights(page: Int, size: Int): PageResponse<FlightResponse>
    suspend fun findFlight(flightNumber: String): FlightResponse
}