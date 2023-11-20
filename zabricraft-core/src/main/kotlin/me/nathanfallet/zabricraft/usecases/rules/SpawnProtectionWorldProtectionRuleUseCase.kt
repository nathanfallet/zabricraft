package me.nathanfallet.zabricraft.usecases.rules

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent

class SpawnProtectionWorldProtectionRuleUseCase : IWorldProtectionRuleUseCase {

    override fun isProtected(location: Location): Boolean {
        return location.world?.name == Bukkit.getWorlds().firstOrNull()?.name
    }

    override fun isAllowedInProtectedLocation(
        player: Player,
        location: Location,
        event: Event
    ): Boolean {
        return (event is PlayerInteractEvent && event.clickedBlock?.type == Material.OAK_WALL_SIGN) || player.isOp
    }

}
