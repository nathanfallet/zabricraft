package me.nathanfallet.zabricraft.commands.auth

import me.nathanfallet.usecases.models.get.IGetModelUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.usecases.auth.IAuthenticatePlayerUseCase
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class LoginCommand(
    private val getZabriPlayerUseCase: IGetModelUseCase<ZabriPlayer, UUID>,
    private val authenticatePlayerUseCase: IAuthenticatePlayerUseCase
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player || args.size != 1) return false
        val zabriPlayer = getZabriPlayerUseCase(sender.uniqueId) ?: return false
        if (zabriPlayer.authenticated) {
            sender.sendMessage(ChatColor.RED.toString() + "Vous êtes déjà connecté !")
            return true
        }
        if (zabriPlayer.password.isEmpty()) {
            sender.sendMessage(
                ChatColor.RED.toString() + "Vous n'avez pas encore de compte ! Utilisez " + ChatColor.DARK_RED + "/register <password> <password>"
            )
            return true
        }
        if (!authenticatePlayerUseCase(zabriPlayer, args[0])) {
            sender.sendMessage(ChatColor.RED.toString() + "Mot de passe incorrect !")
            return true
        }
        sender.sendMessage(ChatColor.GREEN.toString() + "Authentification réussie !")
        return true
    }

}
