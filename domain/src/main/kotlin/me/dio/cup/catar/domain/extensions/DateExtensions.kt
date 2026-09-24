package me.dio.cup.catar.domain.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.getDate(): String {
    return DateTimeFormatter.ofPattern("dd/MM HH:mm").format(this)
}
