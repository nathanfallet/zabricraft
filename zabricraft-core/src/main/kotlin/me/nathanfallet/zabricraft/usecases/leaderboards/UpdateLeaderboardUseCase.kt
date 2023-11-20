package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import org.bukkit.ChatColor
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType

class UpdateLeaderboardUseCase(
    private val getGenerateLeaderboardsUseCase: IGetGenerateLeaderboardsUseCase
) : IUpdateLeaderboardUseCase {

    override fun invoke(input: Leaderboard) {
        if (input.armors.size != input.limit + 2) {
            input.kill()
            val currentLocation = input.location.clone().add(0.0, 0.27 * (input.limit + 2).toDouble() - 1.0, 0.0)
            for (index in 0..<input.limit + 2) {
                val armor = input.location.world?.spawnEntity(
                    currentLocation, EntityType.ARMOR_STAND
                ) as? ArmorStand ?: continue
                armor.isVisible = false
                armor.setGravity(false)
                armor.setBasePlate(false)
                armor.customName = ""
                armor.isCustomNameVisible = true
                input.armors.add(armor)
                currentLocation.add(0.0, -0.27, 0.0)
            }
        }
        val generator = getGenerateLeaderboardsUseCase()[input.type] ?: run {
            input.armors.forEach {
                it.customName = ChatColor.RED.toString() + "---------------------------"
            }
            return
        }
        val lines = generator(input.limit)
        input.armors.forEachIndexed { index, armor ->
            armor.customName = when (index) {
                0 -> ChatColor.YELLOW.toString() + "------- " + ChatColor.GOLD + generator.title + ChatColor.YELLOW + " ---------"
                input.limit + 1 -> ChatColor.YELLOW.toString() + "---------------------------"
                else -> ChatColor.GOLD.toString() + index + ". " + ChatColor.YELLOW + if (lines.size >= index) lines[index - 1] else ""
            }
        }
    }

}
