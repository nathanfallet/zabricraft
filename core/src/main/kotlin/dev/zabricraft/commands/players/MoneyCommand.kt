package dev.zabricraft.commands.players

import dev.kaccelero.commons.repositories.IGetModelUseCase
import dev.zabricraft.models.players.ZabriPlayer
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class MoneyCommand(
    private val getZabriPlayerUseCase: IGetModelUseCase<ZabriPlayer, UUID>,
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) return false
        val zabriPlayer = getZabriPlayerUseCase(sender.uniqueId) ?: return false
        sender.sendMessage(ChatColor.GREEN.toString() + "Money" + " : " + zabriPlayer.money + "$")
        return true
    }

}
