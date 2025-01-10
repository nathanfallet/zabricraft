package dev.zabricraft.commands.auth

import dev.kaccelero.commons.repositories.IGetModelUseCase
import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.usecases.auth.IAuthenticatePlayerUseCase
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class RegisterCommand(
    private val getZabriPlayerUseCase: IGetModelUseCase<ZabriPlayer, UUID>,
    private val authenticatePlayerUseCase: IAuthenticatePlayerUseCase,
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player || args.size != 2) return false
        val zabriPlayer = getZabriPlayerUseCase(sender.uniqueId) ?: return false
        if (zabriPlayer.authenticated) {
            sender.sendMessage(ChatColor.RED.toString() + "Vous êtes déjà connecté !")
            return true
        }
        if (zabriPlayer.password.isNotEmpty()) {
            sender.sendMessage(
                ChatColor.RED.toString() + "Vous avez déjà un compte ! Utilisez " + ChatColor.DARK_RED + "/login <password>"
            )
            return true
        }
        if (args[0] != args[1]) {
            sender.sendMessage(ChatColor.RED.toString() + "Les deux mots de passe ne correspondent pas !")
            return true
        }
        authenticatePlayerUseCase(zabriPlayer, args[0])
        sender.sendMessage(ChatColor.GREEN.toString() + "Authentification réussie !")
        return true
    }

}
