package ru.gorynkin.ticketservice.service

import ru.gorynkin.common.safeBody
import ru.gorynkin.flight_service_api.FlightResponse
import ru.gorynkin.ticketservice.feign.FlightServiceApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Service

@Service
class NetworkFlightService(private val flightServiceApi: FlightServiceApi): FlightService {
    override suspend fun getByFlightNumber(flightNumber: String): FlightResponse = flightServiceApi
        .fetchByFlightNumber(flightNumber).safeBody().asFlow().first()
}