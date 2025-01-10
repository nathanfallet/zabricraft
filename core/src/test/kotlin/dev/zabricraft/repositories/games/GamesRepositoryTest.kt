package dev.zabricraft.repositories.games

import dev.zabricraft.models.games.IGame
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class GamesRepositoryTest {

    @Test
    fun testListEmpty() {
        val repository = GamesRepository()
        assertEquals(listOf(), repository.list())
    }

    @Test
    fun testAdd() {
        val repository = GamesRepository()
        val game = mockk<IGame>()
        repository.add(game)
        assertEquals(listOf(game), repository.list())
    }

    @Test
    fun testClear() {
        val repository = GamesRepository()
        val game = mockk<IGame>()
        repository.add(game)
        repository.clear()
        assertEquals(listOf(), repository.list())
    }

}
