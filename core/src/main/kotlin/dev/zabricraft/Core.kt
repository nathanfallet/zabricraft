package dev.zabricraft

import dev.kaccelero.commons.repositories.IListModelUseCase
import dev.zabricraft.commands.auth.LoginCommand
import dev.zabricraft.commands.auth.RegisterCommand
import dev.zabricraft.commands.leaderboards.LeaderboardCommand
import dev.zabricraft.commands.players.MoneyCommand
import dev.zabricraft.commands.spawn.SetSpawnCommand
import dev.zabricraft.commands.spawn.SpawnCommand
import dev.zabricraft.database.Database
import dev.zabricraft.di.ZabriKoin
import dev.zabricraft.events.auth.PlayerAuthentication
import dev.zabricraft.events.core.ServerPing
import dev.zabricraft.events.games.SignChange
import dev.zabricraft.events.players.*
import dev.zabricraft.events.rules.WorldProtection
import dev.zabricraft.models.leaderboards.Leaderboard
import dev.zabricraft.usecases.core.ISetMessageUseCase
import dev.zabricraft.usecases.games.IClearGamesUseCase
import dev.zabricraft.usecases.games.IListGameUseCase
import dev.zabricraft.usecases.games.IUpdateGameUseCase
import dev.zabricraft.usecases.leaderboards.*
import dev.zabricraft.usecases.players.IClearZabriPlayersCacheUseCase
import dev.zabricraft.usecases.players.ICreateUpdateZabriPlayerUseCase
import dev.zabricraft.usecases.players.IUpdateOnlinePlayersUseCase
import dev.zabricraft.usecases.rules.IAddWorldProtectionRuleUseCase
import dev.zabricraft.usecases.rules.IClearWorldProtectionRuleUseCase
import dev.zabricraft.usecases.rules.IWorldProtectionRuleUseCase
import dev.zabricraft.usecases.scoreboards.IClearGenerateScoreboardUseCase
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import java.io.File

class Core : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        reloadConfig()

        try {
            ZabriKoin.start(this)
        } catch (e: Exception) {
            logger.severe("Failed to connect to database: ${e.message}")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        val setMessageUseCase = get().get<ISetMessageUseCase>()
        val messagesFile = File(dataFolder, "messages.yml")
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false)
        }
        val messages = YamlConfiguration.loadConfiguration(messagesFile)
        messages.getKeys(false).forEach { id ->
            setMessageUseCase(id, messages.getString(id) ?: "")
        }

        val createUpdateZabriPlayerUseCase = get().get<ICreateUpdateZabriPlayerUseCase>()
        Bukkit.getOnlinePlayers().forEach {
            createUpdateZabriPlayerUseCase(it)
        }

        clearCustomEntities()

        val addWorldProtectionRuleUseCase = get().get<IAddWorldProtectionRuleUseCase>()
        if (config.getBoolean("server.spawn_protection")) {
            addWorldProtectionRuleUseCase(get().get<IWorldProtectionRuleUseCase>(named("spawn_protection")))
        }

        val setGenerateLeaderboardUseCase = get().get<ISetGenerateLeaderboardUseCase>()
        listOf("money", "score", "victories").forEach {
            setGenerateLeaderboardUseCase(it, get().get<IGenerateLeaderboardUseCase>(named(it)))
        }

        Bukkit.getPluginManager().registerEvents(get().get<PlayerAuthentication>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<PlayerChat>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<PlayerInteract>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<PlayerJoin>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<PlayerQuit>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<PlayerRespawn>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<ServerPing>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<SignChange>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<WorldProtection>(), this)

        getCommand("login")?.setExecutor(get().get<LoginCommand>())
        getCommand("register")?.setExecutor(get().get<RegisterCommand>())
        getCommand("leaderboard")?.setExecutor(get().get<LeaderboardCommand>())
        getCommand("money")?.setExecutor(get().get<MoneyCommand>())
        getCommand("spawn")?.setExecutor(get().get<SpawnCommand>())
        getCommand("setspawn")?.setExecutor(get().get<SetSpawnCommand>())

        val listGameUseCase = get().get<IListGameUseCase>()
        val updateGameUseCase = get().get<IUpdateGameUseCase>()
        val updateOnlinePlayersUseCase = get().get<IUpdateOnlinePlayersUseCase>()
        val listLeaderboardUseCase = get().get<IListModelUseCase<Leaderboard>>(named<Leaderboard>())
        val saveLeaderboardsUseCase = get().get<ISaveLeaderboardsUseCase>()
        val updateLeaderboardUseCase = get().get<IUpdateLeaderboardUseCase>()
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            listGameUseCase().forEach { updateGameUseCase(it) }
            updateOnlinePlayersUseCase()
            listLeaderboardUseCase().forEach { updateLeaderboardUseCase(it) }
        }, 0, 20)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            saveLeaderboardsUseCase()
        }, 6000, 6000)
    }

    override fun onDisable() {
        val clearGameUseCase = get().get<IClearGamesUseCase>()
        clearGameUseCase()

        val clearZabriPlayersCacheUseCase = get().get<IClearZabriPlayersCacheUseCase>()
        clearZabriPlayersCacheUseCase()

        val saveLeaderboardsUseCase = get().get<ISaveLeaderboardsUseCase>()
        val clearLeaderboardUseCase = get().get<IClearLeaderboardsUseCase>()
        saveLeaderboardsUseCase()
        clearLeaderboardUseCase()

        val getWorldProtectionRuleUseCase = get().get<IClearWorldProtectionRuleUseCase>()
        val clearGenerateLeaderboardUseCase = get().get<IClearGenerateLeaderboardUseCase>()
        val getGenerateScoreboardsUseCase = get().get<IClearGenerateScoreboardUseCase>()
        getWorldProtectionRuleUseCase()
        clearGenerateLeaderboardUseCase()
        getGenerateScoreboardsUseCase()

        val database = get().get<Database>()
        database.disconnect()

        clearCustomEntities()
        ZabriKoin.stop()
    }

    private fun clearCustomEntities() {
        Bukkit.getWorlds().flatMap { it.entities }.filter { it.customName?.startsWith("§") ?: false }
            .forEach { it.remove() }
    }

}
