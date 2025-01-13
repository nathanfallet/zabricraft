package dev.zabricraft.replica.events

import dev.zabricraft.replica.Replica
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoin : Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        Replica.instance?.initPlayer(e.player)
    }

}
