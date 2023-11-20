package me.nathanfallet.zabricraft.models.players

import kotlinx.datetime.Instant

data class CachedPlayer(
    val authenticated: Boolean,
    val loginAt: Instant,
    val scoreboard: PlayerScoreboard = PlayerScoreboard("ZabriCraft")
)
