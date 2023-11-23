package me.nathanfallet.zabricraft.models.leaderboards

import org.bukkit.Location

data class UpdateLeaderboardPayload(
    val location: Location? = null,
    val type: String? = null,
    val limit: Int? = null
)
