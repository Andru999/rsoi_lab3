package ru.gorynkin.flightservice.repository

import ru.gorynkin.flightservice.domain.AirportEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AirportRepository: CoroutineCrudRepository<AirportEntity, Int>