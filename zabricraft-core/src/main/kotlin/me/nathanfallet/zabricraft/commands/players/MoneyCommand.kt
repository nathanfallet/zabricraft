package me.nathanfallet.zabricraft.commands.players

import me.nathanfallet.usecases.models.get.IGetModelUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class MoneyCommand(
    private val getZabriPlayerUseCase: IGetModelUseCase<ZabriPlayer, UUID>
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) return false
        val zabriPlayer = getZabriPlayerUseCase(sender.uniqueId) ?: return false
        sender.sendMessage(ChatColor.GREEN.toString() + "Money" + " : " + zabriPlayer.money + "$")
        return true
    }

}
