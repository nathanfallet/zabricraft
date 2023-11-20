package me.nathanfallet.zabricraft.usecases.games

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class GetSignsUseCase(
    private val plugin: JavaPlugin
) : IGetSignsUseCase {

    override fun invoke(input1: String, input2: Int): List<Location> {
        val signs = mutableListOf<Location>()
        val path = input1.lowercase().replace(" ", "_") + "." + "game" + input2
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
