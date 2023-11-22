package me.nathanfallet.zabricraft.usecases.players

import kotlinx.datetime.Clock
import me.nathanfallet.zabricraft.usecases.scoreboards.IListGenerateScoreboardUseCase
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class UpdateOnlinePlayersUseCase(
    private val plugin: JavaPlugin,
    private val getZabriPlayersUseCase: IGetZabriPlayersUseCase,
    private val listGenerateScoreboardUseCase: IListGenerateScoreboardUseCase
) : IUpdateOnlinePlayersUseCase {

    override fun invoke() {
        val now = Clock.System.now()
        val zabriPlayers = getZabriPlayersUseCase()
        val generateScoreboardsUseCase = listGenerateScoreboardUseCase()
        val headerLines = listOf(
            ChatColor.AQUA.toString(),
            ChatColor.AQUA.toString() + ChatColor.BOLD + "Serveur :",
            ChatColor.WHITE.toString() + plugin.config.getString("server.name"),
            ChatColor.WHITE.toString() + zabriPlayers.size.toString() + " joueurs",
            ChatColor.GREEN.toString(),
            ChatColor.GREEN.toString() + ChatColor.BOLD + "Money" + " :"
        )
        val footerLines = listOf(
            ChatColor.YELLOW.toString(),
            ChatColor.YELLOW.toString() + ChatColor.BOLD + plugin.config.getString("server.ip")
        )

        Bukkit.getOnlinePlayers().forEach { player ->
            val zabriPlayer = zabriPlayers.firstOrNull { it.id == player.uniqueId } ?: return@forEach
            val lines = mutableListOf<String>()
            lines.addAll(headerLines)
            lines.add(ChatColor.WHITE.toString() + zabriPlayer.money.toString() + "$")
            generateScoreboardsUseCase.forEach {
                lines.addAll(it(player, zabriPlayer))
            }
            lines.addAll(footerLines)
            zabriPlayer.scoreboard.update(player, lines)

            if (!zabriPlayer.authenticated) {
                if (now - zabriPlayer.loginAt > 60.toDuration(DurationUnit.SECONDS)) {
                    player.kickPlayer("Delai de connexion expiré !")
                } else if (zabriPlayer.password.isNotEmpty()) {
                    player.sendMessage(ChatColor.RED.toString() + "Utilisez " + ChatColor.DARK_RED + "/login <password> " + ChatColor.RED + "pour vous connecter.")
                } else {
                    player.sendMessage(ChatColor.RED.toString() + "Utilisez " + ChatColor.DARK_RED + "/register <password> <password> " + ChatColor.RED + "pour vous inscrire.")
                }
            }
        }
    }

}
