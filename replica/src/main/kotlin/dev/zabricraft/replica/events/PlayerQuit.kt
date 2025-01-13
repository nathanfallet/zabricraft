package dev.zabricraft.replica.events

import dev.zabricraft.replica.Replica
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val zp = Replica.instance?.getPlayer(e.player.uniqueId) ?: return
        Replica.instance?.uninitPlayer(zp)
    }

}
