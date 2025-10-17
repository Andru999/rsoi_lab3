package ru.gorynkin.ticketservice.service

import ru.gorynkin.common.exception.ClientResponseException
import ru.gorynkin.common.safeBody
import ru.gorynkin.ticketservice.feign.BonusServiceApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Service
import ru.gorynkin.bonus_service_api.*

@Service
class NetworkBonusService(
    private val bonusServiceApi: BonusServiceApi
) : BonusService {
    override suspend fun getPrivilegeInfo(username: String): PrivilegeInfoResponse =
        bonusServiceApi.getPrivilegeInfo(username).safeBody().asFlow().first()

    override suspend fun payWithBonuses(
        username: String,
        request: PayWithBonusesRequest
    ): PayWithBonusesResponse = bonusServiceApi.payWithBonuses(username, request).safeBody().asFlow().first()

    override suspend fun fillBonuses(
        username: String,
        request: FillBonusesRequest
    ): FillBonusesResponse = bonusServiceApi.fillBonuses(username, request).safeBody().asFlow().first()

    override suspend fun cancelBonusOperation(username: String, request: CancelBonusesRequest) {
        val response = bonusServiceApi.cancelBonusOperation(username, request).asFlow().first()
        if (response.statusCode.isError) {
            throw ClientResponseException(response)
        }
    }
}
