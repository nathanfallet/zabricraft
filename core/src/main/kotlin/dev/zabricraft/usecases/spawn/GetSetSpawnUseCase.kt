package dev.zabricraft.usecases.spawn

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class GetSetSpawnUseCase(
    private val plugin: JavaPlugin,
) : IGetSetSpawnUseCase {

    private var spawn: Location? = null

    override fun invoke(): Location {
        if (spawn == null) {
            val f = File(plugin.dataFolder, "spawn.yml")
            if (!f.exists()) {
                return (Bukkit.getWorlds()[0] as World).spawnLocation
            }
            val config = YamlConfiguration.loadConfiguration(f)
            spawn = Location(
                config.getString("world")?.let { Bukkit.getWorld(it) },
                config.getDouble("x"),
                config.getDouble("y"),
                config.getDouble("z")
            )
            spawn?.yaw = config.getLong("yaw").toFloat()
            spawn?.pitch = config.getLong("pitch").toFloat()
        }
        return spawn!!
    }

    override fun invoke(input: Location) {
        this.spawn = input
        val file = File(plugin.dataFolder, "spawn.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        config["world"] = input.world?.name
        config["x"] = input.x
        config["y"] = input.y
        config["z"] = input.z
        config["yaw"] = input.yaw
        config["pitch"] = input.pitch

        try {
            config.save(file)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

}
