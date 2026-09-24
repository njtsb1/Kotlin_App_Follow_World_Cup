package me.dio.cup.catar.domain.model

import java.time.Instant

data class Stadium(
    val name: String,
    val imageUrl: String
)

data class Match(
    val name: String,
    val stadium: Stadium,
    val team1: String,
    val team2: String,
    val date: Instant
)
