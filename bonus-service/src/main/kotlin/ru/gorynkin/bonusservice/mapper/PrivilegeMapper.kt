package ru.gorynkin.bonusservice.mapper

import ru.gorynkin.bonus_service_api.PrivilegeInfoResponse
import ru.gorynkin.bonus_service_api.common.BalanceHistory
import ru.gorynkin.bonus_service_api.common.PrivilegeShortInfo
import ru.gorynkin.bonusservice.domain.PrivilegeEntity

fun PrivilegeEntity.toPrivilegeInfoResponse(history: List<BalanceHistory>) = PrivilegeInfoResponse(
    balance = balance,
    status = status,
    history = history
)

fun PrivilegeEntity.toPrivilegeShortInfo() = PrivilegeShortInfo(
    balance = balance,
    status = status
)
