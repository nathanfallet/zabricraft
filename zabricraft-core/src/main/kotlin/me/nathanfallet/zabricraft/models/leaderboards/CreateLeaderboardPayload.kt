package me.nathanfallet.zabricraft.models.leaderboards

import org.bukkit.Location

data class CreateLeaderboardPayload(
    val id: String,
    val location: Location,
    val type: String,
    val limit: Int
)
