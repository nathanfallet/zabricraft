package me.nathanfallet.zabricraft.repositories.leaderboards

import me.nathanfallet.usecases.users.IUser
import me.nathanfallet.zabricraft.models.leaderboards.CreateLeaderboardPayload
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import me.nathanfallet.zabricraft.models.leaderboards.UpdateLeaderboardPayload
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class LeaderboardsRepository(
    private val plugin: JavaPlugin
) : ILeaderboardsRepository {

    private var leaderboards: MutableList<Leaderboard>? = null

    private fun loadIfNeeded() {
        if (leaderboards != null) return
        val file = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "leaderboards.yml"))
        leaderboards = file.getKeys(false).mapNotNull {
            Leaderboard(
                it,
                Location(
                    file.getString("$it.location.world")?.let(Bukkit::getWorld),
                    file.getDouble("$it.location.x"),
                    file.getDouble("$it.location.y"),
                    file.getDouble("$it.location.z")
                ),
                file.getString("$it.type") ?: return@mapNotNull null,
                file.getInt("$it.limit")
            )
        }.toMutableList()
    }

    override fun list(): List<Leaderboard> {
        loadIfNeeded()
        return leaderboards!!
    }

    override fun list(limit: Long, offset: Long): List<Leaderboard> {
        loadIfNeeded()
        return leaderboards!!.subList(
            offset.toInt(),
            leaderboards!!.size.coerceAtMost(offset.toInt() + limit.toInt())
        )
    }

    override fun get(id: String): Leaderboard? {
        loadIfNeeded()
        return leaderboards!!.firstOrNull { it.id == id }
    }

    override fun create(payload: CreateLeaderboardPayload, user: IUser?): Leaderboard? {
        loadIfNeeded()
        if (get(payload.id) != null) return null
        return Leaderboard(
            payload.id,
            payload.location,
            payload.type,
            payload.limit
        ).also {
            leaderboards!!.add(it)
        }
    }

    override fun update(id: String, payload: UpdateLeaderboardPayload, user: IUser?): Boolean {
        loadIfNeeded()
        val index = leaderboards!!.indexOfFirst { it.id == id }.takeIf { it != -1 } ?: return false
        val leaderboard = leaderboards!![index]
        leaderboards!![index] = leaderboard.copy(
            location = payload.location ?: leaderboard.location,
            type = payload.type ?: leaderboard.type,
            limit = payload.limit ?: leaderboard.limit
        )
        leaderboard.kill()
        return true
    }

    override fun delete(id: String): Boolean {
        loadIfNeeded()
        val leaderboard = leaderboards!!.firstOrNull { it.id == id } ?: return false
        leaderboards!!.remove(leaderboard)
        leaderboard.kill()
        return true
    }

    override fun save() {
        loadIfNeeded()
        val source = File(plugin.dataFolder, "leaderboards.yml")
        val file = YamlConfiguration()
        leaderboards!!.forEach {
            file["${it.id}.location.world"] = it.location.world!!.name
            file["${it.id}.location.x"] = it.location.x
            file["${it.id}.location.y"] = it.location.y
            file["${it.id}.location.z"] = it.location.z
            file["${it.id}.type"] = it.type
            file["${it.id}.limit"] = it.limit
        }
        try {
            file.save(source)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    override fun clear() {
        leaderboards = null
    }

}
