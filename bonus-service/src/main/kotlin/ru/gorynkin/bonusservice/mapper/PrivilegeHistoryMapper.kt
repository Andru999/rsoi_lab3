package ru.gorynkin.bonusservice.mapper

import ru.gorynkin.bonus_service_api.common.BalanceHistory
import ru.gorynkin.bonusservice.domain.PrivilegeHistoryEntity

fun PrivilegeHistoryEntity.toBalanceHistory() = BalanceHistory(
    date = dateTime,
    balanceDiff = balanceDiff,
    ticketUid = ticketUid,
    operationType = operationType
)