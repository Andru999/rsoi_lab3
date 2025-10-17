package ru.gorynkin.bonus_service_api

import ru.gorynkin.bonus_service_api.common.PrivilegeShortInfo

data class PayWithBonusesResponse(
    val payed: Int,
    val privilege: PrivilegeShortInfo
)
