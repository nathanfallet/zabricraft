package dev.zabricraft.events.players

import dev.zabricraft.usecases.players.ICreateUpdateZabriPlayerUseCase
import dev.zabricraft.usecases.spawn.IGetSetSpawnUseCase
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin(
    private val createUpdateZabriPlayerUseCase: ICreateUpdateZabriPlayerUseCase,
    private val getSetSpawnUseCase: IGetSetSpawnUseCase,
) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        createUpdateZabriPlayerUseCase(event.player)

        event.joinMessage =
            ChatColor.GRAY.toString() + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + ChatColor.GOLD + event.player.name
        event.player.sendMessage(ChatColor.YELLOW.toString() + "Bienvenue sur le serveur !")

        if (!event.player.hasPlayedBefore()) event.player.teleport(getSetSpawnUseCase())
    }

}
