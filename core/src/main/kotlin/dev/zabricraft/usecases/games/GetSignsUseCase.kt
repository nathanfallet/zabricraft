package dev.zabricraft.usecases.games

import dev.zabricraft.models.games.IGame
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class GetSignsUseCase(
    private val plugin: JavaPlugin,
) : IGetSignsUseCase {

    override fun invoke(input: IGame): List<Location> {
        val path = input.name.lowercase().replace(" ", "_") + "." + "game" + input.id
        val signs = mutableListOf<Location>()
        val config = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "signs.yml"))
        val data = config.getConfigurationSection(path) ?: return signs
        val iterator = data.getKeys(false).iterator()
        while (iterator.hasNext()) {
            val sk = iterator.next()
            signs.add(
                Location(
                    data.getString("$sk.world")?.let { Bukkit.getWorld(it) },
                    data.getInt("$sk.x").toDouble(),
                    data.getInt("$sk.y").toDouble(),
                    data.getInt("$sk.z").toDouble()
                )
            )
        }
        return signs
    }

}
