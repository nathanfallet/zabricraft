package me.nathanfallet.zabricraft.database.players

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.datetime.Clock
import me.nathanfallet.zabricraft.database.Database
import me.nathanfallet.zabricraft.models.players.CachedPlayer
import me.nathanfallet.zabricraft.models.players.UpdateZabriPlayerPayload
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class DatabaseZabriPlayersRepositoryTest {

    @Test
    fun testCreate() {
        val database = Database(protocol = "h2", name = "testCreateZabriPlayer")
        val repository = DatabaseZabriPlayersRepository(database)
        val player = mockk<Player>()
        val uuid = UUID.randomUUID()
        mockkStatic(Bukkit::class)
        every { Bukkit.getOnlineMode() } returns true
        every { player.uniqueId } returns uuid
        every { player.name } returns "Player"
        val zabriPlayer = repository.create(player)
        val zabriPlayerFromDatabase = database.dbQuery {
            ZabriPlayers
                .selectAll()
                .map {
                    ZabriPlayers.toZabriPlayer(it, CachedPlayer(false, Clock.System.now()))
                }
                .singleOrNull()
        }
        assertEquals(uuid, zabriPlayer?.id)
        assertEquals("Player", zabriPlayer?.name)
        assertEquals(0, zabriPlayer?.money)
        assertEquals(0, zabriPlayer?.score)
        assertEquals(0, zabriPlayer?.victories)
        assertEquals(false, zabriPlayer?.admin)
        assertEquals(zabriPlayer?.id, zabriPlayerFromDatabase?.id)
        assertEquals(zabriPlayer?.name, zabriPlayerFromDatabase?.name)
        assertEquals(zabriPlayer?.money, zabriPlayerFromDatabase?.money)
        assertEquals(zabriPlayer?.score, zabriPlayerFromDatabase?.score)
        assertEquals(zabriPlayer?.victories, zabriPlayerFromDatabase?.victories)
        assertEquals(zabriPlayer?.admin, zabriPlayerFromDatabase?.admin)
        unmockkStatic(Bukkit::class)
    }

    @Test
    fun testGet() {
        val database = Database(protocol = "h2", name = "testGetZabriPlayer")
        val repository = DatabaseZabriPlayersRepository(database)
        val player = mockk<Player>()
        val uuid = UUID.randomUUID()
        mockkStatic(Bukkit::class)
        every { Bukkit.getOnlineMode() } returns true
        every { player.uniqueId } returns uuid
        every { player.name } returns "Player"
        val zabriPlayer = repository.create(player)
        val zabriPlayerFromDatabase = database.dbQuery {
            ZabriPlayers
                .select { ZabriPlayers.id eq uuid }
                .map {
                    ZabriPlayers.toZabriPlayer(it, CachedPlayer(false, Clock.System.now()))
                }
                .singleOrNull()
        }
        assertEquals(zabriPlayer?.id, zabriPlayerFromDatabase?.id)
        assertEquals(zabriPlayer?.name, zabriPlayerFromDatabase?.name)
        assertEquals(zabriPlayer?.money, zabriPlayerFromDatabase?.money)
        assertEquals(zabriPlayer?.score, zabriPlayerFromDatabase?.score)
        assertEquals(zabriPlayer?.victories, zabriPlayerFromDatabase?.victories)
        assertEquals(zabriPlayer?.admin, zabriPlayerFromDatabase?.admin)
        unmockkStatic(Bukkit::class)
    }

    @Test
    fun testUpdate() {
        val database = Database(protocol = "h2", name = "testUpdateZabriPlayer")
        val repository = DatabaseZabriPlayersRepository(database)
        val player = mockk<Player>()
        val uuid = UUID.randomUUID()
        mockkStatic(Bukkit::class)
        every { Bukkit.getOnlineMode() } returns true
        every { player.uniqueId } returns uuid
        every { player.name } returns "Player"
        val zabriPlayer = repository.create(player)
        repository.update(uuid, UpdateZabriPlayerPayload(money = 100, score = 200, victories = 300, admin = true))
        val zabriPlayerFromDatabase = database.dbQuery {
            ZabriPlayers
                .select { ZabriPlayers.id eq uuid }
                .map {
                    ZabriPlayers.toZabriPlayer(it, CachedPlayer(false, Clock.System.now()))
                }
                .singleOrNull()
        }
        assertEquals(zabriPlayer?.id, zabriPlayerFromDatabase?.id)
        assertEquals(zabriPlayer?.name, zabriPlayerFromDatabase?.name)
        assertEquals(100, zabriPlayerFromDatabase?.money)
        assertEquals(200, zabriPlayerFromDatabase?.score)
        assertEquals(300, zabriPlayerFromDatabase?.victories)
        assertEquals(true, zabriPlayerFromDatabase?.admin)
        unmockkStatic(Bukkit::class)
    }

    @Test
    fun testDelete() {
        val database = Database(protocol = "h2", name = "testDeleteZabriPlayer")
        val repository = DatabaseZabriPlayersRepository(database)
        val player = mockk<Player>()
        val uuid = UUID.randomUUID()
        mockkStatic(Bukkit::class)
        every { Bukkit.getOnlineMode() } returns true
        every { player.uniqueId } returns uuid
        every { player.name } returns "Player"
        repository.create(player)
        repository.delete(uuid)
        val count = database.dbQuery {
            ZabriPlayers
                .selectAll()
                .count()
        }
        assertEquals(0, count)
        unmockkStatic(Bukkit::class)
    }

}
