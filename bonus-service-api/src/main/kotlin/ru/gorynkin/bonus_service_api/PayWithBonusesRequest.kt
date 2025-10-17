package ru.gorynkin.bonus_service_api

import java.util.*

data class PayWithBonusesRequest(
    val ticketUid: UUID,
    val price: Int
)
