package me.nathanfallet.zabricraft.commands.spawn

import me.nathanfallet.zabricraft.usecases.players.IGetBukkitPlayerByNameUseCase
import me.nathanfallet.zabricraft.usecases.spawn.IGetSetSpawnUseCase
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SpawnCommand(
    private val getBukkitPlayerByNameUseCase: IGetBukkitPlayerByNameUseCase,
    private val getSetSpawnUseCase: IGetSetSpawnUseCase
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (args.size == 1 && sender.hasPermission("zabricraft.spawn.others")) {
            getBukkitPlayerByNameUseCase(args[0])?.takeIf { it.isOnline }?.let { player ->
                player.sendMessage(ChatColor.GOLD.toString() + "Téléportation au spawn...")
                player.teleport(getSetSpawnUseCase())
            }
        } else if (sender is Player) {
            sender.sendMessage(ChatColor.GOLD.toString() + "Téléportation au spawn...")
            sender.teleport(getSetSpawnUseCase())
        }
        return true
    }

}
