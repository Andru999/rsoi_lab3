package ru.gorynkin.userservice.mapper

import ru.gorynkin.bonus_service_api.PrivilegeInfoResponse
import ru.gorynkin.bonus_service_api.common.PrivilegeShortInfo

fun PrivilegeInfoResponse.toPrivilegeShortInfo() = PrivilegeShortInfo(
    balance = balance,
    status = status
)