package me.nathanfallet.zabricraft.commands.leaderboards

import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import me.nathanfallet.zabricraft.usecases.leaderboards.IGetGenerateLeaderboardsUseCase
import me.nathanfallet.zabricraft.usecases.leaderboards.IGetLeaderboardsUseCase
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LeaderboardCommand(
    private val getLeaderboardsUseCase: IGetLeaderboardsUseCase,
    private val getGenerateLeaderboardsUseCase: IGetGenerateLeaderboardsUseCase
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        when (args.getOrNull(0)?.lowercase()) {
            "list" -> {
                sender.sendMessage(ChatColor.YELLOW.toString() + "------ " + ChatColor.GOLD + "Classements existants " + ChatColor.YELLOW + "------")
                getLeaderboardsUseCase().forEach {
                    sender.sendMessage(ChatColor.GOLD.toString() + it.key + ChatColor.YELLOW + " : " + it.value.type)
                }
            }

            "types" -> {
                sender.sendMessage(ChatColor.YELLOW.toString() + "------ " + ChatColor.GOLD + "Type de classements disponibles " + ChatColor.YELLOW + "------")
                getGenerateLeaderboardsUseCase().forEach {
                    sender.sendMessage(ChatColor.GOLD.toString() + it.key + ChatColor.YELLOW + " : " + it.value.title)
                }
            }

            "create" -> {
                if (sender !is Player) return false
                if (args.size < 3) {
                    sender.sendMessage(ChatColor.RED.toString() + "/leaderboard create <nom> <type>")
                    return true
                }
                if (getLeaderboardsUseCase().containsKey(args[1])) {
                    sender.sendMessage(ChatColor.RED.toString() + "Le classement " + ChatColor.DARK_RED + args[1] + ChatColor.RED + " existe déjà !")
                    return true
                }
                val leaderboard = Leaderboard(
                    sender.location,
                    args[2], 10
                )
                getLeaderboardsUseCase()[args[1]] = leaderboard
                sender.sendMessage(ChatColor.GREEN.toString() + "Le classement " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " a bien été créé !")
            }

            "info" -> args.getOrNull(1)?.let { key ->
                leaderboard(key, sender)?.let { leaderboard ->
                    sender.sendMessage(ChatColor.GOLD.toString() + key + " : " + ChatColor.YELLOW + leaderboard.type)
                } ?: Unit
            } ?: sender.sendMessage(ChatColor.RED.toString() + "/leaderboard info <nom>")


            "remove" -> args.getOrNull(1)?.let { key ->
                leaderboard(key, sender)?.let { leaderboard ->
                    getLeaderboardsUseCase().remove(key)
                    leaderboard.kill()
                    sender.sendMessage(ChatColor.GREEN.toString() + "Le classement " + ChatColor.YELLOW + key + ChatColor.GREEN + " a bien été supprimé !")
                } ?: Unit
            } ?: sender.sendMessage(ChatColor.RED.toString() + "/leaderboard remove <nom>")


            "settype" -> args.getOrNull(1)?.let { key ->
                val type = args.getOrNull(2) ?: return@let null
                leaderboard(key, sender)?.let { leaderboard ->
                    getLeaderboardsUseCase()[key] = leaderboard.copy(type = type)
                    leaderboard.kill()
                    sender.sendMessage(ChatColor.GREEN.toString() + "Le classement " + ChatColor.YELLOW + key + ChatColor.GREEN + " est maintenant de type " + type + " !")
                } ?: Unit
            } ?: sender.sendMessage(ChatColor.RED.toString() + "/leaderboard settype <nom> <type>")


            "setlimit" -> args.getOrNull(1)?.let { key ->
                val limit = args.getOrNull(2)?.toInt() ?: return@let null
                leaderboard(key, sender)?.let { leaderboard ->
                    getLeaderboardsUseCase()[key] = leaderboard.copy(limit = limit)
                    leaderboard.kill()
                    sender.sendMessage(ChatColor.GREEN.toString() + "Le classement " + ChatColor.YELLOW + key + ChatColor.GREEN + " a maintenant " + limit + " lignes !")
                } ?: Unit
            } ?: sender.sendMessage(ChatColor.RED.toString() + "/leaderboard setlimit <nom> <limit>")

            else -> sendHelp(sender)
        }
        return true
    }

    private fun leaderboard(key: String, sender: CommandSender): Leaderboard? {
        return getLeaderboardsUseCase()[key] ?: run {
            sender.sendMessage(ChatColor.RED.toString() + "Le classement " + ChatColor.DARK_RED + key + ChatColor.RED + " n'existe pas !")
            null
        }
    }

    private fun sendHelp(sender: CommandSender) {
        sender.sendMessage(
            """
            ${ChatColor.YELLOW}----- ${ChatColor.GOLD}Aide du /leaderboard ${ChatColor.YELLOW}-----
            ${ChatColor.GOLD}/leaderboard create <nom> <type> ${ChatColor.YELLOW}: Créer un classement.
            ${ChatColor.GOLD}/leaderboard list ${ChatColor.YELLOW}: Afficher les classements existants.
            ${ChatColor.GOLD}/leaderboard info <nom> ${ChatColor.YELLOW}: Afficher les informations sur un classement.
            ${ChatColor.GOLD}/leaderboard remove <nom> ${ChatColor.YELLOW}: Supprimer un classement.
            ${ChatColor.GOLD}/leaderboard types ${ChatColor.YELLOW}: Afficher les types de classements disponibles.
            ${ChatColor.GOLD}/leaderboard settype <nom> <type> ${ChatColor.YELLOW}: Changer le type d'un classement.
            ${ChatColor.GOLD}/leaderboard setlimit <nom> <top> ${ChatColor.YELLOW}: Changer le nombre de lignes d'un classement.
            """.trimIndent()
        )
    }

}
