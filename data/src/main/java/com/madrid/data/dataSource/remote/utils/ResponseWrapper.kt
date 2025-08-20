package com.madrid.data.dataSource.remote.utils


import android.util.Log
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.exceptions.ServerException
import com.madrid.domain.exceptions.TimeoutException
import com.madrid.domain.exceptions.UnauthorizedException
import com.madrid.domain.exceptions.UnknownException
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Response
import retrofit2.HttpException


fun responseWrapper(
    block: () -> Response,
): Response {
    // HTTP response code ranges
    val successRange = 200..299

    // TMDB specific error codes
    val unauthorizedExceptionCodes = setOf(3, 7, 10, 14, 16, 17, 30, 31, 32, 33, 35, 36, 38, 39)
    val serverExceptionCodes = setOf(9, 43, 44, 46, 11, 15)
    val timeoutExceptionCodes = setOf(24)

// val unknownExceptionCodes = setOf(2, 4, 5, 6, 8, 18, 19, 20, 21, 22, 23, 25, 26, 27, 28, 29, 34, 37, 40, 41, 42, 45, 47)

    val response: Response
    try {
        response = block()
        return when (response.code) {
            in successRange -> {
                val responseBody = response.body?.string() ?: ""
                Log.e("ResponseWrapper", "Error response: $responseBody")
                response
            }
            else -> {
                val responseBody = response.body?.string() ?: ""
                val errorResponseBody = Json.decodeFromString<ErrorResponseBody>(responseBody)
                Log.e("ResponseWrapper", "Error response: $errorResponseBody")
                when (errorResponseBody.status_code) {
                    in unauthorizedExceptionCodes -> throw UnauthorizedException("")

                    in serverExceptionCodes -> throw ServerException(
                        message = errorResponseBody.status_message ?: "Server error occurred"
                    )

                    in timeoutExceptionCodes -> throw TimeoutException(
                        message = errorResponseBody.status_message ?: "Request timed out"
                    )

                    else -> throw UnknownException(
                        message = errorResponseBody.status_message ?: "Unknown error occurred"
                    )
                }
//                throw UnknownException(
//                    message = "Unknown error occurred"
//                )
            }
        }
    } catch (e: java.net.UnknownHostException) {
        throw NetworkException(
            message = "Network error occurred: ${e.message ?: "Unknown error"}"
        )
    } catch (movioException: MovioException) {
        throw movioException
    } catch (e: Exception) {
        throw UnknownException(
            message = "Unknown error occurred: ${e.message ?: "Unknown error"}"
        )
    }
}


@Serializable
data class ErrorResponseBody(
    val status_code: Int,
    val status_message: String?,
    val success: Boolean? = null,
)