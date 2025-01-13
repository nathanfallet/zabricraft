package dev.zabricraft.replica.events

import dev.zabricraft.models.games.GameState
import dev.zabricraft.replica.Replica
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object PlayerRespawn : Listener {

    @EventHandler
    fun onPlayerRespawn(e: PlayerRespawnEvent) {
        val zp = Replica.instance?.getPlayer(e.player.uniqueId)?.takeIf {
            it.currentGame != 0
        } ?: return
        val game = Replica.instance?.games?.find {
            it.id == zp.currentGame && it.state == GameState.IN_GAME
        } ?: return
        val location = Location(
            Bukkit.getWorld("Replica"),
            (4 + Replica.DISTANCE * 16 * (game.id - 1)).toDouble(),
            65.0,
            ((zp.plot - 1) * 32 + 9).toDouble()
        )
        location.yaw = -90f
        e.respawnLocation = location
    }

}
