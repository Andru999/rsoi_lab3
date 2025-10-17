package ru.gorynkin.flightservice.service

import ru.gorynkin.common.exception.EntityNotFoundException
import ru.gorynkin.flight_service_api.FlightResponse
import ru.gorynkin.flightservice.PageResponse
import ru.gorynkin.flightservice.domain.AirportEntity
import ru.gorynkin.flightservice.mapper.toFlightResponse
import ru.gorynkin.flightservice.repository.AirportRepository
import ru.gorynkin.flightservice.repository.FlightRepositoryAdapter
import ru.gorynkin.flightservice.toPageResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersistableFlightService(
    private val flightRepository: FlightRepositoryAdapter,
    private val airportRepository: AirportRepository
): FlightService {
    @Transactional
    override suspend fun findFlights(page: Int, size: Int): PageResponse<FlightResponse> = flightRepository
        .findBy(PageRequest.of(page, size))
        .suspendableMap { flightEntity ->
            val fromAirport = findAirport(flightEntity.fromAirportId)
            val toAirport = findAirport(flightEntity.toAirportId)

            flightEntity.toFlightResponse(fromAirport, toAirport)
        }.toPageResponse()

    @Transactional(readOnly = true)
    override suspend fun findFlight(flightNumber: String): FlightResponse = flightRepository.findByFlightNumber(flightNumber)?.let {
        val fromAirport = findAirport(it.fromAirportId)
        val toAirport = findAirport(it.toAirportId)

        it.toFlightResponse(fromAirport, toAirport)
    } ?: throw EntityNotFoundException("Flight with flightNumber '$flightNumber' not found")

    private suspend fun findAirport(id: Int): AirportEntity = airportRepository.findById(id)
        ?: throw EntityNotFoundException("Airport with id '$id' not found")
}

inline fun <U, T> Page<U>.suspendableMap(converter: (U) -> T): Page<T> {
    val newContent = content.map {
        converter(it)
    }

    return PageImpl(newContent, pageable, totalElements)
}