package ru.gorynkin.bonus_service_api

import ru.gorynkin.bonus_service_api.common.BalanceHistory
import ru.gorynkin.bonus_service_api.common.PrivilegeStatus

data class PrivilegeInfoResponse(
    val balance: Int,
    val status: PrivilegeStatus,
    val history: List<BalanceHistory>
)
