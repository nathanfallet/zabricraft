package me.nathanfallet.zabricraft

import me.nathanfallet.zabricraft.commands.spawn.SetSpawnCommand
import me.nathanfallet.zabricraft.commands.spawn.SpawnCommand
import me.nathanfallet.zabricraft.di.ZabriKoin
import me.nathanfallet.zabricraft.events.players.PlayerJoin
import me.nathanfallet.zabricraft.events.players.PlayerQuit
import me.nathanfallet.zabricraft.events.players.PlayerRespawn
import me.nathanfallet.zabricraft.usecases.core.IGetSetMessageUseCase
import me.nathanfallet.zabricraft.usecases.leaderboards.IGetLeaderboardsUseCase
import me.nathanfallet.zabricraft.usecases.leaderboards.ISaveLeaderboardsUseCase
import me.nathanfallet.zabricraft.usecases.players.IClearZabriPlayersCacheUseCase
import me.nathanfallet.zabricraft.usecases.players.ICreateUpdateZabriPlayerUseCase
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.GlobalContext.get
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

        // TODO: Generators

        // TODO: Events
        Bukkit.getPluginManager().registerEvents(get().get<PlayerJoin>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<PlayerQuit>(), this)
        Bukkit.getPluginManager().registerEvents(get().get<PlayerRespawn>(), this)

        // TODO: Commands
        getCommand("spawn")?.setExecutor(get().get<SpawnCommand>())
        getCommand("setspawn")?.setExecutor(get().get<SetSpawnCommand>())

        val getLeaderboardsUseCase = get().get<IGetLeaderboardsUseCase>()
        val saveLeaderboardsUseCase = get().get<ISaveLeaderboardsUseCase>()
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            // TODO: Update games

            // TODO: Update player scoreboards

            // TODO: Auth

            // TODO: Leaderboards

            getLeaderboardsUseCase().forEach { (_, leaderboard) ->
                leaderboard.update()
            }
        }, 0, 20)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            saveLeaderboardsUseCase()
        }, 6000, 6000)
    }

    override fun onDisable() {
        val clearZabriPlayersCacheUseCase = get().get<IClearZabriPlayersCacheUseCase>()
        clearZabriPlayersCacheUseCase()

        // TODO: Games

        // TODO: Generators

        val getLeaderboardsUseCase = get().get<IGetLeaderboardsUseCase>()
        val saveLeaderboardsUseCase = get().get<ISaveLeaderboardsUseCase>()
        saveLeaderboardsUseCase()
        getLeaderboardsUseCase().forEach { (_, leaderboard) ->
            leaderboard.kill()
        }
        getLeaderboardsUseCase.clear()

        clearCustomEntities()
        ZabriKoin.stop()
    }

    private fun clearCustomEntities() {
        Bukkit.getWorlds().flatMap { it.entities }.filter { it.customName?.startsWith("§") ?: false }
            .forEach { it.remove() }
    }

}
