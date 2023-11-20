package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.zabricraft.models.games.IGame
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class SaveSignsUseCase(
    private val plugin: JavaPlugin
) : ISaveSignsUseCase {

    override fun invoke(input: IGame) {
        val path = input.name.lowercase().replace(" ", "_") + "." + "game" + input.id
        val file = File(plugin.dataFolder, "signs.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        var i = 1
        val iterator = input.signs.iterator()
        while (iterator.hasNext()) {
            val l = iterator.next()
            config["$path.sign$i.world"] = l.world?.name
            config["$path.sign$i.x"] = l.blockX
            config["$path.sign$i.y"] = l.blockY
            config["$path.sign$i.z"] = l.blockZ
            i++
        }
        while (config.contains("$path.sign$i")) {
            config["$path.sign$i"] = null
            i++
        }
        try {
            config.save(file)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

}
