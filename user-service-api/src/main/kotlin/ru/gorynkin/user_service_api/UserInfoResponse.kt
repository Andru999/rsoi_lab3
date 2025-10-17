package ru.gorynkin.user_service_api

import ru.gorynkin.bonus_service_api.common.PrivilegeShortInfo
import ru.gorynkin.ticket_service_api.TicketResponse

data class UserInfoResponse(
    val tickets: List<TicketResponse>,
    val privilege: PrivilegeShortInfo
)
