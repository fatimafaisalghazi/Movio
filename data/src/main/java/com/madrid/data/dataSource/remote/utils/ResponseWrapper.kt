package com.madrid.data.dataSource.remote.utils

import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.exceptions.UnknownException
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend fun <T> responseWrapper(
    block: suspend () -> T
): T {
    return try {
        val result = block()
        result
    } catch (e: HttpException) {
        throw if (e.code() == 401) {
            val responseBody = e.response()?.errorBody()?.string()
            val errorResponseBody =
                responseBody?.let { Json.decodeFromString<ErrorResponseBody>(it) }
            mapHttpCodeToDomainException(errorResponseBody?.status_code)
        } else {
            NetworkException("No Internet Connection: ${e.message}")
        }
    } catch (e: ConnectException) {
        throw NetworkException("No Internet Connection: ${e.message}")
    } catch (e: SerializationException) {
        throw UnknownException("Serialization Error: ${e.message ?: "Unknown serialization error"}")
    } catch (e: UnknownHostException) {
        throw NetworkException("No Internet Connection: ${e.message}")
    } catch (e: SocketTimeoutException) {
        throw NetworkException("Timeout Error: ${e.message ?: "Unknown timeout error"}")
    } catch (e: Exception) {
        throw UnknownException(e.message ?: "Unknown error")
    }
}

fun mapHttpCodeToDomainException(
    code: Int?
): MovioException {
    return when (code) {
        3, 14, 30 -> {
            NetworkException("No Internet Connection")
        }

        7, 10, 16, 17, 33, 35, 36 -> {
            NetworkException("No Internet Connection")
        }

        31 -> {
            NetworkException("No Internet Connection")
        }

        32 -> {
            NetworkException("No Internet Connection")
        }

        38, 39 -> {
            NetworkException("No Internet Connection")
        }

        45 -> {
            NetworkException("No Internet Connection")
        }

        else -> {
            NetworkException("")
        }
    }
}

@Serializable
data class ErrorResponseBody(
    val status_code: Int,
    val status_message: String?,
    val success: Boolean? = null,
)