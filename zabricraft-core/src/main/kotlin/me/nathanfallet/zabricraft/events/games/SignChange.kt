package me.nathanfallet.zabricraft.events.games

import me.nathanfallet.zabricraft.usecases.games.IListGameUseCase
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

class SignChange(
    private val listGameUseCase: IListGameUseCase
) : Listener {

    @EventHandler
    fun onSignChange(e: SignChangeEvent) {
        if (e.getLine(0)?.startsWith("[") != true || e.getLine(0)?.endsWith("]") != true) return
        if (!e.player.hasPermission("zabricraft.game.sign")) {
            e.player.sendMessage(ChatColor.RED.toString() + "Vous n'avez pas la permission de faire ça !")
            return
        }
        val name = e.getLine(0)
        val id = e.getLine(1)?.toInt()
        val game = listGameUseCase().firstOrNull {
            it.name == name && it.id == id
        } ?: run {
            e.isCancelled = true
            e.player.sendMessage(ChatColor.RED.toString() + "Entrez un nom et un numéro de partie valide !")
            return
        }
        game.signs.add(e.block.location)
    }

}
