package ru.gorynkin.ticketservice.service

import ru.gorynkin.bonus_service_api.*

interface BonusService {
    suspend fun getPrivilegeInfo(username: String): PrivilegeInfoResponse
    suspend fun payWithBonuses(username: String, request: PayWithBonusesRequest): PayWithBonusesResponse
    suspend fun fillBonuses(username: String, request: FillBonusesRequest): FillBonusesResponse
    suspend fun cancelBonusOperation(username: String, request: CancelBonusesRequest)
}
