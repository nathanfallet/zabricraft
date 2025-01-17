package dev.zabricraft.replica.commands

import dev.zabricraft.replica.Replica
import dev.zabricraft.replica.usecases.players.IResetReplicaPlayerUseCase
import dev.zabricraft.usecases.messages.IGetMessageUseCase
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ReplicaCommand(
    private val getMessageUseCase: IGetMessageUseCase,
    private val resetReplicaPlayerUseCase: IResetReplicaPlayerUseCase,
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        // Check that args are specified
        if (args.isEmpty()) {
            sendHelp(sender, label)
            return true
        }

        // Go to command
        if (args[0].equals("goto", ignoreCase = true)) {
            // Permission check
            if (!sender.hasPermission("replica.admin")) {
                sender.sendMessage("§c" + getMessageUseCase("cmd-error-perm"))
                return true
            }
            // Convert to player
            if (sender !is Player) {
                sender.sendMessage("§c" + getMessageUseCase("cmd-error-not-a-player"))
                return true
            }
            sender.sendMessage("§c" + getMessageUseCase("cmd-goto-success"))
            sender.teleport(Location(Bukkit.getWorld(Replica.WORLD_NAME), 3.5, 65.0, 4.5))
            return true
        }
        // Build mode command
        if (args[0].equals("buildmode", ignoreCase = true)) {
            // Permission check
            if (!sender.hasPermission("replica.admin")) {
                sender.sendMessage("§c" + getMessageUseCase("cmd-error-perm"))
                return true
            }
            // Convert to player
            if (sender !is Player) {
                sender.sendMessage("§c" + getMessageUseCase("cmd-error-not-a-player"))
                return true
            }
            Replica.instance?.getPlayer(sender.uniqueId)?.let { zp ->
                // Set buildmode
                zp.buildmode = !zp.buildmode
                sender.sendMessage(
                    "§c" + getMessageUseCase(
                        if (zp.buildmode) "cmd-buildmode-enable"
                        else "cmd-buildmode-disable"
                    )
                )
            }
            return true
        }
        // Leave command
        if (args[0].equals("leave", ignoreCase = true)) {
            // Convert to player
            if (sender !is Player) {
                sender.sendMessage("§c" + getMessageUseCase("cmd-error-not-a-player"))
                return true
            }
            val zp = Replica.instance?.getPlayer(sender.uniqueId)?.takeIf {
                it.currentGame != 0
            } ?: run {
                sender.sendMessage("§c" + getMessageUseCase("chat-no-game"))
                return true
            }

            // Leave game
            resetReplicaPlayerUseCase(sender, zp)
            return true
        }

        // Command not found, send help
        sendHelp(sender, label)
        return true
    }

    fun sendHelp(sender: CommandSender, label: String) {
        if (sender.hasPermission("replica.admin")) {
            sender.sendMessage(
                "§e/" + label + " goto : " + getMessageUseCase("cmd-help-goto") + "\n" +
                        "§e/" + label + " buildmode : " + getMessageUseCase("cmd-help-buildmode") + "\n"
            )
        }
        sender.sendMessage("§e/" + label + " leave : " + getMessageUseCase("cmd-help-leave"))
    }

}
