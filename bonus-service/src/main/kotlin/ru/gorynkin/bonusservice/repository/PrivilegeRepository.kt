package ru.gorynkin.bonusservice.repository

import ru.gorynkin.bonusservice.domain.PrivilegeEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PrivilegeRepository: CoroutineCrudRepository<PrivilegeEntity, Int> {
    suspend fun findFirstByUsername(username: String): PrivilegeEntity?
}
