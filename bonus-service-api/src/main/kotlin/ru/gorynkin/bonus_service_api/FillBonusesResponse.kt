package ru.gorynkin.bonus_service_api

import ru.gorynkin.bonus_service_api.common.PrivilegeShortInfo

data class FillBonusesResponse(
    val filledBonuses: Int,
    val privilege: PrivilegeShortInfo
)
