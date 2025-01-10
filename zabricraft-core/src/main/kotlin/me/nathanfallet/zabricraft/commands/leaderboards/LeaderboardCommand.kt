package me.nathanfallet.zabricraft.commands.leaderboards

import dev.kaccelero.commons.repositories.*
import me.nathanfallet.zabricraft.models.leaderboards.CreateLeaderboardPayload
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import me.nathanfallet.zabricraft.models.leaderboards.UpdateLeaderboardPayload
import me.nathanfallet.zabricraft.usecases.leaderboards.IListGenerateLeaderboardUseCase
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LeaderboardCommand(
    private val listLeaderboardUseCase: IListModelUseCase<Leaderboard>,
    private val createLeaderboardsUseCase: ICreateModelUseCase<Leaderboard, CreateLeaderboardPayload>,
    private val getLeaderboardUseCase: IGetModelUseCase<Leaderboard, String>,
    private val updateLeaderboardUseCase: IUpdateModelUseCase<Leaderboard, String, UpdateLeaderboardPayload>,
    private val deleteLeaderboardUseCase: IDeleteModelUseCase<Leaderboard, String>,
    private val getGenerateLeaderboardsUseCase: IListGenerateLeaderboardUseCase,
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        when (args.getOrNull(0)?.lowercase()) {
            "list" -> {
                sender.sendMessage(ChatColor.YELLOW.toString() + "------ " + ChatColor.GOLD + "Classements existants " + ChatColor.YELLOW + "------")
                listLeaderboardUseCase().forEach {
                    sender.sendMessage(ChatColor.GOLD.toString() + it.id + ChatColor.YELLOW + " : " + it.type)
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
                createLeaderboardsUseCase(CreateLeaderboardPayload(args[1], sender.location, args[2], 10)) ?: run {
                    sender.sendMessage(ChatColor.RED.toString() + "Le classement " + ChatColor.DARK_RED + args[1] + ChatColor.RED + " existe déjà !")
                    return true
                }
                sender.sendMessage(ChatColor.GREEN.toString() + "Le classement " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " a bien été créé !")
            }

            "info" -> args.getOrNull(1)?.let { key ->
                val leaderboard = getLeaderboardUseCase(key) ?: run {
                    sender.sendMessage(ChatColor.RED.toString() + "Le classement " + ChatColor.DARK_RED + key + ChatColor.RED + " n'existe pas !")
                    return true
                }
                sender.sendMessage(ChatColor.GOLD.toString() + key + " : " + ChatColor.YELLOW + leaderboard.type)
            } ?: sender.sendMessage(ChatColor.RED.toString() + "/leaderboard info <nom>")


            "remove" -> args.getOrNull(1)?.let { key ->
                if (!deleteLeaderboardUseCase(key)) {
                    sender.sendMessage(ChatColor.RED.toString() + "Le classement " + ChatColor.DARK_RED + key + ChatColor.RED + " n'existe pas !")
                    return true
                }
                sender.sendMessage(ChatColor.GREEN.toString() + "Le classement " + ChatColor.YELLOW + key + ChatColor.GREEN + " a bien été supprimé !")
            } ?: sender.sendMessage(ChatColor.RED.toString() + "/leaderboard remove <nom>")


            "settype" -> args.getOrNull(1)?.let { key ->
                val type = args.getOrNull(2) ?: return@let null
                if (updateLeaderboardUseCase(key, UpdateLeaderboardPayload(type = type)) == null) {
                    sender.sendMessage(ChatColor.RED.toString() + "Le classement " + ChatColor.DARK_RED + key + ChatColor.RED + " n'existe pas !")
                    return true
                }
                sender.sendMessage(ChatColor.GREEN.toString() + "Le classement " + ChatColor.YELLOW + key + ChatColor.GREEN + " est maintenant de type " + type + " !")
            } ?: sender.sendMessage(ChatColor.RED.toString() + "/leaderboard settype <nom> <type>")


            "setlimit" -> args.getOrNull(1)?.let { key ->
                val limit = args.getOrNull(2)?.toInt() ?: return@let null
                if (updateLeaderboardUseCase(key, UpdateLeaderboardPayload(limit = limit)) == null) {
                    sender.sendMessage(ChatColor.RED.toString() + "Le classement " + ChatColor.DARK_RED + key + ChatColor.RED + " n'existe pas !")
                    return true
                }
                sender.sendMessage(ChatColor.GREEN.toString() + "Le classement " + ChatColor.YELLOW + key + ChatColor.GREEN + " a maintenant " + limit + " lignes !")
            } ?: sender.sendMessage(ChatColor.RED.toString() + "/leaderboard setlimit <nom> <limit>")

            else -> sendHelp(sender)
        }
        return true
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
