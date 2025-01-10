package dev.zabricraft.events.players

import dev.zabricraft.usecases.spawn.IGetSetSpawnUseCase
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerRespawn(
    private val getSetSpawnUseCase: IGetSetSpawnUseCase,
) : Listener {

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        if (!event.isBedSpawn) {
            event.respawnLocation = getSetSpawnUseCase()
        }
    }

}
