package me.nathanfallet.zabricraft.usecases.games

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.nathanfallet.zabricraft.models.games.IGame
import me.nathanfallet.zabricraft.repositories.games.IGamesRepository
import kotlin.test.Test

class ClearGamesUseCaseTest {

    @Test
    fun testInvoke() {
        val repository = mockk<IGamesRepository>()
        val useCase = ClearGamesUseCase(repository)
        val game = mockk<IGame>()
        every { repository.list() } returns listOf(game)
        every { game.reset() } returns Unit
        every { repository.clear() } returns Unit
        useCase()
        verify { repository.list() }
        verify { game.reset() }
        verify { repository.clear() }
    }

}
