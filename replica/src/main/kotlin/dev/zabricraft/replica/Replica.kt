package dev.zabricraft.replica

import dev.zabricraft.replica.commands.ReplicaCommand
import dev.zabricraft.replica.di.ReplicaKoin
import dev.zabricraft.replica.events.*
import dev.zabricraft.replica.models.Picture
import dev.zabricraft.replica.models.ReplicaGame
import dev.zabricraft.replica.models.ReplicaPlayer
import dev.zabricraft.replica.usecases.world.ICreateReplicaWorldUseCase
import dev.zabricraft.usecases.games.IAddGameUseCase
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.GlobalContext.get
import java.util.*

class Replica : JavaPlugin() {

    companion object {

        const val WORLD_NAME = "Replica"
        const val DISTANCE = 5
        const val SCORE = 10
        const val MONEY = 10

        var instance: Replica? = null

    }

    val pictures = mutableListOf<Picture>()
    val games = mutableListOf<ReplicaGame>()

    val players = mutableListOf<ReplicaPlayer>()

    override fun onEnable() {
        instance = this

        saveDefaultConfig()
        reloadConfig()

        ReplicaKoin.start(this)

        val createReplicaWorldUseCase = get().get<ICreateReplicaWorldUseCase>()
        createReplicaWorldUseCase()

        Bukkit.getOnlinePlayers().forEach {
            initPlayer(it)
        }

        games.clear()
        val gamesAmount = getConfig().getInt("games-amount")
        var i = 1
        while (i <= gamesAmount) {
            games.add(ReplicaGame(i))
            i++
        }
        if (games.isEmpty()) {
            logger.severe("You have to add one game or more to use this plugin !")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }
        val addGameUseCase = get().get<IAddGameUseCase>()
        games.forEach { game ->
            addGameUseCase(game)
            game.loadPlots()
        }

        pictures.clear()
        getConfig().getConfigurationSection("pictures")?.let { pictureFile ->
            pictureFile.getKeys(false).forEach { s ->
                val picture = Picture(pictureFile.getString("$s.name") ?: "Unknown")
                val blocks = pictureFile.getString("$s.blocks")?.split(";") ?: return@forEach
                for (x in 0..7) {
                    for (y in 0..7) {
                        picture.blocks[Pair(x, y)] = Integer.parseInt(blocks[y * 8 + x])
                    }
                }
                pictures.add(picture)
            }
        }
        if (pictures.isEmpty()) {
            logger.severe("You have to add one picture or more to use this plugin !")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        val pm = Bukkit.getPluginManager()
        pm.registerEvents(get().get<BlockUpdate>(), this)
        pm.registerEvents(get().get<EntityDamage>(), this)
        pm.registerEvents(get().get<PlayerCommandPreprocess>(), this)
        pm.registerEvents(get().get<PlayerJoin>(), this)
        pm.registerEvents(get().get<PlayerQuit>(), this)
        pm.registerEvents(get().get<PlayerRespawn>(), this)

        getCommand("replica")?.setExecutor(get().get<ReplicaCommand>())

        // TODO: Leaderboard/scoreboard generator
    }

    override fun onDisable() {
        games.clear()
        pictures.clear()

        players.clear()

        instance = null
    }

    fun getPlayer(uuid: UUID): ReplicaPlayer? {
        return players.firstOrNull { it.uuid == uuid }
    }

    fun initPlayer(player: Player) {
        players.add(
            ReplicaPlayer(
                player.uniqueId,
                0,
                false,
                false,
                false,
                0,
                0,
                0
            )
        )
    }

    fun uninitPlayer(player: ReplicaPlayer) {
        players.removeIf { it.uuid == player.uuid }
    }

}
