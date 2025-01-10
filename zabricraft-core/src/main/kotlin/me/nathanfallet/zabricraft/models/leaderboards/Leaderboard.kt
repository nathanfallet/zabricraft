package me.nathanfallet.zabricraft.models.leaderboards

import dev.kaccelero.models.IModel
import org.bukkit.Location
import org.bukkit.entity.ArmorStand

data class Leaderboard(
    override val id: String,
    val location: Location,
    val type: String,
    val limit: Int,
) : IModel<String, CreateLeaderboardPayload, UpdateLeaderboardPayload> {

    val armors: MutableList<ArmorStand> = mutableListOf()

    fun kill() {
        armors.forEach { it.remove() }
        armors.clear()
    }

}
