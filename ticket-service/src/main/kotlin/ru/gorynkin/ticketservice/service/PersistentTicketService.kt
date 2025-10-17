package ru.gorynkin.ticketservice.service

import ru.gorynkin.bonus_service_api.CancelBonusesRequest
import ru.gorynkin.bonus_service_api.FillBonusesRequest
import ru.gorynkin.bonus_service_api.PayWithBonusesRequest
import ru.gorynkin.bonus_service_api.common.PrivilegeShortInfo
import ru.gorynkin.common.exception.EntityNotFoundException
import ru.gorynkin.ticket_service_api.TicketPurchaseRequest
import ru.gorynkin.ticket_service_api.TicketPurchaseResponse
import ru.gorynkin.ticket_service_api.TicketResponse
import ru.gorynkin.ticket_service_api.common.TicketStatus
import ru.gorynkin.ticketservice.domain.TicketEntity
import ru.gorynkin.ticketservice.mapper.toTicketResponse
import ru.gorynkin.ticketservice.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PersistentTicketService(
    private val ticketRepository: TicketRepository,
    private val flightService: FlightService,
    private val bonusService: BonusService
) : TicketService {
    @Transactional(readOnly = true)
    override fun findTickets(username: String): Flow<TicketResponse> = ticketRepository.findAllByUsername(username)
        .map {
            val flightResponse = flightService.getByFlightNumber(it.flightNumber)
            it.toTicketResponse(flightResponse)
        }

    @Transactional(readOnly = true)
    override suspend fun findTicket(username: String, ticketUid: UUID): TicketResponse =
        ticketRepository.findFirstByUsernameAndTicketUid(username, ticketUid)?.let {
            val flightResponse = flightService.getByFlightNumber(it.flightNumber)
            it.toTicketResponse(flightResponse)
        } ?: throw EntityNotFoundException("Ticket with username $username and uid $ticketUid not found")

    @Transactional
    override suspend fun buyTicket(username: String, request: TicketPurchaseRequest): TicketPurchaseResponse {
        // TODO add date validation handling and saga
        val flight = flightService.getByFlightNumber(request.flightNumber) // Check that flight exists
        val ticket = TicketEntity(
            id = 0,
            ticketUid = UUID.randomUUID(),
            username = username,
            flightNumber = request.flightNumber,
            price = request.price,
            status = TicketStatus.PAID
        )
        val savedTicket = ticketRepository.save(ticket)
        var paidFromBalance: Int = 0

        val privilege: PrivilegeShortInfo = if (request.paidFromBalance) {
            val payRequest = PayWithBonusesRequest(ticketUid = savedTicket.ticketUid, price = savedTicket.price)
            val response = bonusService.payWithBonuses(username, payRequest)
            paidFromBalance = response.payed
            response.privilege
        } else {
            val fillBonusesRequest = FillBonusesRequest(ticketUid = savedTicket.ticketUid, price = savedTicket.price)
            val response = bonusService.fillBonuses(username, fillBonusesRequest)
            response.privilege
        }

        return TicketPurchaseResponse(
            ticketUid = savedTicket.ticketUid,
            flightNumber = request.flightNumber,
            fromAirport = flight.fromAirport,
            toAirport = flight.toAirport,
            date = flight.date,
            status = savedTicket.status,
            price = savedTicket.price,
            paidByMoney = savedTicket.price - paidFromBalance,
            paidByBonuses = paidFromBalance,
            privilege = privilege
        )
    }

    @Transactional
    override suspend fun cancelTicket(username: String, ticketUid: UUID) {
        val ticket = ticketRepository.findFirstByUsernameAndTicketUid(username, ticketUid) ?:
            throw EntityNotFoundException("Ticket with username $username and uid $ticketUid not found")
        bonusService.cancelBonusOperation(username, CancelBonusesRequest(ticketUid))

        ticketRepository.save(ticket.copy(status = TicketStatus.CANCELED))
    }
}