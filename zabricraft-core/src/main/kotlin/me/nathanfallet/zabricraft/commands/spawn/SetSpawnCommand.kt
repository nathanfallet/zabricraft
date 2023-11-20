package me.nathanfallet.zabricraft.commands.spawn

import me.nathanfallet.zabricraft.usecases.spawn.IGetSetSpawnUseCase
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSpawnCommand(
    private val getSetSpawnUseCase: IGetSetSpawnUseCase
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) return false
        sender.sendMessage(ChatColor.GREEN.toString() + "Le spawn a bien été définit !")
        getSetSpawnUseCase(sender.location)
        return true
    }

}

