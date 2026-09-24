package me.dio.cup.catar.remote.extensions

import me.dio.cup.catar.remote.NotFoundException
import me.dio.cup.catar.remote.UnexpectedException
import java.net.HttpURLConnection
import retrofit2.HttpException as RetrofitHttpException

internal fun <T> Result<T>.getOrThrowDomainError(): T = getOrElse { throwable ->
    throw throwable.toDomainError()
}

internal fun Throwable.toDomainError(): Throwable {
    return when (this) {
        is RetrofitHttpException -> {
            when (code()) {
                HttpURLConnection.HTTP_NOT_FOUND ->
                    NotFoundException("Oops! We couldn't find the matches :'(")
                else -> UnexpectedException()
            }
        }
        else -> this
    }
}
