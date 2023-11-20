package me.nathanfallet.zabricraft.usecases.leaderboards

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class SaveLeaderboardsUseCase(
    private val plugin: JavaPlugin,
    private val getLeaderboardsUseCase: IGetLeaderboardsUseCase
) : ISaveLeaderboardsUseCase {

    override fun invoke() {
        val empty = File(plugin.dataFolder, ".empty")
        val source = File(plugin.dataFolder, "leaderboards.yml")
        val file = YamlConfiguration.loadConfiguration(empty)
        val leaderboards = getLeaderboardsUseCase()
        val iterator = leaderboards.keys.iterator()

        while (iterator.hasNext()) {
            val key = iterator.next()
            val leaderboard = leaderboards[key] ?: continue
            file["$key.location.world"] = leaderboard.location.world!!.name
            file["$key.location.x"] = leaderboard.location.x
            file["$key.location.y"] = leaderboard.location.y
            file["$key.location.z"] = leaderboard.location.z
            file["$key.type"] = leaderboard.type
            file["$key.limit"] = leaderboard.limit
        }

        try {
            file.save(source)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

}
