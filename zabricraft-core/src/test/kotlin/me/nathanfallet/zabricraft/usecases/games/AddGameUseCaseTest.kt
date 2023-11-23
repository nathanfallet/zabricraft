package me.nathanfallet.zabricraft.usecases.games

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.nathanfallet.zabricraft.models.games.IGame
import me.nathanfallet.zabricraft.repositories.games.IGamesRepository
import kotlin.test.Test

class AddGameUseCaseTest {

    @Test
    fun testInvoke() {
        val repository = mockk<IGamesRepository>()
        val useCase = AddGameUseCase(repository)
        val game = mockk<IGame>()
        every { repository.add(game) } returns Unit
        useCase(game)
        verify { repository.add(game) }
    }

}
