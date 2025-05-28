package dev.zabricraft.usecases.games

import dev.zabricraft.models.games.IGame
import dev.zabricraft.repositories.games.IGamesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class AddGameUseCaseTest {

    @Test
    fun testInvoke() {
        val repository = mockk<IGamesRepository>()
        val getSignsUseCase = mockk<IGetSignsUseCase>()
        val useCase = AddGameUseCase(repository, getSignsUseCase)
        val game = mockk<IGame>()
        every { repository.add(game) } returns Unit
        every { getSignsUseCase(game) } returns emptyList()
        every { game.signs } returns mutableListOf()
        useCase(game)
        verify { repository.add(game) }
    }

}
