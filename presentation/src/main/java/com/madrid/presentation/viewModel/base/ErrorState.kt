package com.madrid.presentation.viewModel.base

import com.madrid.designSystem.R as designSystemR
import com.madrid.presentation.R as presentationR


open class ErrorState(val message: String, val errorImageId: Int? = null)
class NetworkError(message: String) : ErrorState(message, errorImageId = presentationR.drawable.img_no_internet)
class NotFoundError(message: String) : ErrorState(message,errorImageId = designSystemR.drawable.not_found)

class ServerError(message: String) : ErrorState(message, errorImageId =designSystemR.drawable.no_internet)

open class AuthorizationError(message: String) : ErrorState(message)
class UnauthorizedError(message: String) : AuthorizationError(message)


open class ValidationError(message: String) : ErrorState(message)
class InvalidCredentialsError(message: String) : ValidationError(message)
class UnknownError(message: String) : ErrorState(message)