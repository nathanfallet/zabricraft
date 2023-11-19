package me.nathanfallet.zabricraft.models.leaderboards

import me.nathanfallet.zabricraft.usecases.leaderboards.IGenerateLeaderboardUseCase
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType

data class Leaderboard(
    val location: Location,
    val type: String,
    val limit: Int
) {

    private val armors: MutableList<ArmorStand> = mutableListOf()

    fun update() {
        if (armors.size != limit + 2) {
            kill()
            val currentLocation = location.clone().add(0.0, 0.27 * (limit + 2).toDouble() - 1.0, 0.0)
            for (index in 0..<limit + 2) {
                val armor =
                    location.world?.spawnEntity(currentLocation, EntityType.ARMOR_STAND) as? ArmorStand ?: continue
                armor.isVisible = false
                armor.setGravity(false)
                armor.setBasePlate(false)
                armor.customName = ""
                armor.isCustomNameVisible = true
                armors.add(armor)
                currentLocation.add(0.0, -0.27, 0.0)
            }
        }
        val generator: IGenerateLeaderboardUseCase? = null//Core.getInstance().leaderboardGenerators[type]
        generator ?: run {
            armors.iterator().forEach {
                it.customName = ChatColor.RED.toString() + "---------------------------"
            }
            return
        }
        val (title, lines) = generator(limit)
        armors.forEachIndexed { index, armor ->
            armor.customName = when (index) {
                0 -> ChatColor.YELLOW.toString() + "------- " + ChatColor.GOLD + title + ChatColor.YELLOW + " ---------"
                limit + 1 -> ChatColor.YELLOW.toString() + "---------------------------"
                else -> ChatColor.GOLD.toString() + index + ". " + ChatColor.YELLOW + if (lines.size >= index) lines[index - 1] else ""
            }
        }
    }

    fun kill() {
        armors.forEach { it.remove() }
        armors.clear()
    }

}
