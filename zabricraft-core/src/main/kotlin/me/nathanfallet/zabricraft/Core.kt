package me.nathanfallet.zabricraft

import me.nathanfallet.zabricraft.commands.auth.LoginCommand
import me.nathanfallet.zabricraft.commands.auth.RegisterCommand
import me.nathanfallet.zabricraft.commands.leaderboards.LeaderboardCommand
import me.nathanfallet.zabricraft.commands.players.MoneyCommand
import me.nathanfallet.zabricraft.commands.spawn.SetSpawnCommand
import me.nathanfallet.zabricraft.commands.spawn.SpawnCommand
import me.nathanfallet.zabricraft.database.Database
import me.nathanfallet.zabricraft.di.ZabriKoin
import me.nathanfallet.zabricraft.events.auth.PlayerAuthentication
import me.nathanfallet.zabricraft.events.core.ServerPing
import me.nathanfallet.zabricraft.events.core.WorldProtection
import me.nathanfallet.zabricraft.events.games.SignChange
import me.nathanfallet.zabricraft.events.players.*
import me.nathanfallet.zabricraft.usecases.core.IGetSetMessageUseCase
import me.nathanfallet.zabricraft.usecases.games.IGetAddGamesUseCase
import me.nathanfallet.zabricraft.usecases.games.IUpdateGameUseCase
import me.nathanfallet.zabricraft.usecases.leaderboards.*
import me.nathanfallet.zabricraft.usecases.players.IClearZabriPlayersCacheUseCase
import me.nathanfallet.zabricraft.usecases.players.ICreateUpdateZabriPlayerUseCase
import me.nathanfallet.zabricraft.usecases.players.IUpdateOnlinePlayersUseCase
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

        val getSetMessageUseCase = get().get<IGetSetMessageUseCase>()
        val messagesFile = File(dataFolder, "messages.yml")
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false)
        }
        val messages = YamlConfiguration.loadConfiguration(messagesFile)
        messages.getKeys(false).forEach { id ->
            getSetMessageUseCase(id, messages.getString(id) ?: "")
        }

        val createUpdateZabriPlayerUseCase = get().get<ICreateUpdateZabriPlayerUseCase>()
        Bukkit.getOnlinePlayers().forEach {
            createUpdateZabriPlayerUseCase(it)
        }

        clearCustomEntities()

        val getGenerateLeaderboardsUseCase = get().get<IGetGenerateLeaderboardsUseCase>()
        listOf("money", "score", "victories").forEach {
            getGenerateLeaderboardsUseCase()[it] = get().get<IGenerateLeaderboardUseCase>(named(it))
        }
        // TODO: Other generators

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

        val getAddGamesUseCase = get().get<IGetAddGamesUseCase>()
        val updateGameUseCase = get().get<IUpdateGameUseCase>()
        val updateOnlinePlayersUseCase = get().get<IUpdateOnlinePlayersUseCase>()
        val getLeaderboardsUseCase = get().get<IGetLeaderboardsUseCase>()
        val saveLeaderboardsUseCase = get().get<ISaveLeaderboardsUseCase>()
        val updateLeaderboardUseCase = get().get<IUpdateLeaderboardUseCase>()
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            getAddGamesUseCase().forEach { updateGameUseCase(it) }
            updateOnlinePlayersUseCase()
            getLeaderboardsUseCase().values.forEach { updateLeaderboardUseCase(it) }
        }, 0, 20)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            saveLeaderboardsUseCase()
        }, 6000, 6000)
    }

    override fun onDisable() {
        val getAddGamesUseCase = get().get<IGetAddGamesUseCase>()
        getAddGamesUseCase().forEach { it.reset() }
        getAddGamesUseCase.clear()

        val clearZabriPlayersCacheUseCase = get().get<IClearZabriPlayersCacheUseCase>()
        clearZabriPlayersCacheUseCase()

        val getLeaderboardsUseCase = get().get<IGetLeaderboardsUseCase>()
        val saveLeaderboardsUseCase = get().get<ISaveLeaderboardsUseCase>()
        saveLeaderboardsUseCase()
        getLeaderboardsUseCase().forEach { (_, leaderboard) ->
            leaderboard.kill()
        }
        getLeaderboardsUseCase.clear()

        val getGenerateLeaderboardsUseCase = get().get<IGetGenerateLeaderboardsUseCase>()
        getGenerateLeaderboardsUseCase.clear()
        // TODO: Other generators

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
