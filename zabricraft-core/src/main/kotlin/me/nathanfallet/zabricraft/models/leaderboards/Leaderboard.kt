package me.nathanfallet.zabricraft.models.leaderboards

import org.bukkit.Location
import org.bukkit.entity.ArmorStand

data class Leaderboard(
    val location: Location,
    val type: String,
    val limit: Int
) {

    val armors: MutableList<ArmorStand> = mutableListOf()

    fun kill() {
        armors.forEach { it.remove() }
        armors.clear()
    }

}
