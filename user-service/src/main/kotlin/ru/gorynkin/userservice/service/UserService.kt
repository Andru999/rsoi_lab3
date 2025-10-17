package ru.gorynkin.userservice.service

import ru.gorynkin.user_service_api.UserInfoResponse

interface UserService {
    suspend fun getUserInfo(username: String): UserInfoResponse
}