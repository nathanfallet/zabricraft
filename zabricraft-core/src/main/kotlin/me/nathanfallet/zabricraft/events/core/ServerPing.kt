package me.nathanfallet.zabricraft.events.core

import me.nathanfallet.zabricraft.models.games.GameState
import me.nathanfallet.zabricraft.usecases.core.IGetMessageUseCase
import me.nathanfallet.zabricraft.usecases.games.IListGameUseCase
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.plugin.java.JavaPlugin

class ServerPing(
    private val plugin: JavaPlugin,
    private val listGameUseCase: IListGameUseCase,
    private val getMessageUseCase: IGetMessageUseCase
) : Listener {

    private val minecraftVersion: String = run {
        val version = Bukkit.getVersion()
        val start = version.indexOf("MC: ") + 4
        val end = version.length - 1
        version.substring(start, end)
    }

    private val secondLine: String
        get() {
            val games = listGameUseCase()
            val count = games.size
            if (count == 1) {
                return ChatColor.YELLOW.toString() + getMessageUseCase(games.first().state.key).replace(
                    "%d",
                    games.first().currentCountValue.toString()
                )
            }
            val playing = games.count { it.state == GameState.IN_GAME }
            return ChatColor.YELLOW.toString() + count + " parties" + ChatColor.GRAY + " | " + ChatColor.YELLOW + playing + " en cours"
        }

    @EventHandler
    fun onServerPing(event: ServerListPingEvent) {
        event.motd =
            ChatColor.GREEN.toString() + plugin.config.getString("server.name") + ChatColor.GRAY + " | " + ChatColor.AQUA + plugin.config.getString(
                "server.ip"
            ) + ChatColor.GRAY + " | " + ChatColor.RED + minecraftVersion + "\n" + secondLine
    }

}
