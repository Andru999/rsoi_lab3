package ru.gorynkin.userservice.service

import ru.gorynkin.common.safeBody
import ru.gorynkin.user_service_api.UserInfoResponse
import ru.gorynkin.userservice.feign.BonusServiceApi
import ru.gorynkin.userservice.feign.TicketServiceApi
import ru.gorynkin.userservice.mapper.toPrivilegeShortInfo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class NetworkUserService(
    private val bonusServiceApi: BonusServiceApi,
    private val ticketServiceApi: TicketServiceApi
): UserService {
    override suspend fun getUserInfo(username: String): UserInfoResponse {
        val tickets = ticketServiceApi.getAllUserTickets(username).safeBody().asFlow().toList()
        val privilege = bonusServiceApi.getPrivilegeInfo(username).safeBody().asFlow().first()
            .toPrivilegeShortInfo()
        return UserInfoResponse(tickets = tickets, privilege = privilege)
    }
}