package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class GetLeaderboardsUseCase(
    private val plugin: JavaPlugin
) : IGetLeaderboardsUseCase {

    private var leaderboards: MutableMap<String, Leaderboard>? = null

    override fun invoke(): MutableMap<String, Leaderboard> {
        if (leaderboards == null) {
            leaderboards = mutableMapOf()
            val file = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "leaderboards.yml"))
            val var3 = file.getKeys(false).iterator()
            while (var3.hasNext()) {
                val key = var3.next()
                val location = Location(
                    file.getString("$key.location.world")?.let { Bukkit.getWorld(it) },
                    file.getDouble("$key.location.x"),
                    file.getDouble("$key.location.y"),
                    file.getDouble("$key.location.z")
                )
                val type = file.getString("$key.type") ?: continue
                val limit = file.getInt("$key.limit")
                leaderboards?.put(key, Leaderboard(location, type, limit))
            }
        }

        return leaderboards!!
    }

    override fun clear() {
        leaderboards = null
    }

}
