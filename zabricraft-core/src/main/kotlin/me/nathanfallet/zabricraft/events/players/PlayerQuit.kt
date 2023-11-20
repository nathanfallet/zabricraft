package me.nathanfallet.zabricraft.events.players

import me.nathanfallet.zabricraft.usecases.players.IClearZabriPlayerCacheUseCase
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuit(
    private val clearZabriPlayerCacheUseCase: IClearZabriPlayerCacheUseCase,
) : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        clearZabriPlayerCacheUseCase(event.player.uniqueId)
        event.quitMessage =
            ChatColor.GRAY.toString() + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + ChatColor.GOLD + event.player.name
    }

}
